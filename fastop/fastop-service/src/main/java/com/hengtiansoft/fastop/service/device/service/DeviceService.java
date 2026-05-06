package com.hengtiansoft.fastop.service.device.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.entity.Device;

public interface DeviceService {

    /** 分页 + 条件检索（name 模糊、type/status 精确）。返回 { list, total }。*/
    Response listDevices(Integer page, Integer size, String name, String type, Integer status);

    Response getDevice(String id);

    Response createDevice(Device device);

    Response updateDevice(String id, Device device);

    /** 逻辑删除：deleted=1，保留行避免外键关联报错。*/
    Response deleteDevice(String id);
}
