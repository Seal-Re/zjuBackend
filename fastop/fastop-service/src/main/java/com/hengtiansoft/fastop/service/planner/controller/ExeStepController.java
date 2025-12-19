package com.hengtiansoft.fastop.service.planner.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.service.planner.service.ExeStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api("测试执行功能管理模块")
@RestController
@RequestMapping("/exeFunction")
public class ExeStepController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
/*
    @Override
    private ExeStepService exeStepService;
*/

}
