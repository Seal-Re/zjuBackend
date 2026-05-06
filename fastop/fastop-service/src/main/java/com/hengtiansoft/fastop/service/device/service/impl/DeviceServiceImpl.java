package com.hengtiansoft.fastop.service.device.service.impl;

import com.hengtiansoft.fastop.base.common.context.UserContextHolder;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.planner.dto.DeviceMapper;
import com.hengtiansoft.fastop.model.planner.entity.Device;
import com.hengtiansoft.fastop.service.device.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public Response listDevices(Integer page, Integer size, String name, String type, Integer status) {
        int p = (page == null || page < 1) ? 1 : page;
        // 上限 200 防止前端误传巨值导致全表扫
        int s = (size == null || size < 1) ? 20 : Math.min(size, 200);
        int offset = (p - 1) * s;

        long total = deviceMapper.countByCondition(name, type, status);
        List<Device> list = total == 0
                ? java.util.Collections.emptyList()
                : deviceMapper.listByCondition(name, type, status, offset, s);

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", total);
        return ResponseFactory.success(data);
    }

    @Override
    public Response getDevice(String id) {
        if (StringUtils.isBlank(id)) {
            return ResponseFactory.failure("设备 ID 不能为空");
        }
        Device d = deviceMapper.selectByPrimaryKey(id);
        if (d == null) {
            return ResponseFactory.failure("未找到设备");
        }
        return ResponseFactory.success(d);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response createDevice(Device device) {
        if (device == null) {
            return ResponseFactory.failure("请求体不能为空");
        }
        if (StringUtils.isBlank(device.getCode())) {
            return ResponseFactory.failure("设备编码 code 不能为空");
        }
        if (StringUtils.isBlank(device.getName())) {
            return ResponseFactory.failure("设备名称 name 不能为空");
        }
        // code 唯一校验：业务编码不允许重复
        Device existing = deviceMapper.selectByCode(device.getCode());
        if (existing != null) {
            return ResponseFactory.failure("设备编码已存在: " + device.getCode());
        }

        device.setId(UUID.randomUUID().toString());
        device.setDeleted(false);
        Date now = new Date();
        device.setCreatedAt(now);
        device.setUpdatedAt(now);
        String operator = UserContextHolder.getCurrentUser();
        device.setCreatedBy(operator);
        device.setUpdatedBy(operator);
        if (device.getStatus() == null) {
            device.setStatus(0);
        }

        int rows = deviceMapper.insert(device);
        if (rows <= 0) {
            return ResponseFactory.failure("创建设备失败");
        }
        return ResponseFactory.success(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateDevice(String id, Device device) {
        if (StringUtils.isBlank(id)) {
            return ResponseFactory.failure("设备 ID 不能为空");
        }
        if (device == null) {
            return ResponseFactory.failure("请求体不能为空");
        }
        Device current = deviceMapper.selectByPrimaryKey(id);
        if (current == null) {
            return ResponseFactory.failure("未找到设备");
        }
        // code 改动时检查不冲突
        if (device.getCode() != null && !device.getCode().equals(current.getCode())) {
            Device byCode = deviceMapper.selectByCode(device.getCode());
            if (byCode != null && !byCode.getId().equals(id)) {
                return ResponseFactory.failure("设备编码已被其他设备占用: " + device.getCode());
            }
        }
        device.setId(id);
        device.setUpdatedAt(new Date());
        device.setUpdatedBy(UserContextHolder.getCurrentUser());
        int rows = deviceMapper.updateByPrimaryKeySelective(device);
        if (rows <= 0) {
            return ResponseFactory.failure("更新设备失败");
        }
        return ResponseFactory.success(deviceMapper.selectByPrimaryKey(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteDevice(String id) {
        if (StringUtils.isBlank(id)) {
            return ResponseFactory.failure("设备 ID 不能为空");
        }
        int rows = deviceMapper.softDeleteByPrimaryKey(id, UserContextHolder.getCurrentUser());
        if (rows <= 0) {
            return ResponseFactory.failure("设备不存在或已被删除");
        }
        return ResponseFactory.success("删除设备成功");
    }
}
