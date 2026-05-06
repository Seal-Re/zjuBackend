package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
// import com.hengtiansoft.fastop.base.common.exception.AppRTException; // Missing in Target
import com.hengtiansoft.fastop.model.designer.dto.*;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.service.designer.service.FunctionSuiteService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionService;
import com.hengtiansoft.fastop.service.designer.service.TestSuiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TestSuiteServiceImpl implements TestSuiteService {

    @Autowired
    private TestFunctionService testFunctionService;

    @Autowired
    private TestSuiteMapper testSuiteMapper;

    @Autowired
    private FunctionSuiteService functionSuiteService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response add(TestSuiteRequestDto testSuiteRequestDto) {

        TestSuiteExample checkExample = new TestSuiteExample();
        TestSuiteExample.Criteria checkCriteria = checkExample.createCriteria();
        checkCriteria.andTestBaseIdEqualTo(testSuiteRequestDto.getTestBaseId());
        checkCriteria.andListApprStatusEqualTo(StatusContants.suite_list_app_unapp);
        List<TestSuite> suites = testSuiteMapper.selectByExample(checkExample);

        if (suites.size() >= CommonConstants.NUM_1) {
            StringBuilder sb = new StringBuilder();
            sb.append("新增失败，已经存在审前通过的清单:");
            suites.forEach((i -> sb.append("【").append(i.getSuiteName()).append("】").append(",")));
            return ResponseFactory.failure(sb.substring(0, sb.length() - 1));
        }

        TestSuiteExample maxVersionExample = new TestSuiteExample();
        maxVersionExample.createCriteria()
                .andTestBaseIdEqualTo(testSuiteRequestDto.getTestBaseId());
        maxVersionExample.setOrderByClause("version DESC");
        List<TestSuite> maxVersionSuites = testSuiteMapper.selectByExample(maxVersionExample);

        TestSuite maxSuite = null;
        if (maxVersionSuites != null && !maxVersionSuites.isEmpty()) {
            maxSuite = maxVersionSuites.get(0);
        }

        if (maxSuite == null) {
            testSuiteRequestDto.setVersion(CommonConstants.NUM_0);
        } else {
            testSuiteRequestDto.setVersion(maxSuite.getVersion() + CommonConstants.NUM_1);
        }

        testSuiteRequestDto.setListApprStatus(StatusContants.suite_list_app_unapp);


        TestSuite testSuite = new TestSuite();
        BeanUtils.copyProperties(testSuiteRequestDto, testSuite);

        testSuite.setDeleted(false);
        testSuite.setCreatedAt(new Date());

        int rows = testSuiteMapper.insertSelective(testSuite);

        if (rows <= 0) {
            return ResponseFactory.failure("新增清单失败，数据库操作未成功。");
        }

        List<Integer> funIds = testSuiteRequestDto.getFunIds();

        if (funIds != null && !funIds.isEmpty()) {
            List<TestFunction> functionList = testFunctionService.getTestFunctionListById(funIds);

            if (functionList.size() != funIds.size()) {
                throw new RuntimeException("部分功能模块ID无效");
            }

            TestSuiteExample findIdExample = new TestSuiteExample();
            findIdExample.createCriteria()
                    .andTestBaseIdEqualTo(testSuite.getTestBaseId())
                    .andVersionEqualTo(testSuite.getVersion()); // 使用刚才计算出的版本号

            List<TestSuite> insertedSuites = testSuiteMapper.selectByExample(findIdExample);

            if (insertedSuites == null || insertedSuites.isEmpty()) {
                throw new RuntimeException("系统异常：无法获取新创建的清单ID");
            }

            Integer suiteId = insertedSuites.get(0).getSuiteId();

            FunSuiteIdConnectDto connectDto = new FunSuiteIdConnectDto();
            connectDto.setSuiteId(suiteId);
            connectDto.setTestFunctions(functionList);

            Response connectResponse = functionSuiteService.createFunctionSuite(connectDto);

            if (!connectResponse.isSuccess()) {
                throw new RuntimeException("关联功能模块失败: " + connectResponse.getMsg());
            }
        }

        return ResponseFactory.success(testSuite);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response update(TestSuiteRequestDto testSuiteRequestDto) {
        Integer suiteId = testSuiteRequestDto.getSuiteId();

        TestSuite tSuite = testSuiteMapper.selectByPrimaryKey(suiteId);
        if (tSuite == null) {
            return ResponseFactory.failure("测试集不存在");
        }
        if (!isCanEdit(tSuite)) {
            return ResponseFactory.failure("测试集待审签无法修改");
        }

        TestSuite record = new TestSuite();
        record.setSuiteId(suiteId);
        record.setSuiteName(testSuiteRequestDto.getSuiteName());
        record.setSuiteDesc(testSuiteRequestDto.getSuiteDesc());
        record.setListApprStatus(StatusContants.suite_list_app_unapp);
        // 更新时间等
        record.setUpdatedAt(new Date());

        int result = testSuiteMapper.updateByPrimaryKeySelective(record);
        if (result <= 0) {
            return ResponseFactory.failure("基础信息更新失败");
        }

        List<Integer> newFunIds = testSuiteRequestDto.getFunIds();

        if (newFunIds != null) {
            List<FunctionSuite> existingRelations = functionSuiteService.listFunctionSuiteBySuite(suiteId);

            Set<Integer> existingFunIdsSet = existingRelations.stream()
                    .map(FunctionSuite::getTestFunId)
                    .collect(Collectors.toSet());

            FunctionSuiteDeleteDto deleteDto = new FunctionSuiteDeleteDto();
            deleteDto.setSuiteId(suiteId);
            List<FunctionSuiteDto> changeList = new ArrayList<>();

            for (FunctionSuite existing : existingRelations) {
                if (!newFunIds.contains(existing.getTestFunId())) {
                    FunctionSuiteDto item = new FunctionSuiteDto();
                    item.setFunId(existing.getTestFunId());
                    item.setDeleted(true); // 标记为删除
                    changeList.add(item);
                }
            }

            for (int i = 0; i < newFunIds.size(); i++) {
                Integer funId = newFunIds.get(i);
                if (existingFunIdsSet.contains(funId)) {
                    FunctionSuiteDto item = new FunctionSuiteDto();
                    item.setFunId(funId);
                    item.setFunOrder(i + 1);
                    item.setDeleted(false);
                    changeList.add(item);
                }
            }

            if (!changeList.isEmpty()) {
                deleteDto.setFunctionSuiteDtos(changeList);
                Response deleteResp = functionSuiteService.deleteFunctionSuite(deleteDto);
                if (!deleteResp.isSuccess()) {
                    throw new RuntimeException("同步关联关系失败: " + deleteResp.getMsg());
                }
            }

            List<Integer> idsToAdd = newFunIds.stream()
                    .filter(id -> !existingFunIdsSet.contains(id))
                    .collect(Collectors.toList());

            if (!idsToAdd.isEmpty()) {
                // 批量查询新增模块的基础信息
                List<TestFunction> metaList = testFunctionService.getTestFunctionListById(idsToAdd);
                Map<Integer, TestFunction> metaMap = metaList.stream()
                        .collect(Collectors.toMap(TestFunction::getFunId, m -> m));

                for (int i = 0; i < newFunIds.size(); i++) {
                    Integer funId = newFunIds.get(i);
                    // 只有是新增的才执行插入
                    if (idsToAdd.contains(funId)) {
                        TestFunction meta = metaMap.get(funId);
                        if (meta != null) {
                            FunSuiteIdConnectDto connectDto = new FunSuiteIdConnectDto();
                            connectDto.setSuiteId(suiteId);
                            connectDto.setTestFunctions(Collections.singletonList(meta));

                            functionSuiteService.createFunctionSuite(connectDto);
                        }
                    }
                }
            }
        }

        return ResponseFactory.success("更新成功");
    }

    @Override
    public Response submit(Integer suiteId) {
        TestSuite tS = new TestSuite();

        tS.setListApprStatus(1);
        tS.setSuiteId(suiteId);

        testSuiteMapper.updateByPrimaryKeySelective(tS);

        return ResponseFactory.success(true);
    }

    @Override
    public Response delete(Integer suiteId) {
        TestSuite tSuite = testSuiteMapper.selectByPrimaryKey(suiteId);
        if (tSuite == null) {
            return ResponseFactory.failure("指定的清单不存在");
        }
        if (!isCanEdit(tSuite)) {
            return ResponseFactory.failure("指定的清单暂时无法操作");
        }

        int result = testSuiteMapper.deleteByPrimaryKey(suiteId);
        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("删除成功");
        }
        return ResponseFactory.success("删除失败");
    }

    @Override
    public Response getById(Integer suiteId) {
        TestSuite testSuite = testSuiteMapper.selectByPrimaryKey(suiteId);
        if (testSuite == null) {
            return ResponseFactory.failure("指定的清单不存在");
        }
        return ResponseFactory.success(testSuite);
    }

    @Override
    public Response listByBaseId(Integer testBaseId) {
        if(testBaseId == null){
            return ResponseFactory.failure("查询失败，缺少基础试验Id");
        }

        TestSuiteExample example = new TestSuiteExample();
        TestSuiteExample.Criteria criteria = example.createCriteria();

        criteria.andTestBaseIdEqualTo(testBaseId);

        List<TestSuite> testSiites = testSuiteMapper.selectByExample(example);

        return ResponseFactory.success(testSiites);
    }

    @Override
    public Response listAll() {

        TestSuiteExample example = new TestSuiteExample();

        List<TestSuite> testSuites = testSuiteMapper.selectByExample(example);

        return ResponseFactory.success(testSuites);
    }

    @Override
    public Response check(Integer suiteId, String checkWorker, Integer level) {

        if (suiteId == null || checkWorker == null || level == null || level < StatusContants.suite_app_submit || level > StatusContants.suite_app_approver) {
            return ResponseFactory.failure( "参数错误或审签级别不在有效范围 (0-2)");
        }

        TestSuite suite = testSuiteMapper.selectByPrimaryKey(suiteId);

        if (suite == null) {
            return ResponseFactory.failure("未找到ID为 " + suiteId + " 的清单");
        }

        String expectedWorker = null;
        int targetListApprStatus;

        switch (level) {
            case StatusContants.suite_app_submit:
                expectedWorker = checkWorker;
                targetListApprStatus = StatusContants.suite_app_proofer;
                break;
            case StatusContants.suite_app_proofer:
                expectedWorker = suite.getProofer();
                targetListApprStatus = StatusContants.suite_app_approver;
                break;
            case StatusContants.suite_app_approver:
                expectedWorker = suite.getApprover();
                targetListApprStatus = StatusContants.suite_app_success;
                break;
            default:
                return ResponseFactory.failure("无效的审签级别: " + level);
        }

        if (level != suite.getListApprStatus()) {
            return ResponseFactory.failure("审签步骤不匹配，实际步骤为 " + StatusContants.SUITE_APP_LEVEL[suite.getListApprStatus()]);
        }

        // 提交阶段（level=0）任意操作人发起；后续各级必须匹配该清单上指定的责任人，
        // 否则任何已登录用户都能审签任何清单（与 TestFunction 审签流对齐）。
        if (level != StatusContants.suite_app_submit) {
            if (expectedWorker == null || !expectedWorker.equals(checkWorker)) {
                return ResponseFactory.failure("工作人员 [" + checkWorker + "] 无权在级别 " + level
                        + " 进行 [" + StatusContants.SUITE_APP_LEVEL[level] + "] 操作，期望工作人员: [" + expectedWorker + "]");
            }
        }

        suite.setListApprStatus(targetListApprStatus);
        Integer updateCount = testSuiteMapper.updateByPrimaryKeySelective(suite);

        if (updateCount > 0) {
            return ResponseFactory.success("清单 " + suiteId + " 审签级别 " + level + " 操作成功，状态更新为 " + StatusContants.SUITE_APP_LEVEL[targetListApprStatus]);
        } else {
            return ResponseFactory.failure("数据库更新失败");
        }
    }


    @Override
    public boolean isCanEdit(TestSuite tSuite) {
        if (!(tSuite.getListApprStatus().equals(StatusContants.suite_list_app_proof)
                || tSuite.getListApprStatus().equals(StatusContants.suite_list_app_approve))) {
            return true;
        }
        return false;
    }

    @Override
    public TestSuite getTestSuiteInfoById(Integer suiteId) {
        return testSuiteMapper.selectByPrimaryKey(suiteId);
    }

    @Override
    public boolean updateTestSuite(TestSuite suite) {
        return  testSuiteMapper.updateByPrimaryKeySelective(suite) > 0;
    }

    @Override
    public boolean reviewSuiteSpecial(Integer suiteId) {
        TestSuite suite = new TestSuite();
        suite.setSuiteId(suiteId);
        boolean military = functionSuiteService.countMilitaryBySuite(suiteId) > CommonConstants.NUM_0;
        boolean keyProcess = functionSuiteService.countKeyProcessBySuite(suiteId) > CommonConstants.NUM_0;
        suite.setMilitary(military);
        suite.setKeyProcess(keyProcess);
        testSuiteMapper.updateByPrimaryKeySelective(suite);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSuiteListAppStatusToUnApp(TestSuite tSuite) {
        tSuite.setListApprStatus(StatusContants.suite_list_app_unapp);
        return testSuiteMapper.updateByPrimaryKeySelective(tSuite) > CommonConstants.NUM_0;
    }

    @Override
    public Response getCheckTestSuite() {
        TestSuiteExample example = new TestSuiteExample();
        TestSuiteExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);
        criteria.andListApprStatusBetween(1, 2);

        List<TestSuite> testSuites = testSuiteMapper.selectByExample(example);

        return ResponseFactory.success(testSuites);
    }

}
