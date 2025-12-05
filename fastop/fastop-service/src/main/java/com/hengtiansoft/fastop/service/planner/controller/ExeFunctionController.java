package com.hengtiansoft.fastop.service.planner.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.service.planner.service.ExeFunctionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api("测试执行功能管理模块")
@RestController
@RequestMapping("/exeFunction")
public class ExeFunctionController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExeFunctionService exeFunctionService;

    @ApiOperation("获取指定测试作业计划下正在执行的功能")
    @GetMapping("/testFunctions/inexe/{planId}")
    public Response getExeFunctionInExe(@PathVariable("planId") String planId){
        LOG.info("start getting ExeFunctionInExe by planId",planId);
        return exeFunctionService.getExeFunctionInExeListByPlanId(planId);
    }

    @ApiOperation("获取指定测试功能ID的功能")
    @GetMapping("/testFunctions/id/{functionId}")
    public Response getExeFunctionById(@PathVariable("functionId") String functionId) {
        LOG.info("start getting ExeFunctionById by functionId",functionId);
        return exeFunctionService.getExeFunctionByFunctionId(functionId);
    }

}
