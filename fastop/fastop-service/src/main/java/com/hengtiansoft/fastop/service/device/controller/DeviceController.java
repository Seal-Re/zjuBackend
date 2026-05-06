package com.hengtiansoft.fastop.service.device.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.entity.Device;
import com.hengtiansoft.fastop.service.device.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "设备主数据 CRUD")
@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @ApiOperation("分页查询设备列表")
    @GetMapping("/list")
    public Response listDevices(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "size", required = false) Integer size,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "status", required = false) Integer status) {
        return deviceService.listDevices(page, size, name, type, status);
    }

    @ApiOperation("查询单个设备")
    @GetMapping("/{id}")
    public Response getDevice(@PathVariable("id") String id) {
        return deviceService.getDevice(id);
    }

    @ApiOperation("创建设备")
    @PostMapping
    public Response createDevice(@RequestBody Device device) {
        log.info("Create device: code={} name={}", device == null ? null : device.getCode(),
                device == null ? null : device.getName());
        return deviceService.createDevice(device);
    }

    @ApiOperation("更新设备")
    @PutMapping("/{id}")
    public Response updateDevice(@PathVariable("id") String id, @RequestBody Device device) {
        return deviceService.updateDevice(id, device);
    }

    @ApiOperation("逻辑删除设备")
    @DeleteMapping("/{id}")
    public Response deleteDevice(@PathVariable("id") String id) {
        return deviceService.deleteDevice(id);
    }
}
