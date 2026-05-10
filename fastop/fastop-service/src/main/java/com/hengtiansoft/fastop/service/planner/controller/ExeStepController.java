package com.hengtiansoft.fastop.service.planner.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.utils.ExeStepCommand;
import com.hengtiansoft.fastop.model.planner.utils.ExeLog;
import com.hengtiansoft.fastop.service.planner.service.ExeStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@Api("测试执行功能管理模块")
@RestController
@RequestMapping("/exeStep")
public class ExeStepController {

    @Autowired
    private ExeStepService exeStepService;

    @ApiOperation("获取指定测试作业计划下的步骤")
    @GetMapping("/getinexe/{functionId}")
    public Response getExeFunctionInExe(@PathVariable("functionId") String functionId) {
        log.info("get ExeStepInExe by functionId={}", functionId);
        return exeStepService.listExeSteps(functionId);
    }

    @ApiOperation("批量暂停步骤")
    @PostMapping("/pause/{exeFunctionId}")
    public Response updateStepExeToPause(@PathVariable("exeFunctionId") String exeFunctionId) {
        log.info("pause steps under exeFunctionId={}", exeFunctionId);
        return exeStepService.updateStepExeToPause(exeFunctionId);
    }

    @ApiOperation("步骤单体操作")
    @PostMapping("/stepOperate")
    public Response stepOperate(@RequestBody Map<String, String> params) {
        String exeStepId = params.get("exeStepId");
        String option = params.get("option");
        log.info("stepOperate exeStepId={} option={}", exeStepId, option);
        return exeStepService.updateStepStatusByOption(exeStepId, option);
    }

    @ApiOperation("步骤执行，发送设备指令v1")
    @PostMapping("/do")
    public Response doV1(@RequestBody ExeStepCommand exeStepCommand) {
        log.info("doV1 received");
        return exeStepService.doV1(exeStepCommand);
    }

    @ApiOperation("预览发往 EMS 的报文（MessageEtt）")
    @GetMapping("/ems/preview")
    public Response previewEms(@RequestParam String exeStepId) {
        return exeStepService.previewEmsMessage(exeStepId);
    }

    @ApiOperation("保存步骤执行日志（军检审计落库）")
    @PostMapping("/log/save")
    public Response saveLog(@RequestBody ExeLog exeLog) {
        log.info("saveLog: {}", exeLog);
        return exeStepService.saveLog(exeLog);
    }

    @ApiOperation("分页查询执行日志")
    @GetMapping("/log/list")
    public Response listExeLogs(
            @RequestParam(required = false) String stepId,
            @RequestParam(required = false) String planId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Date start = parseDate(startTime);
        Date end = parseDate(endTime);
        return exeStepService.listExeLogs(stepId, planId, start, end, page, size);
    }

    private static Date parseDate(String str) {
        if (str == null || str.isEmpty()) return null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (Exception e) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(str);
            } catch (Exception e2) {
                return null;
            }
        }
    }
}
