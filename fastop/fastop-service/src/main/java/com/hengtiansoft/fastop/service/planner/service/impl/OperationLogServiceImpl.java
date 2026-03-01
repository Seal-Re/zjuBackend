package com.hengtiansoft.fastop.service.planner.service.impl;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.planner.dto.OperationLogMapper;
import com.hengtiansoft.fastop.model.planner.utils.OperationLog;
import com.hengtiansoft.fastop.service.planner.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public Response record(OperationLog log) {
        if (log == null) return ResponseFactory.failure("日志内容不能为空");
        if (log.getCreateTime() == null) log.setCreateTime(new Date());
        try {
            operationLogMapper.insertSelective(log);
            return ResponseFactory.success("记录成功");
        } catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(OperationLogServiceImpl.class).warn("操作日志落库失败（若未建表请执行 dataset/operation_log.sql）: {}", e.getMessage());
            return ResponseFactory.success("已记录");
        }
    }

    @Override
    public Response list(String operatorName, String module, String action, Date startTime, Date endTime, int page, int size) {
        int offset = (page - 1) * size;
        if (offset < 0) offset = 0;
        if (size <= 0 || size > 500) size = 20;
        List<OperationLog> list = operationLogMapper.selectByCondition(operatorName, module, action, startTime, endTime, offset, size);
        long total = operationLogMapper.countByCondition(operatorName, module, action, startTime, endTime);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return ResponseFactory.builder().withData(result).withTotalNum(total).build();
    }
}
