package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionCaseMapper;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionStepMapper;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionModuleService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionCaseService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TestFunctionCaseServiceImpl implements TestFunctionCaseService {

    @Autowired
    private TestFunctionModuleService testFunctionModuleService;

    @Autowired
    private TestFunctionStepService testFunctionStepService;

    @Autowired
    private TestFunctionCaseMapper testFunctionCaseMapper;

    @Autowired
    private TestFunctionStepMapper testFunctionStepMapper;

    @Override
    public Response add(TestFunctionCase testFunctionCase) {
        if(testFunctionCase.getModuleId() == null){
            return ResponseFactory.failure("添加失败, 上层module不存在");
        }
        if(!testFunctionModuleService.getByModuleId(testFunctionCase.getModuleId()).isSuccess()) {
            return ResponseFactory.failure("添加失败, 上层module不存在");
        }

        testFunctionCase.setCaseStatus(StatusContants.judge_result_unjudge);
        testFunctionCase.setCaseDate(String.valueOf(new Date()));
        // TODO
        testFunctionCase.setChangeUser(null);

        int count = testFunctionCaseMapper.insertSelective(testFunctionCase);

        if (count > 0) {
            return ResponseFactory.success(testFunctionCase);
        }
        return ResponseFactory.failure("增加失败");
    }

    @Override
    public Response update(TestFunctionCase testFunctionCase) {
        TestFunctionCase tFCase = testFunctionCaseMapper.selectByPrimaryKey(testFunctionCase.getCaseId());
        if (tFCase == null) {
            return ResponseFactory.failure("用例不存在");
        }

        int result = testFunctionCaseMapper.updateByPrimaryKeySelective(testFunctionCase);

        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("更新成功");
        }
        return ResponseFactory.failure("更新失败");
    }

    @Override
    public Response delete(Integer caseId) {
        TestFunctionCase tFCase = testFunctionCaseMapper.selectByPrimaryKey(caseId);
        if (tFCase == null) {
            return ResponseFactory.failure("用例不存在");
        }
        int result = testFunctionCaseMapper.deleteByPrimaryKey(caseId);

        if (result > CommonConstants.NUM_0) {
            if (deleteStep(caseId)) {
                return ResponseFactory.success("删除成功");
            }
            return ResponseFactory.failure("删除步骤失败");
        }
        return ResponseFactory.failure("删除用例失败");
    }

    @Override
    public Response getByCaseId(Integer caseId) {
        if (caseId == null) {
            return ResponseFactory.failure("未提供caseId");
        }
        TestFunctionCase testFunctionCase = testFunctionCaseMapper.selectByPrimaryKey(caseId);
        if (testFunctionCase == null) {
            return ResponseFactory.failure("查询失败");
        }
        return ResponseFactory.success(testFunctionCase);
    }

    @Override
    public Response getByModuleId(Integer moduleId) {
        if (moduleId == null) {
            return ResponseFactory.failure("未提供moduleId");
        }
        TestFunctionCaseExample testFunctionCaseExample = new TestFunctionCaseExample();
        TestFunctionCaseExample.Criteria criteria = testFunctionCaseExample.createCriteria();

        criteria.andModuleIdEqualTo(moduleId);

        List<TestFunctionCase> list = testFunctionCaseMapper.selectByExample(testFunctionCaseExample);

        if (list != null && list.size() > 0) {
            return ResponseFactory.success(list);
        }

        return ResponseFactory.failure("查询失败");
    }

    @Override
    public Response listAll() {
         TestFunctionCaseExample testFunctionCaseExample = new TestFunctionCaseExample();

         List<TestFunctionCase> list = testFunctionCaseMapper.selectByExample(testFunctionCaseExample);

         return ResponseFactory.success(list);
    }

    @Override
    public Response checkByCaseId(Integer CaseId) {
        if (CaseId == null || CaseId <= 0) {
            return ResponseFactory.failure("CaseId 不能为空或无效");
        }

        TestFunctionCase testFunctionCase = testFunctionCaseMapper.selectByPrimaryKey(CaseId);

        if (testFunctionCase == null) {
            return ResponseFactory.failure("未找到 caseId 为 [" + CaseId + "] 对应的测试功能模块");
        }

        TestFunctionStepExample example = new TestFunctionStepExample();
        example.createCriteria().andCaseIdEqualTo(CaseId);

        List<TestFunctionStep> StepList = testFunctionStepMapper.selectByExample(example);

        StringBuilder messageBuilder = new StringBuilder();
        Response subCheckResult;
        boolean allPassed = true;

        for (TestFunctionStep Step : StepList) {

            if (Step.getStepStatus() == StatusContants.judge_result_passed) {
                messageBuilder.append("\nStepId: [")
                        .append(Step.getStepId())
                        .append("] 状态为: 已审签, 跳过检查。\n");
                continue;
            }
            messageBuilder.append("\nStepId: [")
                    .append(Step.getStepId())
                    .append("] 状态为: 未审签。\n");
            allPassed = false;
        }

        if (allPassed) {
            return ResponseFactory.success("所有模块检查并审签成功" + messageBuilder.toString());
        } else {
            return ResponseFactory.failure("部分模块检查成功但更新失败" + messageBuilder.toString());
        }
    }

    @Override
    public Boolean deleteStep(Integer caseId) {
        Response<List<TestFunctionStep>> selectStep = testFunctionStepService.getByCaseId(caseId); // 建议加上泛型以确保类型安全

        if (!selectStep.isSuccess()) {
            return Boolean.FALSE;
        }

        List<TestFunctionStep> lTFStep = selectStep.getData();
        if (lTFStep == null || lTFStep.isEmpty()) {
            return Boolean.TRUE;
        }

        for (TestFunctionStep testFunctionStep : lTFStep) {
            Response result = testFunctionStepService.delete(testFunctionStep.getStepId());

            if (!result.isSuccess()) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }



}
