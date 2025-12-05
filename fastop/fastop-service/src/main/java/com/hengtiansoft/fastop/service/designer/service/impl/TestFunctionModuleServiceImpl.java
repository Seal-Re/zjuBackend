package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionCaseMapper;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionModuleMapper;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCaseExample;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModule;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModuleExample;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCase;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionCaseService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionModuleService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TestFunctionModuleServiceImpl implements TestFunctionModuleService {

    @Autowired
    private TestFunctionService testFunctionService;

    @Autowired
    private TestFunctionCaseService testFunctionCaseService;

    @Autowired
    private TestFunctionModuleMapper testFunctionModuleMapper;

    @Autowired
    private TestFunctionCaseMapper testFunctionCaseMapper;

    @Override
    public Response add(TestFunctionModule testFunctionModule) {
        if(testFunctionModule.getFunId() == null){
            return ResponseFactory.failure("添加失败, 上层fun不存在");
        }
        if(!testFunctionService.getById(testFunctionModule.getFunId()).isSuccess()) {
            return ResponseFactory.failure("添加失败, 上层fun不存在");
        }

        testFunctionModule.setModuleStatus(StatusContants.judge_result_unjudge);
        testFunctionModule.setModuleDate(String.valueOf(new Date()));
        // TODO
        testFunctionModule.setChangeUser(null);
        int count = testFunctionModuleMapper.insertSelective(testFunctionModule);

        if (count > 0) {
            return ResponseFactory.success(testFunctionModule);
        }
        return ResponseFactory.failure("增加失败");
    }

    @Override
    public Response update(TestFunctionModule testFunctionModule) {
        TestFunctionModule tFModule = testFunctionModuleMapper.selectByPrimaryKey(testFunctionModule.getModuleId());
        if (tFModule == null) {
            return ResponseFactory.failure("用例不存在");
        }

        int result = testFunctionModuleMapper.updateByPrimaryKeySelective(testFunctionModule);

        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("更新成功");
        }
        return ResponseFactory.failure("更新失败");
    }

    @Override
    public Response delete(Integer ModuleId) {
        TestFunctionModule tFModule = testFunctionModuleMapper.selectByPrimaryKey(ModuleId);
        if (tFModule == null) {
            return ResponseFactory.failure("用例不存在");
        }
        int result = testFunctionModuleMapper.deleteByPrimaryKey(ModuleId);

        if (result > CommonConstants.NUM_0) {
            if (deleteCase(ModuleId)) {
                return ResponseFactory.success("删除成功");
            }
            return ResponseFactory.failure("删除步骤失败");
        }
        return ResponseFactory.failure("删除用例失败");
    }

    @Override
    public Response getByModuleId(Integer ModuleId) {
        if (ModuleId == null) {
            return ResponseFactory.failure("未提供ModuleId");
        }
        TestFunctionModule testFunctionModule = testFunctionModuleMapper.selectByPrimaryKey(ModuleId);
        if (testFunctionModule == null) {
            return ResponseFactory.failure("查询失败");
        }
        return ResponseFactory.success(testFunctionModule);
    }

    @Override
    public Response getByFunId(Integer funId) {
        if (funId == null) {
            return ResponseFactory.failure("未提供funId");
        }
        TestFunctionModuleExample testFunctionModuleExample = new TestFunctionModuleExample();
        TestFunctionModuleExample.Criteria criteria = testFunctionModuleExample.createCriteria();

        criteria.andFunIdEqualTo(funId);

        List<TestFunctionModule> list = testFunctionModuleMapper.selectByExample(testFunctionModuleExample);

        if (list != null && list.size() > 0) {
            return ResponseFactory.success(list);
        }

        return ResponseFactory.failure("查询失败");
    }

    @Override
    public Response listAll() {
         TestFunctionModuleExample testFunctionModuleExample = new TestFunctionModuleExample();

         List<TestFunctionModule> list = testFunctionModuleMapper.selectByExample(testFunctionModuleExample);

         return ResponseFactory.success(list);
    }

    @Override
    public Response checkByModuleId(Integer moduleId) {
        if (moduleId == null || moduleId <= 0) {
            return ResponseFactory.failure("moduleId 不能为空或无效");
        }

        TestFunctionModule testFunctionModule = testFunctionModuleMapper.selectByPrimaryKey(moduleId);

        if (testFunctionModule == null ) {
            return ResponseFactory.failure("未找到 moduleId 为 [" + moduleId + "] 对应的测试功能模块");
        }

        TestFunctionCaseExample example = new TestFunctionCaseExample();
        example.createCriteria().andModuleIdEqualTo(moduleId);

        List<TestFunctionCase> CaseList = testFunctionCaseMapper.selectByExample(example);


        StringBuilder messageBuilder = new StringBuilder();
        Response subCheckResult;
        boolean allPassed = true;

        for (TestFunctionCase Case : CaseList) {

            if (Case.getCaseStatus() != StatusContants.judge_result_unjudge) {
                messageBuilder.append("CaseId: [")
                        .append(Case.getCaseId())
                        .append("] 状态为: 已审签, 跳过检查。\n");
                continue;
            }

            subCheckResult = testFunctionCaseService.checkByCaseId(Case.getCaseId());

            if (!subCheckResult.isSuccess()) {
                allPassed = false;
                String errorMessage = String.format(
                        "检查 CaseId: [%d] 时发现未审签子级项目，检查失败信息：%s",
                        Case.getCaseId(),
                        subCheckResult.getMsg()
                );
                return ResponseFactory.failure(messageBuilder.toString() + "\n\n" + errorMessage);
            }

            Case.setCaseStatus(StatusContants.judge_result_passed);
            int updateCount = testFunctionCaseMapper.updateByPrimaryKeySelective(Case);

            if (updateCount > 0) {
                messageBuilder.append("\nCaseId: [")
                        .append(Case.getCaseId())
                        .append("] 审签成功！\n");
            } else {
                allPassed = false;
                messageBuilder.append("\nCaseId: [")
                        .append(Case.getCaseId())
                        .append("] 审签通过，但数据库更新失败！\n");
            }
        }

        if (allPassed) {
            return ResponseFactory.success("所有模块检查并审签成功" + messageBuilder.toString());
        } else {
            return ResponseFactory.failure("部分模块检查成功但更新失败" + messageBuilder.toString());
        }
    }

    @Override
    public Boolean deleteCase(Integer ModuleId) {
        Response<List<TestFunctionCase>> selectCase = testFunctionCaseService.getByCaseId(ModuleId); 

        if (!selectCase.isSuccess()) {
            return Boolean.FALSE;
        }

        List<TestFunctionCase> lTFCase = selectCase.getData();
        if (lTFCase == null || lTFCase.isEmpty()) {
            return Boolean.TRUE;
        }

        for (TestFunctionCase testFunctionCase : lTFCase) {
            Response result = testFunctionCaseService.delete(testFunctionCase.getCaseId());

            if (!result.isSuccess()) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }



}
