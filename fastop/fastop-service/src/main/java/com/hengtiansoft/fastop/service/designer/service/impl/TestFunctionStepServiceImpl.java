package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionStepMapper;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCase;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStep;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStepExample;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TestFunctionStepServiceImpl implements TestFunctionStepService {

    @Autowired
    private TestFunctionStepMapper testFunctionStepMapper;

    @Override
    public Response add(TestFunctionStep testFunctionStep) {

        testFunctionStep.setStepStatus(StatusContants.step_status_unuse);
        testFunctionStep.setStepDate(String.valueOf(new Date()));
        testFunctionStep.setUpdate(StatusContants.step_update_change);
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
        tFStep.setUpdate(StatusContants.step_update_change);
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
        tFStep.setUpdate(StatusContants.step_update_change);
        tFStep.setStepStatus(StatusContants.step_status_del);
        int result = testFunctionStepMapper.updateByPrimaryKeySelective(tFStep);
        if (result > CommonConstants.NUM_0) {
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
    public Response getByCaseId(Integer caseId) {
        if (caseId == null) {
            return ResponseFactory.failure("未提供caseId");
        }
        TestFunctionStepExample testFunctionStepExample = new TestFunctionStepExample();
        TestFunctionStepExample.Criteria criteria = testFunctionStepExample.createCriteria();

        criteria.andCaseIdEqualTo(caseId);

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
    public List<TestFunctionStep> getUpdateAll() {
        TestFunctionStepExample testFunctionStepExample = new TestFunctionStepExample();
        List<TestFunctionStep> list = testFunctionStepMapper.selectByExample(testFunctionStepExample);
        return list;
    }

    @Override
    public Response deletePhy(Integer stepId) {
        if (stepId == null) {
            return ResponseFactory.failure("stepId is null");
        }
        TestFunctionStep testFunctionstep = testFunctionStepMapper.selectByPrimaryKey(stepId);
        if (testFunctionstep == null) {
            return ResponseFactory.failure("cant find it" );
        }
        int result = testFunctionStepMapper.deleteByPrimaryKey(stepId);
        if (result > 0) {
            return ResponseFactory.success("success");
        }
        return ResponseFactory.failure("fail");
    }

    @Override
    public Response updateByCaseIds(List<Integer> testFunctionCaseIds) {
        if (testFunctionCaseIds == null || testFunctionCaseIds.isEmpty()) {
            return ResponseFactory.success("列表为空，无需更新");
        }

        TestFunctionStep record = new TestFunctionStep();
        record.setUpdate(StatusContants.step_update_change);

        TestFunctionStepExample example = new TestFunctionStepExample();
        example.createCriteria().andCaseIdIn(testFunctionCaseIds);

        int rows = testFunctionStepMapper.updateByExampleSelective(record, example);

        return ResponseFactory.success("更新成功，共更新了 " + rows + " 条数据");
    }

}
