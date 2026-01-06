package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.*;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TestFunctionModuleServiceImpl implements TestFunctionModuleService {

    @Autowired
    private TestFunctionModuleMapper testFunctionModuleMapper;

    @Autowired
    private TestFunctionCaseMapper testFunctionCaseMapper;

    @Autowired
    private TestFunctionStepMapper testFunctionStepMapper;

    @Override
    public Response add(TestFunctionModule testFunctionModule) {

        testFunctionModule.setModuleStatus(StatusContants.step_status_unuse);
        testFunctionModule.setModuleDate(String.valueOf(new Date()));
        testFunctionModule.setUpdate(StatusContants.step_update_change);
        //TODO
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
        tFModule.setUpdate(StatusContants.step_update_change);
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
        tFModule.setUpdate(StatusContants.step_update_change);
        tFModule.setModuleStatus(StatusContants.step_status_del);
        int result = testFunctionModuleMapper.updateByPrimaryKeySelective(tFModule);
        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("用例删除成功");
        }
        return ResponseFactory.failure("用例删除失败");
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
    public List<TestFunctionModule> getUpdateAll() {
        TestFunctionModuleExample testFunctionModuleExample = new TestFunctionModuleExample();
        List<TestFunctionModule> list = testFunctionModuleMapper.selectByExample(testFunctionModuleExample);
        return list;
    }

    @Override
    public Response deletePhy(Integer moduleId) {
        if (moduleId == null) {
            return ResponseFactory.failure("moduleId is null");
        }
        TestFunctionModule testFunctionmodule = testFunctionModuleMapper.selectByPrimaryKey(moduleId);
        if (testFunctionmodule == null) {
            return ResponseFactory.failure("cant find it" );
        }
        int result = testFunctionModuleMapper.deleteByPrimaryKey(moduleId);
        if (result > 0) {
            return ResponseFactory.success("success");
        }
        return ResponseFactory.failure("fail");
    }

    @Override
    public Response getTreeByFunId(Integer funId) {
        if (funId == null) {
            return ResponseFactory.failure("未提供funId");
        }

        // 1. Fetch Modules
        TestFunctionModuleExample moduleExample = new TestFunctionModuleExample();
        moduleExample.createCriteria().andFunIdEqualTo(funId);
        List<TestFunctionModule> modules = testFunctionModuleMapper.selectByExample(moduleExample);

        if (modules == null || modules.isEmpty()) {
            return ResponseFactory.success(Collections.emptyList());
        }

        List<Integer> moduleIds = modules.stream().map(TestFunctionModule::getModuleId).collect(Collectors.toList());

        // 2. Fetch Cases
        TestFunctionCaseExample caseExample = new TestFunctionCaseExample();
        caseExample.createCriteria().andModuleIdIn(moduleIds);
        List<TestFunctionCase> cases = testFunctionCaseMapper.selectByExample(caseExample);

        Map<Integer, List<TestFunctionCase>> casesByModuleId = cases.stream()
            .collect(Collectors.groupingBy(TestFunctionCase::getModuleId));

        List<Integer> caseIds = cases.stream().map(TestFunctionCase::getCaseId).collect(Collectors.toList());

        // 3. Fetch Steps
        Map<Integer, List<TestFunctionStep>> stepsByCaseId;
        if (!caseIds.isEmpty()) {
            TestFunctionStepExample stepExample = new TestFunctionStepExample();
            stepExample.createCriteria().andCaseIdIn(caseIds);
            List<TestFunctionStep> steps = testFunctionStepMapper.selectByExample(stepExample);
            stepsByCaseId = steps.stream().collect(Collectors.groupingBy(TestFunctionStep::getCaseId));
        } else {
            stepsByCaseId = Collections.emptyMap();
        }

        // 4. Build Tree
        List<DesignNodeDto> moduleNodes = new ArrayList<>();

        for (TestFunctionModule module : modules) {
            DesignNodeDto moduleNode = DesignNodeDto.builder()
                .key("module_" + module.getModuleId())
                .id(module.getModuleId())
                .name(module.getModuleName())
                .type("MODULE")
                .changeUser(module.getChangeUser())
                .description(module.getModuleDescription())
                .children(new ArrayList<>())
                .build();

            List<TestFunctionCase> moduleCases = casesByModuleId.getOrDefault(module.getModuleId(), Collections.emptyList());

            for (TestFunctionCase kase : moduleCases) {
                 DesignNodeDto caseNode = DesignNodeDto.builder()
                    .key("case_" + kase.getCaseId())
                    .id(kase.getCaseId())
                    .name(kase.getCaseName())
                    .type("CASE")
                    .changeUser(kase.getChangeUser())
                    .description(kase.getCaseDescription())
                    .children(new ArrayList<>())
                    .build();

                 List<TestFunctionStep> caseSteps = stepsByCaseId.getOrDefault(kase.getCaseId(), Collections.emptyList());

                 for (TestFunctionStep step : caseSteps) {
                     DesignNodeDto stepNode = DesignNodeDto.builder()
                        .key("step_" + step.getStepId())
                        .id(step.getStepId())
                        .name(step.getStepName())
                        .type("STEP")
                        .changeUser(step.getChangeUser())
                        .description(step.getStepDescription())
                        .operation(step.getStepOperation())
                        .obj(step.getStepObj())
                        .purpose(step.getStepPurpose())
                        .build();

                     caseNode.getChildren().add(stepNode);
                 }

                 moduleNode.getChildren().add(caseNode);
            }

            moduleNodes.add(moduleNode);
        }

        return ResponseFactory.success(moduleNodes);
    }
}
