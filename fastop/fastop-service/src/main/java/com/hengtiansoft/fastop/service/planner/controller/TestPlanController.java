package com.hengtiansoft.fastop.service.planner.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanDelBatchDto;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanRequestDto;
import com.hengtiansoft.fastop.service.planner.service.TestPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "测试计划管理")
@RequestMapping("/planner/plan")
@RestController
public class TestPlanController {

    @Autowired
    private TestPlanService testPlanService;

    @ApiOperation("创建新的计划")
    @PostMapping("/createTestPlan")
    public Response createTestPlan(@RequestBody TestPlanRequestDto testPlanRequestDto) {
        log.info("正在创建新的测试计划:", testPlanRequestDto);
        return testPlanService.createTestPlan(testPlanRequestDto);
    }

    @ApiOperation("更新计划")
    @PostMapping("/updateTestPlan")
    public Response updateTestPlan(@RequestBody TestPlanRequestDto testPlanRequestDto) {
        log.info("正在更新计划:", testPlanRequestDto);
        return testPlanService.updateTestPlan(testPlanRequestDto);
    }

    // 添加备注仅需查看权限，不允许修改计划结构字段；独立方法与 updateTestPlan 区分
    @ApiOperation("添加备注")
    @PostMapping("/remarkTestPlan")
    public Response remarkTestPlan(@RequestBody TestPlanRequestDto testPlanRequestDto) {
        log.info("正在添加备注: planId={}", testPlanRequestDto.getPlanId());
        return testPlanService.remarkTestPlan(testPlanRequestDto.getPlanId(), testPlanRequestDto.getRemark());
    }

    @ApiOperation("删除单个测试计划")
    @DeleteMapping("/deleteTestPlan/{planId}")
    public Response deleteSingleTestPlan(@PathVariable("planId") String planId){
        log.info("正在删除测试计划：",planId);
        return testPlanService.deleteSingleTestPlan(planId);
    }

    @ApiOperation("批量删除测试计划")
    @DeleteMapping("/deleteTestPlanWithBatch")
    public Response deleteTestPlanWithBatch(@RequestBody TestPlanDelBatchDto testPlanDelBatchDto) {
        log.info("正在批量删除测试计划：", testPlanDelBatchDto);
        return testPlanService.deleteBatchTestPlan(testPlanDelBatchDto);
    }

    @ApiOperation("查找全部")
    @GetMapping("/listAll")
    public Response listAll(){
        return testPlanService.listAll();
    }

    @ApiOperation("派发/同步计划")
    @GetMapping("/dispatch/{planId}")
    public Response dispatchPlan(@PathVariable("planId") String planId){
        log.info("Dispatch plan: {}", planId);
        return testPlanService.dispatchPlan(planId);
    }

    @ApiOperation("开始计划")
    @PostMapping("/start/{planId}")
    public Response startPlan(@PathVariable("planId") String planId){
        log.info("Start plan: {}", planId);
        return testPlanService.startPlan(planId);
    }

    @ApiOperation("暂停计划")
    @PostMapping("/pause/{planId}")
    public Response pausePlan(@PathVariable("planId") String planId){
        log.info("Pause plan: {}", planId);
        return testPlanService.pausePlan(planId);
    }

}
