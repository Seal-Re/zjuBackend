package com.hengtiansoft.fastop.service.integration;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "外部集成：设备管理")
@RestController
@RequestMapping("/integration/device")
public class DeviceIntegrationController {

    @Autowired
    private DeviceIntegrationService deviceIntegrationService;

    @ApiOperation("从设备管理服务拉取 topic 列表及示例报文")
    @GetMapping("/topics")
    public Response getTopics() {
        try {
            return ResponseFactory.success(deviceIntegrationService.fetchTopics());
        } catch (Exception e) {
            log.warn("fetch device topics failed", e);
            return ResponseFactory.failure("拉取设备 topic 失败: " + e.getMessage());
        }
    }
}
