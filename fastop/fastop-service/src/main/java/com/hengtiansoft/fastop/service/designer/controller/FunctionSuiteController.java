package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.dto.FunSuiteIdConnectDto;
import com.hengtiansoft.fastop.model.designer.dto.FunctionSuiteDeleteDto;
import com.hengtiansoft.fastop.service.designer.service.FunctionSuiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api("构建模块Function和清单Suite的关系表")
@RequestMapping("/functionSuite")
public class FunctionSuiteController {

    @Autowired
    private FunctionSuiteService functionSuiteService;

    @ApiOperation("获取全部模块清单")
    @GetMapping("/listAll")
    public Response listAllFunctionSuite() {
        log.info("listAllFunctionSuite");
        return functionSuiteService.listAllFunctionSuite();
    }

    @ApiOperation("增加模块清单")
    @PostMapping("/createFunctionSuite")
    public Response createFunctionSuite(@RequestBody FunSuiteIdConnectDto funSuiteIdConnectDto) {
        log.info("createFunctionSuite");
        return functionSuiteService.createFunctionSuite(funSuiteIdConnectDto);
    }

    @ApiOperation("删除模块清单")
    @PostMapping("/deleteFunctionSuite")
    public Response deleteFunctionSuite(@RequestBody FunctionSuiteDeleteDto functionSuiteDeleteDto) {
        log.info("deleteFunctionSuite");
        return functionSuiteService.deleteFunctionSuite(functionSuiteDeleteDto);
    }

    @ApiOperation("获取指定测试集和指定测试类型下的所有功能")
    @GetMapping("/rely")
    public Response getRely(@RequestParam Integer suiteId) {
        return functionSuiteService.getRely(suiteId);
    }
}
