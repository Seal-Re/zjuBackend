package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.dto.TestSuiteRequestDto;
import com.hengtiansoft.fastop.model.designer.entity.TestSuite;
import com.hengtiansoft.fastop.service.designer.service.TestSuiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "测试清单管理模块")
@RequestMapping("/designer/testSuite")
@RestController
public class TestSuiteController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestSuiteService testSuiteService;

    @ApiOperation("新增清单")
    @PostMapping("/add")
    public Response addTestSuite(@RequestBody TestSuiteRequestDto testSuiteRequestDto) {
        LOG.info("开始新增清单: {}", testSuiteRequestDto);
        return testSuiteService.add(testSuiteRequestDto);
    }

    @ApiOperation("修改清单")
    @PostMapping("/update")
    public Response updateTestSuite(@RequestBody TestSuiteRequestDto testSuiteRequestDto) {
        LOG.info("开始修改清单: {}", testSuiteRequestDto);
        return testSuiteService.update(testSuiteRequestDto);
    }

    @ApiOperation("删除清单")
    @PostMapping("/delete")
    public Response deleteTestSuite(@RequestBody Integer suiteId) {
        LOG.info("开始删除清单: {}", suiteId);
        return testSuiteService.delete(suiteId);
    }

    @ApiOperation("根据ID查询单个清单详情")
    @GetMapping("/get/{suiteId}")
    public Response getTestSuiteById(@ApiParam("清单Id")@PathVariable Integer suiteId) {
        LOG.info("查询清单详情, Id: {}", suiteId);
        return testSuiteService.getById(suiteId);
    }

    @ApiOperation("根据BaseId查询清单列表")
    @GetMapping("/listByBaseId")
    public Response listTestSuiteByBaseId(@RequestParam Integer testBaseId) {
        LOG.info("开始查询列表, BaseId: {}", testBaseId);
        return testSuiteService.listByBaseId(testBaseId);
    }

    @ApiOperation("查询全部清单")
    @GetMapping("/listAll")
    public Response listAll() {
        LOG.info("开始查询");
        return testSuiteService.listAll();
    }

    @ApiOperation("审批")
    @PostMapping("/check")
    public Response checkTestFunction(@RequestParam Integer suiteId, @RequestParam String checkWorker, @RequestParam Integer level) {
        return testSuiteService.check(suiteId, checkWorker, level);
    }


}
