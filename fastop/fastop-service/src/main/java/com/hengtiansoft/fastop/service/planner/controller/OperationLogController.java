package com.hengtiansoft.fastop.service.planner.controller;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.utils.OperationLog;
import com.hengtiansoft.fastop.service.planner.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Api(tags = "操作日志")
@RestController
@RequestMapping("/log/operation")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation("记录操作日志")
    @PostMapping("/record")
    public Response record(@RequestBody OperationLog operationLog) {
        return operationLogService.record(operationLog);
    }

    @ApiOperation("分页查询操作日志")
    @GetMapping("/list")
    public Response list(
            @RequestParam(required = false) String operatorName,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Date start = parseDate(startTime);
        Date end = parseDate(endTime);
        return operationLogService.list(operatorName, module, action, start, end, page, size);
    }

    private static Date parseDate(String str) {
        if (str == null || str.isEmpty()) return null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (Exception e) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(str);
            } catch (Exception e2) {
                return null;
            }
        }
    }
}
