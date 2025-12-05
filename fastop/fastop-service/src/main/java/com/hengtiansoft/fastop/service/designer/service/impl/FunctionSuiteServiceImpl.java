package com.hengtiansoft.fastop.service.designer.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.hengtiansoft.fastop.base.common.constants.Response.ResponseCode;
import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.*;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.service.designer.service.FunctionSuiteService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionService;
import com.hengtiansoft.fastop.service.designer.service.TestSuiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FunctionSuiteServiceImpl implements FunctionSuiteService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestSuiteService testSuiteService;

    @Autowired
    private TestFunctionService testFunctionService;

    @Autowired
    private FunctionSuiteMapper functionSuiteMapper;

    @Autowired
    private TestFunctionMapper testFunctionMapper;

    @Override
    public Response listAllFunctionSuite() {
        FunctionSuiteExample functionSuiteExample = new FunctionSuiteExample();
        List<FunctionSuite> functionSuites = functionSuiteMapper.selectByExample(functionSuiteExample);
        return ResponseFactory.success(functionSuites);
    }

    @Override
    public Response createFunctionSuite(FunSuiteIdConnectDto funSuiteIdConnectDto) {
        Integer suiteId = funSuiteIdConnectDto.getSuiteId();
        if (null == suiteId) {
            return ResponseFactory.failure("suiteId is null");
        }
        List<TestFunction> testFunctions = funSuiteIdConnectDto.getTestFunctions();
        if (null == testFunctions) {
            return ResponseFactory.failure("testFunctions is null");
        }

        TestSuite tSuite = testSuiteService.getTestSuiteInfoById(suiteId);
        if (!testSuiteService.isCanEdit(tSuite)) {
            return ResponseFactory.failure("清单待审签无法修改");
        }

        List<FunctionSuite> functionSuites = getFunctionSuiteBySuiteId(suiteId);

        Set<Integer> numSet = functionSuites.stream().map(e -> e.getFunNum()).collect(Collectors.toSet());
        int order = 0;

        if (functionSuites.size() > CommonConstants.NUM_0) {
            order = functionSuites.get(functionSuites.size()-1).getFunOrder();
        }
        FunctionSuite fSuite = null;
        for(TestFunction tFun : testFunctions) {
            if (numSet.contains(tFun.getNum())) {
                return ResponseFactory.failure("重复模块编号：" + tFun.getNum());
            }
            fSuite = new FunctionSuite();
            fSuite.setTestFunId(tFun.getFunId());
            fSuite.setFunNum(tFun.getNum());
            fSuite.setFunOrder(++order);
            fSuite.setFunVersion(tFun.getVersion());
            fSuite.setSuiteId(suiteId);
            functionSuiteMapper.insertSelective(fSuite);
        }
        testSuiteService.reviewSuiteSpecial(suiteId);
        return ResponseFactory.success("创建成功");
    }

    List<FunctionSuite> getFunctionSuiteBySuiteId(Integer suiteId) {
        FunctionSuiteExample functionSuiteExample = new FunctionSuiteExample();
        functionSuiteExample.createCriteria().andSuiteIdEqualTo(suiteId);

        List<FunctionSuite> functionSuites = functionSuiteMapper.selectByExample(functionSuiteExample);
        return functionSuites;
    }

    @Override
    @Transactional(readOnly = false)
    public Response deleteFunctionSuite(FunctionSuiteDeleteDto functionSuiteDeleteDto) {
        Integer suiteId = functionSuiteDeleteDto.getSuiteId();
        if (null == suiteId) {
            return ResponseFactory.failure("suiteId is null");
        }
        List<FunctionSuiteDto> functionSuiteDtos = functionSuiteDeleteDto.getFunctionSuiteDtos();
        if (null == functionSuiteDtos) {
            return ResponseFactory.failure("functionSuiteDtos is null");
        }

        TestSuite tSuite = testSuiteService.getTestSuiteInfoById(suiteId);
        if (!testSuiteService.isCanEdit(tSuite)) {
            return ResponseFactory.failure("清单待审签无法修改");
        }

        // 预加载原始数据
        List<Integer> funIds = functionSuiteDtos.stream()
                .map(FunctionSuiteDto::getFunId)
                .collect(Collectors.toList());
       List<FunctionSuite> originList = listBySuiteAndFunIds(suiteId, funIds);

        // 将原始数据转换为 Map
        Map<Integer, FunctionSuite> originMap = originList.stream()
                .collect(Collectors.toMap(
                        FunctionSuite::getTestFunId,
                        fun -> fun,
                        (v1, v2) -> v1 // 处理重复 funId，保留第一个
                ));

        // 收集待物理删除的 FunctionSuite 实体ID
        List<Integer> funSuiteIdsToDelete = new ArrayList<>();
        // 收集需要更新 funOrder 的 FunctionSuite 实体
        List<FunctionSuite> funSuitesToUpdate = new ArrayList<>();
        // 收集待删除的模块 funId
        List<Integer> funIdsForRelyDeletion = new ArrayList<>();

        for (FunctionSuiteDto functionSuiteDto : functionSuiteDtos) {
            Integer funId = functionSuiteDto.getFunId();
            FunctionSuite originFunction = originMap.get(funId);

            if (originFunction == null) {
                // TODO: 记录日志或抛出异常：数据不一致
                continue;
            }

            // 物理删除
            if (Boolean.TRUE.equals(functionSuiteDto.isDeleted())) {
                funSuiteIdsToDelete.add(originFunction.getId());
                funIdsForRelyDeletion.add(funId);
            }

            // 处理顺序更新操作
            else if (!functionSuiteDto.getFunOrder().equals(originFunction.getFunOrder())) {
                // 复制原始对象，并设置新的顺序
                originFunction.setFunOrder(functionSuiteDto.getFunOrder());
                funSuitesToUpdate.add(originFunction);
            }

        }

        // TODO批量删除依赖关系
        /*
        if (!funIdsForRelyDeletion.isEmpty()) {
            testFunctionRelyMapper.deleteByFunctionAndSuite(funIdsForRelyDeletion, suiteId);
            testFunctionRelyMapper.deleteByFunctionRelyAndSuite(funIdsForRelyDeletion, suiteId);
        }*/

        // 批量物理删除 FunctionSuite 记录
        if (!funSuiteIdsToDelete.isEmpty()) {
            // 假设您有批量删除的方法
            int deletedCount = deleteFunctionSuiteBatchByIds(funSuiteIdsToDelete);
            if (deletedCount != funSuiteIdsToDelete.size()) {
                // 抛出异常以触发回滚
                throw new RuntimeException(ResponseCode.SYSTEM_ERR + " 删除模块数量不匹配，删除操作失败");
            }
        }

        // 批量更新模块顺序
        if (!funSuitesToUpdate.isEmpty()) {
            for (FunctionSuite functionToUpdate : funSuitesToUpdate) {
                int res = functionSuiteMapper.updateByPrimaryKeySelective(functionToUpdate);
                if (res < 1) {
                    // 如果单个更新失败，抛出异常触发事务回滚
                    throw new RuntimeException(String.valueOf(ResponseCode.SYSTEM_ERR));
                }
            }
        }

        TestSuite suite = new TestSuite();
        suite.setSuiteId(suiteId);

        // 更新清单状态
        testSuiteService.updateSuiteListAppStatusToUnApp(suite);
        // 审查特殊清单
        testSuiteService.reviewSuiteSpecial(suiteId);

        return ResponseFactory.success("success");
    }

    @Override
    public Integer countMilitaryBySuite(Integer suiteId) {
        FunctionSuiteExample functionSuiteExample = new FunctionSuiteExample();
        functionSuiteExample.createCriteria().andSuiteIdEqualTo(suiteId)
                .andDeletedEqualTo(false);
        List<FunctionSuite> functionSuites = functionSuiteMapper.selectByExample(functionSuiteExample);

        if (functionSuites.isEmpty()) {
            return CommonConstants.NUM_0;
        }

        Integer MilitaryNum = new Integer(CommonConstants.NUM_0);
        for (FunctionSuite functionSuite : functionSuites) {
            Integer funId = functionSuite.getTestFunId();
            MilitaryNum = MilitaryNum + testFunctionService.countMilitaryByFunId(funId);
        }

        return MilitaryNum;
    }

    @Override
    public Integer countKeyProcessBySuite(Integer suiteId) {
        FunctionSuiteExample functionSuiteExample = new FunctionSuiteExample();
        functionSuiteExample.createCriteria().andSuiteIdEqualTo(suiteId)
                .andDeletedEqualTo(false);
        List<FunctionSuite> functionSuites = functionSuiteMapper.selectByExample(functionSuiteExample);

        if (functionSuites.isEmpty()) {
            return CommonConstants.NUM_0;
        }

        Integer KeyProcessNum = new Integer(CommonConstants.NUM_0);
        for (FunctionSuite functionSuite : functionSuites) {
            Integer funId = functionSuite.getTestFunId();
            KeyProcessNum = KeyProcessNum + testFunctionService.countKeyProcessByFunId(funId);
        }

        return KeyProcessNum;
    }

    List<FunctionSuite> listBySuiteAndFunIds(Integer suiteId, List<Integer> funIds) {
        FunctionSuiteExample example = new FunctionSuiteExample();
        FunctionSuiteExample.Criteria criteria = example.createCriteria();

        criteria.andSuiteIdEqualTo(suiteId);

        if (funIds != null && !funIds.isEmpty()) {
            criteria.andTestFunIdIn(funIds);
        }

        return functionSuiteMapper.selectByExample(example);
    }

    public int deleteFunctionSuiteBatchByIds(List<Integer> funSuiteIdsToDelete) {
        if (funSuiteIdsToDelete == null || funSuiteIdsToDelete.isEmpty()) {
            return 0;
        }

        FunctionSuiteExample example = new FunctionSuiteExample();
        FunctionSuiteExample.Criteria criteria = example.createCriteria();

        criteria.andIdIn(funSuiteIdsToDelete);

        int deletedCount = functionSuiteMapper.deleteByExample(example);

        return deletedCount;
    }

    @Override
    public List<FunctionSuite> listFunctionSuiteBySuite(int suiteId) {
        FunctionSuiteExample functionSuiteExample = new FunctionSuiteExample();
        functionSuiteExample.createCriteria().andSuiteIdEqualTo(suiteId);

        List<FunctionSuite> functionSuites = functionSuiteMapper.selectByExample(functionSuiteExample);
        return functionSuites;
    }

    /**
     * 联合查询，获取FunctionSuiteDto
     * @param suiteId
     * @return
     */
    @Override
    public List<FunctionSuiteDto> listDtoBySuiteWithExample(Integer suiteId) {

        FunctionSuiteExample fsExample = new FunctionSuiteExample();
        fsExample.createCriteria().andSuiteIdEqualTo(suiteId);
        fsExample.setOrderByClause("fun_order"); // ORDER BY fs.fun_order

        // 查询 function_suite 表，获取基础实体列表
        List<FunctionSuite> fsList = functionSuiteMapper.selectByExample(fsExample);

        if (CollectionUtil.isEmpty(fsList)) {
            return Collections.emptyList();
        }

        Set<Integer> funIds = fsList.stream()
                .map(FunctionSuite::getTestFunId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (CollectionUtil.isEmpty(funIds)) {
            return mapAndMergeData(fsList, Collections.emptyMap());
        }

        TestFunctionExample tfExample = new TestFunctionExample();
        tfExample.createCriteria().andFunIdIn(new ArrayList<>(funIds));

        List<TestFunction> tfList = testFunctionMapper.selectByExample(tfExample);

        Map<Integer, TestFunction> tfMap = tfList.stream()
                .collect(Collectors.toMap(TestFunction::getFunId, tf -> tf));

        return mapAndMergeData(fsList, tfMap);
    }

    /**
     * 辅助方法：将 FunctionSuite 实体和 TestFunction Map 合并到 FunctionSuiteDto 列表
     */
    private List<FunctionSuiteDto> mapAndMergeData(List<FunctionSuite> fsList, Map<Integer, TestFunction> tfMap) {

        return fsList.stream().map(fs -> {
            FunctionSuiteDto dto = new FunctionSuiteDto();

            TestFunction tf = tfMap.get(fs.getTestFunId());

            BeanUtils.copyProperties(fs, dto); // 复制 id, funOrder, suiteId, deleted 等

            // 手动映射 testFunId -> funId
            dto.setFunId(fs.getTestFunId());

            // 手动映射 funNum -> num
            dto.setNum(fs.getFunNum());

            if (tf != null) {
                dto.setFunName(tf.getFunName());
                dto.setVersion(tf.getVersion());
                dto.setNum(tf.getNum());
                dto.setPlaneEffectMin(tf.getPlaneEffectMin());
                dto.setPlaneEffectMax(tf.getPlaneEffectMax());
                dto.setVersionDescription(tf.getVersionDescription());
                dto.setMilitary(tf.getMilitary());
                dto.setKeyProCount(tf.getKeyProCount());
            }
            return dto;
        }).collect(Collectors.toList());
    }

}
