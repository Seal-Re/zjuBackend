package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.service.designer.service.BaseStructService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api("存储基础构型")
@RequestMapping("/base")
public class BaseStructController {

    @Autowired
    public BaseStructService baseStructService;

    @GetMapping("/listAllBaseStruct")
    @ApiOperation("获取基础构型")
    public Response listAllBaseStruct(){
        return baseStructService.listAllBaseStruct();
    }

    @GetMapping("/listAllBaseStructAndId")
    @ApiOperation("获取基础构型以及BaseId")
    public Response listAllBaseStructAndId(){
        return baseStructService.listAllBaseStructAndId();
    }
}
