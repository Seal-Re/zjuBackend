package com.hengtiansoft.fastop.service.designer.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.service.designer.service.TestBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "测试库管理")
@RequestMapping("/testBase")
@RestController
public class TestBaseController {

    @Autowired
    private TestBaseService testBaseService;

    @ApiOperation("用构型和子系统查询所有测试库")
    @GetMapping("/getTestBaseWithLimit")
    public Response getTestBaseWithLimit(@RequestParam("model") String model, @RequestParam("profession") String profession, @RequestParam("subsystem") String subsystem) {
        log.info("getTestBaseWithLimit model={} profession={} subsystem={}", model, profession, subsystem);
        return testBaseService.getTestBaseWithLimit(model, profession, subsystem);
    }

    @ApiOperation("获取指定测试库信息")
    @GetMapping("/getTestBaseInfo")
    public Response getTestBaseInfo(@RequestParam("targetGroupId") int targetGroupId, @RequestParam("baseType") int baseType, @RequestParam("entityStructId") int entityStructId) {
        log.info("getTestBaseInfo targetGroupId={} baseType={} entityStructId={}", targetGroupId, baseType, entityStructId);
        return testBaseService.getTestBaseInfo(targetGroupId, baseType, entityStructId);
    }

    @ApiOperation("根据 baseId 获取测试库")
    @GetMapping("/{baseId}")
    public Response getTestBaseById(@PathVariable("baseId") Integer baseId) {
        log.info("getTestBaseById baseId={}", baseId);
        return testBaseService.getTestBaseById(baseId);
    }
}
