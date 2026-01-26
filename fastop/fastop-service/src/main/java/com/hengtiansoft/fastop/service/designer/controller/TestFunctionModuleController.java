package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModule;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(tags = "模块用例管理")
@RequestMapping("/designer/module")
@RestController
public class TestFunctionModuleController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestFunctionModuleService testFunctionModuleService;

    @ApiOperation("增加用例")
    @PostMapping("/add")
    public Response addTestFunctionModule(@RequestBody TestFunctionModule testFunctionModule) {
        LOG.info("增加新用例, ModuleName: {}", testFunctionModule.getModuleName());
        return testFunctionModuleService.add(testFunctionModule);
    }

    @ApiOperation("修改用例")
    @PostMapping("/update")
    public Response updateTestFunctionModule(@RequestBody TestFunctionModule testFunctionModule) {
        LOG.info("修改用例, ModuleId: {}", testFunctionModule.getModuleId());
        return testFunctionModuleService.update(testFunctionModule);
    }

    @ApiOperation("删除用例")
    @PostMapping("/delete")
    public Response deleteTestFunctionModule(@RequestParam Integer ModuleId) {
        LOG.info("删除用例, ModuleId: {}", ModuleId);
        return testFunctionModuleService.delete(ModuleId);
    }

    @ApiOperation("根据ModuleId查询用例")
    @GetMapping("/get/{ModuleId}")
    public Response getByModuleId(@PathVariable Integer ModuleId) {
        LOG.info("开始查询用例, ModuleId: {}", ModuleId);
        return testFunctionModuleService.getByModuleId(ModuleId);
    }

    @ApiOperation("根据funId查询用例列表")
    @GetMapping("/listByFunId")
    public Response listByFunId(@RequestParam Integer funId) {
        LOG.info("开始查询用例列表, funId: {}", funId);
        return testFunctionModuleService.getByFunId(funId);
    }

    @ApiOperation("查询全部的用例")
    @GetMapping("/listAll")
    public Response listAll() {
        LOG.info("查询所有用例");
        return testFunctionModuleService.listAll();
    }

    @ApiOperation("查询用例树形结构")
    @GetMapping("/treeByFunId")
    public Response getTreeByFunId(@RequestParam Integer funId) {
        LOG.info("查询用例树形结构, funId: {}", funId);
        return testFunctionModuleService.getTreeByFunId(funId);
    }
}
