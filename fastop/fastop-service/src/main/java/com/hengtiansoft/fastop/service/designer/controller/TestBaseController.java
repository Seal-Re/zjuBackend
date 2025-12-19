package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.service.designer.service.TestBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "测试库管理")
@RequestMapping("/testBase")
@RestController
public class TestBaseController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestBaseService testBaseService;

    @ApiOperation("用构型和子系统查询所有测试库")
    @GetMapping("/getTestBaseListWithStructAndGroup")
    public Response getListByStructAndGroup(@RequestParam("structId") int structId, @RequestParam("groupId") int groupId) {
        LOG.info("Start getting testBase by structId and groupId :structId={}, groupId={}", structId, groupId);
        return testBaseService.getListByStructAndGroup(structId, groupId);
    }

    @ApiOperation("获取指定测试库信息")
    @GetMapping("/getTestBaseInfo")
    public Response getTestBaseInfo(@RequestParam("targetGroupId") int targetGroupId, @RequestParam("baseType") int baseType, @RequestParam("entityStructId") int entityStructId) {
        LOG.info("Start getting TestBaseInfo by targetGroupId={}, by baseType={}, by entityStructId={}", targetGroupId, baseType, entityStructId);
        return testBaseService.getTestBaseInfo(targetGroupId, baseType, entityStructId);
    }

    @ApiOperation("获取指定测试库信息")
    @GetMapping("/{baseId}")
    public Response getTestBaseById(@PathVariable("baseId") Integer baseId) {
        LOG.info("Start getting testBase by baseId :baseId={}", baseId);
        return testBaseService.getTestBaseById(baseId);
    }

}
