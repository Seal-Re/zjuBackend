package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.model.designer.dto.TestFunctionInfoRequestDto;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;


@Slf4j
@Api(tags = "工艺模块管理")
@RequestMapping("/designer/testFunction")
@RestController
public class TestFunctionController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestFunctionService testFunctionService;

    @ApiOperation("新增模块")
    @PostMapping("/add")
    public Response addTestFunction(@RequestBody TestFunctionInfoRequestDto tFunctionInfo) {
        LOG.info("开始新增模块: {}", tFunctionInfo);
        return testFunctionService.add(tFunctionInfo);
    }

    @ApiOperation("修改模块信息")
    @PostMapping("/update")
    public Response updateTestFunction(@RequestBody TestFunctionInfoRequestDto tFunctionInfo) {
        LOG.info("开始修改模块，ID: {}", tFunctionInfo.getFunId());
        return testFunctionService.update(tFunctionInfo);
    }

    @ApiOperation("删除模块")
    @PostMapping("/delete")
    public Response deleteTestFunction(@ApiParam("功能ID") @PathVariable Integer funId) {
        LOG.warn("开始删除模块，ID: {}", funId);
        return testFunctionService.delete(funId);
    }

    @ApiOperation("根据ID查询单个模块详情")
    @GetMapping("/get/{funId}")
    public Response getTestFunctionById(@ApiParam("功能ID") @PathVariable Integer funId) {
        LOG.debug("查询模块详情，ID: {}", funId);
        return testFunctionService.getById(funId);
    }

    @ApiOperation("根据基础试验ID查询模块列表")
    @GetMapping("/listByBaseId")
    public Response listTestFunctionByBaseId(@ApiParam("基础试验ID") @RequestParam Integer testBaseId) {
        LOG.debug("查询基础试验ID下的模块列表: {}", testBaseId);
        return testFunctionService.listByTestBaseId(testBaseId);
    }

    @ApiOperation("查询全部模块列表")
    @GetMapping("/listAll")
    public Response listAllTestFunction() {
        LOG.debug("查询全部模块列表");
        return testFunctionService.listAll();
    }

    @ApiOperation("通用/条件查询模块")
    @GetMapping("/query")
    public Response queryTestFunction(@RequestBody TestFunctionInfoRequestDto tFunctionInfo) {
        LOG.debug("执行通用查询: {}", tFunctionInfo);
        return testFunctionService.query(tFunctionInfo);
    }

    @ApiOperation("审批")
    @PostMapping("/check")
    public Response checkTestFunction(@RequestParam Integer funId, @RequestParam String checkWorker, @RequestParam Integer level) {
        return testFunctionService.check(funId, checkWorker, level);
    }

    @ApiOperation("获取审批列表")
    @GetMapping("/getCheckTestFunction")
    public Response getCheckTestFunction() {
        return testFunctionService.getCheckTestFunction();
    }
}