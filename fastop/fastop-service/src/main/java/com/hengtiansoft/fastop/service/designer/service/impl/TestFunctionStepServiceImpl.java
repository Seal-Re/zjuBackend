package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionStepMapper;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStep;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStepExample;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionCaseService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TestFunctionStepServiceImpl implements TestFunctionStepService {

    @Autowired
    private TestFunctionCaseService testFunctionCaseService;

    @Autowired
    private TestFunctionStepMapper testFunctionStepMapper;

    @Override
    public Response add(TestFunctionStep testFunctionStep) {
        if(testFunctionStep.getCaseId() == null){
            return ResponseFactory.failure("添加失败, 上层case不存在");
        }
        if(!testFunctionCaseService.getByCaseId(testFunctionStep.getCaseId()).isSuccess()) {
            return ResponseFactory.failure("添加失败, 上层case不存在");
        }

        testFunctionStep.setStepStatus(StatusContants.judge_result_unjudge);
        testFunctionStep.setStepDate(String.valueOf(new Date()));
        // TODO
        testFunctionStep.setChangeUser(null);

        int count = testFunctionStepMapper.insertSelective(testFunctionStep);

        if (count > 0) {
            return ResponseFactory.success(testFunctionStep);
        }
        return ResponseFactory.failure("增加失败");
    }

    @Override
    public Response update(TestFunctionStep testFunctionStep) {
        TestFunctionStep tFStep = testFunctionStepMapper.selectByPrimaryKey(testFunctionStep.getStepId());
        if (tFStep == null) {
            return ResponseFactory.failure("用例不存在");
        }

        int result = testFunctionStepMapper.updateByPrimaryKeySelective(testFunctionStep);

        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("更新成功");
        }
        return ResponseFactory.failure("更新失败");
    }

    @Override
    public Response delete(Integer StepId) {
        TestFunctionStep tFStep = testFunctionStepMapper.selectByPrimaryKey(StepId);
        if (tFStep == null) {
            return ResponseFactory.failure("用例不存在");
        }
        int result = testFunctionStepMapper.deleteByPrimaryKey(StepId);

        if (result > CommonConstants.NUM_0) {
            // TODO 删除对应的步骤
            // if
            return ResponseFactory.success("删除成功");
        }
        return ResponseFactory.failure("删除失败");
    }

    @Override
    public Response getByStepId(Integer StepId) {
        if (StepId == null) {
            return ResponseFactory.failure("未提供StepId");
        }
        TestFunctionStep testFunctionStep = testFunctionStepMapper.selectByPrimaryKey(StepId);
        if (testFunctionStep == null) {
            return ResponseFactory.failure("查询失败");
        }
        return ResponseFactory.success(testFunctionStep);
    }

    @Override
    public Response getByCaseId(Integer funId) {
        if (funId == null) {
            return ResponseFactory.failure("未提供funId");
        }
        TestFunctionStepExample testFunctionStepExample = new TestFunctionStepExample();
        TestFunctionStepExample.Criteria criteria = testFunctionStepExample.createCriteria();

        criteria.andCaseIdEqualTo(funId);

        List<TestFunctionStep> list = testFunctionStepMapper.selectByExample(testFunctionStepExample);

        if (list != null && list.size() > 0) {
            return ResponseFactory.success(list);
        }

        return ResponseFactory.failure("查询失败");
    }

    @Override
    public Response listAll() {
         TestFunctionStepExample testFunctionStepExample = new TestFunctionStepExample();

         List<TestFunctionStep> list = testFunctionStepMapper.selectByExample(testFunctionStepExample);

         return ResponseFactory.success(list);
    }

    @Override
    public Response checkStep(Integer StepId) {

        if (StepId == null || StepId <= 0) {
            return ResponseFactory.failure("不能为空");
        }
        TestFunctionStep testFunctionStep = testFunctionStepMapper.selectByPrimaryKey(StepId);
        if (testFunctionStep == null) {
            return ResponseFactory.failure("查找不到");
        }
        testFunctionStep.setStepStatus(StatusContants.judge_result_passed);
        testFunctionStepMapper.updateByPrimaryKeySelective(testFunctionStep);
        return ResponseFactory.success("审签成功");
    }

    @Override
    public Response getAllStepsByFunctionId(Integer functionId) {
        if (functionId == null) {
            return ResponseFactory.failure("查找失败，functionId为空");
        }
        TestFunctionStepExample testFunctionStepExample = new TestFunctionStepExample();
        TestFunctionStepExample.Criteria criteria = testFunctionStepExample.createCriteria();

        criteria.andCaseIdEqualTo(functionId);

        List<TestFunctionStep> list = testFunctionStepMapper.selectByExample(testFunctionStepExample);

        return ResponseFactory.success(list);
    }

}
