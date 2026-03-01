package com.hengtiansoft.fastop.model.planner.dto;

import com.hengtiansoft.fastop.model.planner.utils.OperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 操作日志 Mapper
 */
public interface OperationLogMapper {

    int insert(OperationLog record);

    int insertSelective(OperationLog record);

    List<OperationLog> selectByCondition(@Param("operatorName") String operatorName,
                                         @Param("module") String module,
                                         @Param("action") String action,
                                         @Param("startTime") Date startTime,
                                         @Param("endTime") Date endTime,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    long countByCondition(@Param("operatorName") String operatorName,
                          @Param("module") String module,
                          @Param("action") String action,
                          @Param("startTime") Date startTime,
                          @Param("endTime") Date endTime);
}
