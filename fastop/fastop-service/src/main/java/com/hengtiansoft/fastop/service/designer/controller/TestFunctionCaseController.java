package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCase;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "模块子用例管理")
@RequestMapping("/designer/case")
@RestController
public class TestFunctionCaseController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestFunctionCaseService testFunctionCaseService;

    @ApiOperation("增加子用例")
    @PostMapping("/add")
    public Response addTestFunctionCase(@RequestBody TestFunctionCase testFunctionCase) {
        LOG.info("增加新子用例, caseName: {}", testFunctionCase.getCaseName());
        return testFunctionCaseService.add(testFunctionCase);
    }

    @ApiOperation("修改子用例")
    @PostMapping("/update")
    public Response updateTestFunctionCase(@RequestBody TestFunctionCase testFunctionCase) {
        LOG.info("修改子用例, caseId: {}", testFunctionCase.getCaseId());
        return testFunctionCaseService.update(testFunctionCase);
    }

    @ApiOperation("删除子用例")
    @PostMapping("/delete")
    public Response deleteTestFunctionCase(@RequestParam Integer caseId) {
        LOG.info("删除子用例, caseId: {}", caseId);
        return testFunctionCaseService.delete(caseId);
    }

    @ApiOperation("根据caseId查询子用例")
    @GetMapping("/get/{caseId}")
    public Response getByCaseId(@PathVariable Integer caseId) {
        LOG.info("开始查询子用例, caseId: {}", caseId);
        return testFunctionCaseService.getByCaseId(caseId);
    }

    @ApiOperation("根据moduleId查询子用例列表")
    @GetMapping("/listByModuleId")
    public Response listByFunId(@RequestParam Integer moduleId) {
        LOG.info("开始查询子用例列表, funId: {}", moduleId);
        return testFunctionCaseService.getByModuleId(moduleId);
    }

    @ApiOperation("查询全部的子用例")
    @GetMapping("/listAll")
    public Response listAll() {
        LOG.info("查询所有子用例");
        return testFunctionCaseService.listAll();
    }

}
