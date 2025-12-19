package com.hengtiansoft.fastop.model.planner.dto;

import com.hengtiansoft.fastop.model.planner.entity.ExeFunction;
import com.hengtiansoft.fastop.model.planner.entity.ExeFunctionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExeFunctionMapper {
    long countByExample(ExeFunctionExample example);

    int deleteByExample(ExeFunctionExample example);

    int deleteByPrimaryKey(String exeFunctionId);

    int insert(ExeFunction record);

    int insertSelective(ExeFunction record);

    List<ExeFunction> selectByExample(ExeFunctionExample example);

    ExeFunction selectByPrimaryKey(String exeFunctionId);

    int updateByExampleSelective(@Param("record") ExeFunction record, @Param("example") ExeFunctionExample example);

    int updateByExample(@Param("record") ExeFunction record, @Param("example") ExeFunctionExample example);

    int updateByPrimaryKeySelective(ExeFunction record);

    int updateByPrimaryKey(ExeFunction record);
}