package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStep;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "模块步骤管理")
@RequestMapping("/designer/step")
@RestController
public class TestFunctionStepController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestFunctionStepService testFunctionStepService;

    @ApiOperation("增加步骤")
    @PostMapping("/add")
    public Response addTestFunctionStep(@RequestBody TestFunctionStep testFunctionStep) {
        LOG.info("增加新步骤, StepName: {}", testFunctionStep.getStepName());
        return testFunctionStepService.add(testFunctionStep);
    }

    @ApiOperation("修改步骤")
    @PostMapping("/update")
    public Response updateTestFunctionStep(@RequestBody TestFunctionStep testFunctionStep) {
        LOG.info("修改步骤, StepId: {}", testFunctionStep.getStepId());
        return testFunctionStepService.update(testFunctionStep);
    }

    @ApiOperation("删除步骤")
    @PostMapping("/delete")
    public Response deleteTestFunctionStep(@RequestParam Integer StepId) {
        LOG.info("删除步骤, StepId: {}", StepId);
        return testFunctionStepService.delete(StepId);
    }

    @ApiOperation("根据StepId查询步骤")
    @GetMapping("/get/{StepId}")
    public Response getByStepId(@PathVariable Integer StepId) {
        LOG.info("开始查询步骤, StepId: {}", StepId);
        return testFunctionStepService.getByStepId(StepId);
    }

    @ApiOperation("根据caseId查询步骤列表")
    @GetMapping("/listByCaseId")
    public Response listByFunId(@RequestParam Integer caseId) {
        LOG.info("开始查询步骤列表, funId: {}", caseId);
        return testFunctionStepService.getByCaseId(caseId);
    }

    @ApiOperation("查询全部的步骤")
    @GetMapping("/listAll")
    public Response listAll() {
        LOG.info("查询所有步骤");
        return testFunctionStepService.listAll();
    }


}