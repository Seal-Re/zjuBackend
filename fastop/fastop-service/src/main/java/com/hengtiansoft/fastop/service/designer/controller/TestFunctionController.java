package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.model.designer.dto.TestFunctionInfoRequestDto;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionService;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "工艺模块管理")
@RequestMapping("/designer/testFunction")
@RestController
public class TestFunctionController {

    @Autowired
    private TestFunctionService testFunctionService;

    @ApiOperation("新增模块")
    @PostMapping("/add")
    public Response addTestFunction(@Valid @RequestBody TestFunctionInfoRequestDto tFunctionInfo) {
        log.info("addTestFunction: {}", tFunctionInfo);
        return testFunctionService.add(tFunctionInfo);
    }

    @ApiOperation("修改模块信息")
    @PostMapping("/update")
    public Response updateTestFunction(@Valid @RequestBody TestFunctionInfoRequestDto tFunctionInfo) {
        log.info("updateTestFunction: {}", tFunctionInfo);
        return testFunctionService.update(tFunctionInfo);
    }

    @ApiOperation("提交模块")
    @PostMapping("/submit")
    public Response submitTestFunction(@RequestBody Integer funId) {
        log.info("submitTestFunction funId={}", funId);
        return testFunctionService.submit(funId);
    }

    @ApiOperation("删除模块")
    @PostMapping("/delete/{funId}")
    public Response deleteTestFunction(@ApiParam("功能ID") @PathVariable Integer funId) {
        log.warn("deleteTestFunction funId={}", funId);
        return testFunctionService.delete(funId);
    }

    @ApiOperation("根据ID查询单个模块详情")
    @GetMapping("/get/{funId}")
    public Response getTestFunctionById(@ApiParam("功能ID") @PathVariable Integer funId) {
        log.info("getTestFunctionById funId={}", funId);
        return testFunctionService.getById(funId);
    }

    @ApiOperation("根据基础试验ID查询模块列表")
    @GetMapping("/listByBaseId")
    public Response listTestFunctionByBaseId(@ApiParam("基础试验ID") @RequestParam Integer testBaseId) {
        log.info("listTestFunctionByBaseId testBaseId={}", testBaseId);
        return testFunctionService.listByTestBaseId(testBaseId);
    }

    @ApiOperation("查询全部模块列表")
    @GetMapping("/listAll")
    public Response listAllTestFunction() {
        log.info("listAllTestFunction");
        return testFunctionService.listAll();
    }

    @ApiOperation("模块列表（支持可选 testBaseId 筛选）")
    @GetMapping("/list")
    public Response listTestFunction(@RequestParam(required = false) Integer testBaseId) {
        log.info("listTestFunction testBaseId={}", testBaseId);
        return testBaseId != null
                ? testFunctionService.listByTestBaseId(testBaseId)
                : testFunctionService.listAll();
    }

    @ApiOperation("通用/条件查询模块")
    @PostMapping("/query")
    public Response queryTestFunction(@RequestBody TestFunctionInfoRequestDto tFunctionInfo) {
        log.info("queryTestFunction: {}", tFunctionInfo);
        return testFunctionService.query(tFunctionInfo);
    }

    @ApiOperation("审批")
    @PostMapping("/check")
    public Response checkTestFunction(@RequestParam Integer funId, @RequestParam String checkWorker, @RequestParam Integer level) {
        log.info("checkTestFunction funId={} checkWorker={} level={}", funId, checkWorker, level);
        return testFunctionService.check(funId, checkWorker, level);
    }

    @ApiOperation("获取审批列表")
    @GetMapping("/getCheckTestFunction")
    public Response getCheckTestFunction() {
        return testFunctionService.getCheckTestFunction();
    }
}
