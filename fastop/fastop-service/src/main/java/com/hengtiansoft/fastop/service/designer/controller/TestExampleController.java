package com.hengtiansoft.fastop.service.designer.controller;


import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.service.designer.service.TestExampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "测试用例")
@RequestMapping("/testExample")
@RestController
public class TestExampleController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestExampleService testExampleService;

    @ApiOperation("更新测试用例状态")
    @PostMapping("/updateAll")
    public Response updateAll(){
        LOG.info("开始全量更新");
        return testExampleService.UpdateAll();
    }

}
