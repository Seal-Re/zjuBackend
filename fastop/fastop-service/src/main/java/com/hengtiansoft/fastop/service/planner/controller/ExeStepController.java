package com.hengtiansoft.fastop.service.planner.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.entity.ExeStep;
import com.hengtiansoft.fastop.model.planner.utils.ExeStepCommand;
import com.hengtiansoft.fastop.service.planner.service.ExeStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api("测试执行功能管理模块")
@RestController
@RequestMapping("/exeFunction")
public class ExeStepController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExeStepService exeStepService;

    @ApiOperation("获取指定测试作业计划下的步骤")
    @GetMapping("/getinexe/{functionId}")
    public Response getExeFunctionInExe(@PathVariable("functionId") String functionId){
        LOG.info("start getting ExeStepInExe by functionId",functionId);
        return exeStepService.listExeSteps(functionId);
    }

    @ApiOperation("批量暂停步骤")
    @PostMapping("/pause/{exeFunctionId}")
    public Response updateStepExeToPause(@PathVariable("exeFunctionId") String exeFunctionId) {
        LOG.info("暂停指令已收到，开始批量暂停functionId为{} 下的所有步骤",exeFunctionId);
        return exeStepService.updateStepExeToPause(exeFunctionId);
    }

    @ApiOperation("步骤单体操作")
    @PostMapping("/stepOperate")
    public Response stepOperate(@RequestBody Map<String, String> params) {
        String exeStepId = params.get("exeStepId");
        String option = params.get("option");
        LOG.info("开始对步骤{} 进行指令{}",exeStepId,option);
        return exeStepService.updateStepStatusByOption(exeStepId, option);
    }

    @ApiOperation("步骤执行，发送设备指令v1")
    @PostMapping("/do")
    public Response doV1(@RequestBody ExeStepCommand exeStepCommand) {
        LOG.info("接收到执行指令，开始操作");
        return exeStepService.doV1(exeStepCommand);
    }
}
