package com.hengtiansoft.fastop.model.planner.dto;

import com.hengtiansoft.fastop.model.planner.utils.ExeLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 执行步骤日志 Mapper，用于军检审计日志落库与查询。
 */
public interface ExeLogMapper {

    int insert(ExeLog record);

    int insertSelective(ExeLog record);

    List<ExeLog> selectByCondition(@Param("stepId") String stepId,
                                   @Param("planId") String planId,
                                   @Param("startTime") Date startTime,
                                   @Param("endTime") Date endTime,
                                   @Param("offset") int offset,
                                   @Param("limit") int limit);

    long countByCondition(@Param("stepId") String stepId,
                          @Param("planId") String planId,
                          @Param("startTime") Date startTime,
                          @Param("endTime") Date endTime);
}
