package com.hengtiansoft.fastop.service.planner.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanDelBatchDto;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanRequestDto;
import com.hengtiansoft.fastop.service.planner.service.TestPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "测试计划管理")
@RequestMapping("/planner/plan")
@RestController
public class TestPlanController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestPlanService testPlanService;

    @ApiOperation("创建新的计划")
    @PostMapping("/createTestPlan")
    public Response createTestPlan(@RequestBody TestPlanRequestDto testPlanRequestDto) {
        LOG.info("正在创建新的测试计划:", testPlanRequestDto);
        return testPlanService.createTestPlan(testPlanRequestDto);
    }

    @ApiOperation("更新计划")
    @PostMapping("/updateTestPlan")
    public Response updateTestPlan(@RequestBody TestPlanRequestDto testPlanRequestDto) {
        LOG.info("正在更新计划:", testPlanRequestDto);
        return testPlanService.updateTestPlan(testPlanRequestDto);
    }

    //TODO 这里添加备注和更新计划实际使用的逻辑是一样的，需要控制权限不同，
    // 添加备注是查看权限；更新计划是编辑权限
    @ApiOperation("添加备注")
    @PostMapping("/remarkTestPlan")
    public Response remarkTsetPlan(@RequestBody TestPlanRequestDto testPlanRequestDto) {
        LOG.info("正在添加备注:", testPlanRequestDto);
        return testPlanService.updateTestPlan(testPlanRequestDto);
    }

    @ApiOperation("删除单个测试计划")
    @DeleteMapping("/deleteTestPlan/{planId}")
    public Response deleteSingleTestPlan(@PathVariable("planId") String planId){
        LOG.info("正在删除测试计划：",planId);
        return testPlanService.deleteSingleTestPlan(planId);
    }

    @ApiOperation("批量删除测试计划")
    @DeleteMapping("/deleteTestPlanWithBatch")
    public Response deleteTestPlanWithBatch(@RequestBody TestPlanDelBatchDto testPlanDelBatchDto) {
        LOG.info("正在批量删除测试计划：", testPlanDelBatchDto);
        return testPlanService.deleteBatchTestPlan(testPlanDelBatchDto);
    }

    @ApiOperation("查找全部")
    @GetMapping("/listAll")
    public Response listAll(){
        return testPlanService.listAll();
    }

}
