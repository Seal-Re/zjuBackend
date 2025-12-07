package com.hengtiansoft.fastop.service.planner.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.constants.Status.TestPlanStatusContants;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionCaseMapper;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionModuleMapper;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionStepMapper;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.model.planner.dto.ExeStepMapper;
import com.hengtiansoft.fastop.model.planner.entity.ExeStep;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepExample;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepWithBLOBs;
import com.hengtiansoft.fastop.service.planner.service.ExeStepService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ExeStepServiceImpl implements ExeStepService {

    @Autowired
    private ExeStepMapper exeStepMapper;

    @Autowired
    private TestFunctionModuleMapper testFunctionModuleMapper;

    @Autowired
    private TestFunctionCaseMapper testFunctionCaseMapper;

    @Autowired
    private TestFunctionStepMapper testFunctionStepMapper;

    @Override
    @Transactional(readOnly = false)
    public int deleteExeStep(String exeFunctionId) {
        int res = 0;
        // 查出对应的数据
        ExeStepExample exeStepExample = new ExeStepExample();
        ExeStepExample.Criteria criteria = exeStepExample.createCriteria();
        criteria.andExeFunctionIdEqualTo(exeFunctionId);
        List<ExeStep> list = exeStepMapper.selectByExample(exeStepExample);
        // delete设置为true
        for (ExeStep exeStep : list) {
            exeStep.setDeleted(true);
            // 更新
            exeStepMapper.updateByPrimaryKeySelective((ExeStepWithBLOBs) exeStep);
            res++;
        }
        return res;
    }

    @Override
    @Transactional(readOnly = false)
    public void conveyTestStep2ExeStep(Integer funId, String exeFunctionId) {
        if (funId == null || exeFunctionId == null) return;

        // 1. Get Modules
        TestFunctionModuleExample moduleExample = new TestFunctionModuleExample();
        moduleExample.createCriteria().andFunIdEqualTo(funId);
        List<TestFunctionModule> modules = testFunctionModuleMapper.selectByExample(moduleExample);

        for (TestFunctionModule module : modules) {
            // 2. Get Cases
            TestFunctionCaseExample caseExample = new TestFunctionCaseExample();
            caseExample.createCriteria().andModuleIdEqualTo(module.getModuleId());
            List<TestFunctionCase> cases = testFunctionCaseMapper.selectByExample(caseExample);

            for (TestFunctionCase testCase : cases) {
                // 3. Get Steps
                TestFunctionStepExample stepExample = new TestFunctionStepExample();
                stepExample.createCriteria().andCaseIdEqualTo(testCase.getCaseId());
                List<TestFunctionStep> steps = testFunctionStepMapper.selectByExample(stepExample);

                for (TestFunctionStep step : steps) {
                    ExeStepWithBLOBs exeStep = new ExeStepWithBLOBs();
                    BeanUtils.copyProperties(step, exeStep);
                    exeStep.setExeStepId(UUID.randomUUID().toString());
                    exeStep.setExeFunctionId(exeFunctionId);
                    exeStep.setExeStatus(TestPlanStatusContants.PLAN_STATUS_UNEXE);

                    // Set default deleted if null
                    if (exeStep.getDeleted() == null) exeStep.setDeleted(false);

                    exeStepMapper.insertSelective(exeStep);
                }
            }
        }
    }
}
