package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.entity.TestFunction;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCase;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModule;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStep;
import com.hengtiansoft.fastop.service.designer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestExampleServiceImpl implements TestExampleService {

    @Autowired
    private TestFunctionService testFunctionService;
    @Autowired
    private TestFunctionModuleService testFunctionModuleService;
    @Autowired
    private TestFunctionCaseService testFunctionCaseService;
    @Autowired
    private TestFunctionStepService testFunctionStepService;

    private static class ProcessResult {
        String log;
        List<Integer> ids;

        public ProcessResult(String log, List<Integer> ids) {
            this.log = log;
            this.ids = ids;
        }
    }

    @Override
    public Response UpdateAll() {
        StringBuilder sb = new StringBuilder();

        ProcessResult moduleResult = updateModule();
        sb.append(moduleResult.log);

        ProcessResult caseResult = updateCase(moduleResult.ids);
        sb.append(caseResult.log);

        String stepLog = updateStep(caseResult.ids);
        sb.append(stepLog);

        return ResponseFactory.success(sb.toString());
    }

    private ProcessResult updateModule() {
        List<TestFunctionModule> tFModules = testFunctionModuleService.getUpdateAll();
        if (tFModules.isEmpty()) {
            return new ProcessResult("testFunctionModule没有需要更新的内容\n\n", Collections.emptyList());
        }

        List<Integer> moduleIds = tFModules.stream()
                .map(TestFunctionModule::getModuleId)
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        for (TestFunctionModule tFModule : tFModules) {
            Integer funId = tFModule.getFunId();
            tFModule.setUpdated(StatusContants.step_update_unchange);

            if (tFModule.getModuleStatus() == StatusContants.step_status_del) {
                Response result = testFunctionModuleService.update(tFModule);
                sb.append("ModuleId: ").append(tFModule.getModuleId())
                        .append(" 已经被删除 ").append(result.getMsg()).append("\n");
                continue;
            }

            if (funId != null) {
                Response tFResponse = testFunctionService.getById(funId);
                if (tFResponse.isSuccess() && tFResponse.getData() != null) {
                    TestFunction tF = (TestFunction) tFResponse.getData();

                    if (Boolean.FALSE.equals(tF.getDeleted())) {
                        tFModule.setModuleStatus(StatusContants.step_status_inuse);
                        Response result = testFunctionModuleService.update(tFModule);
                        sb.append("ModuleId: ").append(tFModule.getModuleId())
                                .append(" 进入工作状态 ").append(result.getMsg()).append("\n");
                    } else {
                        tFModule.setModuleStatus(StatusContants.step_status_del);
                        Response result = testFunctionModuleService.update(tFModule);
                        sb.append("ModuleId: ").append(tFModule.getModuleId())
                                .append(" 被删除 ").append(result.getMsg()).append("\n");
                    }
                    continue;
                }
            }

            Response result = testFunctionModuleService.deletePhy(tFModule.getModuleId());
            sb.append("ModuleId: ").append(tFModule.getModuleId())
                    .append(" 没有被使用，删除中 ").append(result.getMsg()).append("\n");
        }

        sb.append("\n");
        return new ProcessResult(sb.toString(), moduleIds);
    }

    private ProcessResult updateCase(List<Integer> moduleIds) {
        if (moduleIds != null && !moduleIds.isEmpty()) {
            testFunctionCaseService.updateByModuleIds(moduleIds);
        }

        List<TestFunctionCase> tFCases = testFunctionCaseService.getUpdateAll();
        if (tFCases.isEmpty()) {
            return new ProcessResult("testFunctionCase没有需要更新的内容\n\n", Collections.emptyList());
        }

        List<Integer> caseIds = tFCases.stream()
                .map(TestFunctionCase::getCaseId)
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        for (TestFunctionCase tFCase : tFCases) {
            Integer moduleId = tFCase.getModuleId();
            tFCase.setUpdated(StatusContants.step_update_unchange);

            if (tFCase.getCaseStatus() == StatusContants.step_status_del) {
                Response result = testFunctionCaseService.update(tFCase);
                sb.append("CaseId: ").append(tFCase.getCaseId())
                        .append(" 已经被删除 ").append(result.getMsg()).append("\n");
                continue;
            }

            if (moduleId != null) {
                Response tFModuleResponse = testFunctionModuleService.getByModuleId(moduleId);
                if (tFModuleResponse.isSuccess() && tFModuleResponse.getData() != null) {
                    TestFunctionModule tFModule = (TestFunctionModule) tFModuleResponse.getData();
                    tFCase.setCaseStatus(tFModule.getModuleStatus());
                    Response result = testFunctionCaseService.update(tFCase);
                    sb.append("CaseId: ").append(tFCase.getCaseId())
                            .append(" 切换为状态: ").append(tFModule.getModuleStatus())
                            .append(" ").append(result.getMsg()).append("\n");
                    continue;
                }
            }

            Response result = testFunctionCaseService.deletePhy(tFCase.getCaseId());
            sb.append("CaseId: ").append(tFCase.getCaseId())
                    .append(" 没有被引用，删除中 ").append(result.getMsg()).append("\n");
        }

        sb.append("\n");
        return new ProcessResult(sb.toString(), caseIds);
    }

    private String updateStep(List<Integer> caseIds) {
        if (caseIds != null && !caseIds.isEmpty()) {
            testFunctionStepService.updateByCaseIds(caseIds);
        }

        List<TestFunctionStep> tFSteps = testFunctionStepService.getUpdateAll();
        if (tFSteps.isEmpty()) {
            return "testFunctionStep没有需要更新的内容\n";
        }

        StringBuilder sb = new StringBuilder();

        for (TestFunctionStep tFStep : tFSteps) {
            Integer caseId = tFStep.getCaseId();
            tFStep.setUpdate(StatusContants.step_update_unchange);

            if (tFStep.getStepStatus() == StatusContants.step_status_del) {
                Response result = testFunctionStepService.update(tFStep);
                sb.append("StepId: ").append(tFStep.getStepId())
                        .append(" 已经被删除 ").append(result.getMsg()).append("\n");
                continue;
            }

            if (caseId != null) {
                Response tFCaseResponse = testFunctionCaseService.getByCaseId(caseId);
                if (tFCaseResponse.isSuccess() && tFCaseResponse.getData() != null) {
                    TestFunctionCase tFCase = (TestFunctionCase) tFCaseResponse.getData();
                    tFStep.setStepStatus(tFCase.getCaseStatus());
                    Response result = testFunctionStepService.update(tFStep);
                    sb.append("StepId: ").append(tFStep.getStepId())
                            .append(" 切换为状态: ").append(tFCase.getCaseStatus())
                            .append(" ").append(result.getMsg()).append("\n");
                    continue;
                }
            }

            Response result = testFunctionStepService.deletePhy(tFStep.getStepId());
            sb.append("StepId: ").append(tFStep.getStepId())
                    .append(" 没有被引用，删除中 ").append(result.getMsg()).append("\n");
        }

        sb.append("\n");
        return sb.toString();
    }
}