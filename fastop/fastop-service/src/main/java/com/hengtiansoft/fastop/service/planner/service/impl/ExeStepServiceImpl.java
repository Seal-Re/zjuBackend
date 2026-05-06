package com.hengtiansoft.fastop.service.planner.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengtiansoft.fastop.base.common.constants.Status.TestPlanStatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;

import java.util.HashMap;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionCaseMapper;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionModuleMapper;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionStepMapper;
import com.hengtiansoft.fastop.model.planner.dto.ExeLogMapper;
import com.hengtiansoft.fastop.model.planner.dto.ExeStepCommandDto;
import com.hengtiansoft.fastop.model.planner.dto.ExeStepMapper;
import com.hengtiansoft.fastop.model.planner.entity.ExeStep;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepExample;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepWithBLOBs;
import com.hengtiansoft.fastop.model.planner.utils.ExeLog;
import com.hengtiansoft.fastop.model.planner.utils.ExeStepCommand;
import com.hengtiansoft.fastop.model.planner.dto.ems.MessageEtt;
import com.hengtiansoft.fastop.service.config.IntegrationProperties;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionCaseService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionModuleService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionStepService;
import com.hengtiansoft.fastop.service.integration.EmsMessageService;
import com.hengtiansoft.fastop.service.planner.service.ExeStepService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class ExeStepServiceImpl implements ExeStepService {

    @Autowired
    @Qualifier("taskExecutor")
    private Executor executor;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExeStepMapper exeStepMapper;

    @Autowired
    private com.hengtiansoft.fastop.model.planner.dto.ExeFunctionMapper exeFunctionMapper;

    @Autowired
    private ExeLogMapper exeLogMapper;

    @Autowired
    private TestFunctionCaseService testFunctionCaseService;
    @Autowired
    private TestFunctionModuleService testFunctionModuleService;
    @Autowired
    private TestFunctionStepService testFunctionStepService;

    // conveyTestStep2ExeStep 使用 mapper 直接做 IN 批拉，绕开 service 层单条接口的 N+1
    @Autowired
    private TestFunctionModuleMapper testFunctionModuleMapper;
    @Autowired
    private TestFunctionCaseMapper testFunctionCaseMapper;
    @Autowired
    private TestFunctionStepMapper testFunctionStepMapper;

    @Autowired
    private IntegrationProperties integrationProperties;

    @Autowired
    private EmsMessageService emsMessageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteExeStep(String exeFunctionId) {
        // 1. 创建查询条件
        ExeStepExample example = new ExeStepExample();
        example.createCriteria().andExeFunctionIdEqualTo(exeFunctionId);

        List<ExeStepWithBLOBs> list = exeStepMapper.selectByExampleWithBLOBs(example);

        int count = 0;
        for (ExeStepWithBLOBs step : list) {
            step.setDeleted(true);
            exeStepMapper.updateByPrimaryKeySelective(step); // 此时不需要强转，本身就是 WithBLOBs
            count++;
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void conveyTestStep2ExeStep(Integer funId, String exeFunctionId) {
        if (funId == null || exeFunctionId == null) return;

        // 一次拉 module（按 funId）
        TestFunctionModuleExample moduleExample = new TestFunctionModuleExample();
        moduleExample.createCriteria().andFunIdEqualTo(funId);
        List<TestFunctionModule> modules = testFunctionModuleMapper.selectByExample(moduleExample);
        if (modules == null || modules.isEmpty()) return;

        // 一次 IN 拉 case（按 moduleIds）
        List<Integer> moduleIds = new ArrayList<>(modules.size());
        for (TestFunctionModule m : modules) moduleIds.add(m.getModuleId());
        TestFunctionCaseExample caseExample = new TestFunctionCaseExample();
        caseExample.createCriteria().andModuleIdIn(moduleIds);
        List<TestFunctionCase> cases = testFunctionCaseMapper.selectByExample(caseExample);
        if (cases == null || cases.isEmpty()) return;

        // 一次 IN 拉 step（按 caseIds）
        List<Integer> caseIds = new ArrayList<>(cases.size());
        for (TestFunctionCase c : cases) caseIds.add(c.getCaseId());
        TestFunctionStepExample stepExample = new TestFunctionStepExample();
        stepExample.createCriteria().andCaseIdIn(caseIds);
        List<TestFunctionStep> steps = testFunctionStepMapper.selectByExample(stepExample);
        if (steps == null || steps.isEmpty()) return;

        // 三层 N+1 已折叠为 3 次 SQL；下方循环仅做内存拼装 + 单条 insert。
        // TODO 后续再加 batch insert 自定义 mapper（mybatis-generator 默认不生成 batch insert）。
        for (TestFunctionStep step : steps) {
            ExeStepWithBLOBs exeStep = new ExeStepWithBLOBs();
            exeStep.setExeStepId(UUID.randomUUID().toString());
            exeStep.setExeFunctionId(exeFunctionId);
            exeStep.setStepId(step.getStepId());
            String desc = step.getStepDescription();
            if (desc == null || desc.trim().isEmpty()) {
                desc = step.getStepName();
            }
            exeStep.setStepDescription(desc);
            exeStep.setOperation(step.getStepOperation());
            exeStep.setOperationObject(step.getStepObj());
            exeStep.setOperationContent(step.getStepPurpose());
            exeStep.setExeStatus(TestPlanStatusContants.PLAN_STATUS_UNEXE);
            exeStep.setDeleted(false);
            try {
                Map<String, Object> cmd = new LinkedHashMap<>();
                cmd.put("topic", step.getStepObj());
                if (step.getStepCommandExample() != null && !step.getStepCommandExample().trim().isEmpty()) {
                    cmd.put("example", objectMapper.readValue(step.getStepCommandExample(), Object.class));
                } else {
                    cmd.put("example", new LinkedHashMap<String, Object>());
                }
                if (step.getStepCommandParams() != null && !step.getStepCommandParams().trim().isEmpty()) {
                    cmd.put("params", objectMapper.readValue(step.getStepCommandParams(),
                            new TypeReference<Map<String, Object>>() {}));
                } else {
                    cmd.put("params", new LinkedHashMap<String, Object>());
                }
                exeStep.setCommandData(objectMapper.writeValueAsString(cmd));
            } catch (Exception e) {
                exeStep.setCommandData("{\"topic\":\"\",\"example\":{},\"params\":{}}");
            }
            exeStepMapper.insertSelective(exeStep);
        }
    }

    /**
     * 获取测试步骤。早期微服务版本带 Commands / Devices / Drivers 的拼装，
     * 模块化单体演进后裁剪为基础结构，相关聚合后续按需重建。
     */
    public Response listExeSteps(String exeFunctionId) {
        ExeStepExample example = new ExeStepExample();
        example.createCriteria().andExeFunctionIdEqualTo(exeFunctionId);
        example.setOrderByClause("step_order ASC, step_level ASC");
        return ResponseFactory.success(exeStepMapper.selectByExampleWithBLOBs(example));
    }

    /**
     * 更新指定测试步骤下正在执行的步骤为暂停状态
     */
    @Transactional(rollbackFor = Exception.class)
    public Response updateStepExeToPause(String exeFunctionId) {
        ExeStepWithBLOBs updateRecord = new ExeStepWithBLOBs();
        updateRecord.setExeStatus(TestPlanStatusContants.PLAN_STATUS_PAUSE);

        ExeStepExample example = new ExeStepExample();
        List<Integer> targetStatuses = new ArrayList<>();
        targetStatuses.add(TestPlanStatusContants.PLAN_STATUS_EXEING);
        // targetStatuses.add(StepStatusEnum.OPERATED.getKey());

        example.createCriteria()
                .andExeFunctionIdEqualTo(exeFunctionId)
                .andExeStatusIn(targetStatuses);

        try {
            int rows = exeStepMapper.updateByExampleSelective(updateRecord, example);

            return ResponseFactory.success("更新成功，暂停了 " + rows + " 个步骤");

        } catch (Exception e) {
            throw new RuntimeException("暂停步骤异常", e);
        }
    }

    /**
     * 获取指定步骤
     */
    public ExeStep getExeStep(String exeStepId) {
        return exeStepMapper.selectByPrimaryKey(exeStepId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateStepStatusByOption(String exeStepId, String option) {

        if (exeStepId == null || option == null) {
            return ResponseFactory.success("参数错误");
        }

        int targetStatus;

        if ("runStep".equals(option)) {
            targetStatus = TestPlanStatusContants.PLAN_STATUS_EXEING;
        } else if ("Pause".equals(option)) {
            targetStatus = TestPlanStatusContants.PLAN_STATUS_PAUSE;
        } else if ("doFinish".equals(option)) {
            targetStatus = TestPlanStatusContants.PLAN_STATUS_FINISH;
        } else {
            return ResponseFactory.failure("无效的操作选项: " + option);
        }

        ExeStepWithBLOBs updateRecord = new ExeStepWithBLOBs();
        updateRecord.setExeStepId(exeStepId);
        updateRecord.setExeStatus(targetStatus);

        int result = exeStepMapper.updateByPrimaryKeySelective(updateRecord);

        if (result > 0) {
            return ResponseFactory.success("状态更新成功，更新记录条目：" + result);
        } else {
            return ResponseFactory.failure("更新失败，未找到对应记录");
        }
    }

    /**
     * 更新手动结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateManualResult(String exeStepId, String manualResult) {
        ExeStepWithBLOBs exeStep = (ExeStepWithBLOBs) exeStepMapper.selectByPrimaryKey(exeStepId);
        if (exeStep != null) {
            exeStep.setStepResult(manualResult);
            exeStep.setIsManual(true);
            // exeStep.setJudgeResult(0);
            return exeStepMapper.updateByPrimaryKeySelective(exeStep) > 0;
        }
        return false;
    }

    /**
     * 更新自动获取结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAutoResult(String exeStepId, String autoResult) {
        ExeStepWithBLOBs exeStep = (ExeStepWithBLOBs) exeStepMapper.selectByPrimaryKey(exeStepId);
        if (exeStep != null) {
            exeStep.setStepResult(autoResult);
            exeStep.setIsManual(false);
            // exeStep.setJudgeResult(0);
            return exeStepMapper.updateByPrimaryKeySelective(exeStep) > 0;
        }
        return false;
    }

    /*
     * 已知未实现方法（依赖尚未建模的 entity，等待后续 DDL/实体补全）：
     *   executeStepCommandv2  — 依赖 Device / DeviceCommand 实体
     *   getChartData          — 依赖 PaintModel / DeviceCommand 实体
     * 这两个方法既未在 ExeStepService 接口暴露，也无任何调用方；保留 TODO 占位，
     * 实体补齐后再补 endpoint，避免现在就先写半截 controller 误导前端。
     */

    /**
     * 通过 plan_id 查询该计划下所有 ExeStep。两段查询而非 join：
     * 先用 plan_id 取 ExeFunction 的所有 id，再 IN 查询 ExeStep；避免引入新的自定义 SQL。
     */
    public List<ExeStep> getExeStepByPlan(String planId) {
        if (planId == null || planId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        com.hengtiansoft.fastop.model.planner.entity.ExeFunctionExample funcExample =
                new com.hengtiansoft.fastop.model.planner.entity.ExeFunctionExample();
        funcExample.createCriteria().andPlanIdEqualTo(planId);
        java.util.List<com.hengtiansoft.fastop.model.planner.entity.ExeFunction> funcs =
                exeFunctionMapper.selectByExample(funcExample);
        if (funcs == null || funcs.isEmpty()) {
            return new ArrayList<>();
        }
        java.util.List<String> funcIds = funcs.stream()
                .map(com.hengtiansoft.fastop.model.planner.entity.ExeFunction::getExeFunctionId)
                .collect(java.util.stream.Collectors.toList());

        ExeStepExample stepExample = new ExeStepExample();
        stepExample.createCriteria().andExeFunctionIdIn(funcIds);
        java.util.List<ExeStep> steps = exeStepMapper.selectByExample(stepExample);
        return steps == null ? new ArrayList<>() : steps;
    }

    public Response doV1(ExeStepCommand exeStepCommand) {
        if (exeStepCommand == null || exeStepCommand.getExeStepId() == null) {
            return ResponseFactory.failure("参数错误：exeStepId 不能为空");
        }
        boolean legacyUrl = exeStepCommand.getUrl() != null && !exeStepCommand.getUrl().trim().isEmpty();
        if (legacyUrl) {
            executor.execute(() -> {
                try {
                    processAsyncStep(exeStepCommand);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            String emsBase = integrationProperties.getEmsUrl();
            if (emsBase == null || emsBase.trim().isEmpty()) {
                return ResponseFactory.failure("未配置 fastop.integration.ems-url 且请求未携带 url");
            }
            String sid = exeStepCommand.getExeStepId();
            executor.execute(() -> processAsyncEms(sid));
        }
        return ResponseFactory.success("指令已接收，正在异步处理中");
    }

    @Override
    public Response previewEmsMessage(String exeStepId) {
        if (exeStepId == null || exeStepId.isEmpty()) {
            return ResponseFactory.failure("exeStepId 不能为空");
        }
        ExeStepWithBLOBs step = exeStepMapper.selectByPrimaryKey(exeStepId);
        if (step == null) {
            return ResponseFactory.failure("步骤不存在");
        }
        MessageEtt msg = emsMessageService.buildFromExeStep(step);
        return ResponseFactory.success(msg);
    }

    private void processAsyncEms(String exeStepId) {
        try {
            ExeStepWithBLOBs step = exeStepMapper.selectByPrimaryKey(exeStepId);
            if (step == null) {
                return;
            }
            MessageEtt msg = emsMessageService.buildFromExeStep(step);
            String base = integrationProperties.getEmsUrl().trim();
            if (base.endsWith("/")) {
                base = base.substring(0, base.length() - 1);
            }
            String path = integrationProperties.getEmsSendPath();
            if (path == null || path.isEmpty()) {
                path = "/addDefault";
            }
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            String url = base + path;
            restTemplate.postForObject(url, msg, String.class);
        } catch (Exception e) {
            LoggerFactory.getLogger(ExeStepServiceImpl.class).error("EMS 调用失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response saveLog(ExeLog exeLog) {
        if (exeLog == null) {
            return ResponseFactory.failure("日志内容不能为空");
        }
        if (exeLog.getLogId() == null || exeLog.getLogId().isEmpty()) {
            exeLog.setLogId(UUID.randomUUID().toString());
        }
        if (exeLog.getCreateTime() == null) {
            exeLog.setCreateTime(new Date());
        }
        try {
            int rows = exeLogMapper.insertSelective(exeLog);
            return rows > 0 ? ResponseFactory.success("日志保存成功") : ResponseFactory.failure("日志保存失败");
        } catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(ExeStepServiceImpl.class).warn("执行日志落库失败（exe_log 表见 dataset/260302.sql 完整 dump）: {}", e.getMessage());
            return ResponseFactory.success("日志已接收");
        }
    }

    @Override
    public Response listExeLogs(String stepId, String planId, Date startTime, Date endTime, int page, int size) {
        int offset = (page - 1) * size;
        if (offset < 0) offset = 0;
        if (size <= 0 || size > 500) size = 20;
        java.util.List<ExeLog> list = exeLogMapper.selectByCondition(stepId, planId, startTime, endTime, offset, size);
        long total = exeLogMapper.countByCondition(stepId, planId, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return ResponseFactory.builder().withData(result).withTotalNum(total).build();
    }

    private String processAsyncStep(ExeStepCommand sourceCommand) {
        String stepId = sourceCommand.getExeStepId();

        ExeStep exeStep = exeStepMapper.selectByPrimaryKey(stepId);

        if (exeStep == null) {
            return "未找到对应的 ExeStep, ID: " + stepId;
        }

        ExeStepCommandDto dto = new ExeStepCommandDto();
        dto.setExeStep(exeStep);
        dto.setCommand(sourceCommand.getCommand());

        String targetUrl = sourceCommand.getUrl();
        try {
            String result = restTemplate.postForObject(targetUrl, dto, String.class);
            return "发送 DTO 成功. URL: " + targetUrl + " ,Result: " + result;
        } catch (Exception e) {
            throw e;
        }
    }
}
