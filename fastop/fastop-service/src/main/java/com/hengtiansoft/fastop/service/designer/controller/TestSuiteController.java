package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.dto.TestSuiteRequestDto;
import com.hengtiansoft.fastop.service.designer.service.TestSuiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "测试清单管理模块")
@RequestMapping("/designer/testSuite")
@RestController
public class TestSuiteController {

    @Autowired
    private TestSuiteService testSuiteService;

    @ApiOperation("新增清单")
    @PostMapping("/add")
    public Response addTestSuite(@RequestBody TestSuiteRequestDto testSuiteRequestDto) {
        log.info("addTestSuite: {}", testSuiteRequestDto);
        return testSuiteService.add(testSuiteRequestDto);
    }

    @ApiOperation("修改清单")
    @PostMapping("/update")
    public Response updateTestSuite(@RequestBody TestSuiteRequestDto testSuiteRequestDto) {
        log.info("updateTestSuite: {}", testSuiteRequestDto);
        return testSuiteService.update(testSuiteRequestDto);
    }

    @ApiOperation("提交清单")
    @PostMapping("/submit")
    public Response submitTestSuite(@RequestBody Integer suiteId) {
        log.info("submitTestSuite suiteId={}", suiteId);
        return testSuiteService.submit(suiteId);
    }

    @ApiOperation("删除清单")
    @PostMapping("/delete")
    public Response deleteTestSuite(@RequestBody Integer suiteId) {
        log.info("deleteTestSuite suiteId={}", suiteId);
        return testSuiteService.delete(suiteId);
    }

    @ApiOperation("根据ID查询单个清单详情")
    @GetMapping("/get/{suiteId}")
    public Response getTestSuiteById(@ApiParam("清单Id") @PathVariable Integer suiteId) {
        log.info("getTestSuiteById suiteId={}", suiteId);
        return testSuiteService.getById(suiteId);
    }

    @ApiOperation("根据BaseId查询清单列表")
    @GetMapping("/listByBaseId")
    public Response listTestSuiteByBaseId(@RequestParam Integer testBaseId) {
        log.info("listTestSuiteByBaseId testBaseId={}", testBaseId);
        return testSuiteService.listByBaseId(testBaseId);
    }

    @ApiOperation("查询全部清单")
    @GetMapping("/listAll")
    public Response listAll() {
        log.info("listAll test suites");
        return testSuiteService.listAll();
    }

    @ApiOperation("审批")
    @PostMapping("/check")
    public Response checkTestFunction(@RequestParam Integer suiteId, @RequestParam String checkWorker, @RequestParam Integer level) {
        return testSuiteService.check(suiteId, checkWorker, level);
    }

    @ApiOperation("获取审批列表")
    @GetMapping("/getCheckTestSuite")
    public Response getCheckTestSuite() {
        return testSuiteService.getCheckTestSuite();
    }
}
