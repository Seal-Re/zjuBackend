package com.hengtiansoft.fastop.model.planner.dto;

import com.hengtiansoft.fastop.model.planner.entity.ExeStep;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepExample;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExeStepMapper {
    long countByExample(ExeStepExample example);

    int deleteByExample(ExeStepExample example);

    int deleteByPrimaryKey(String exeStepId);

    int insert(ExeStepWithBLOBs record);

    int insertSelective(ExeStepWithBLOBs record);

    List<ExeStepWithBLOBs> selectByExampleWithBLOBs(ExeStepExample example);

    List<ExeStep> selectByExample(ExeStepExample example);

    ExeStepWithBLOBs selectByPrimaryKey(String exeStepId);

    int updateByExampleSelective(@Param("record") ExeStepWithBLOBs record, @Param("example") ExeStepExample example);

    int updateByExampleWithBLOBs(@Param("record") ExeStepWithBLOBs record, @Param("example") ExeStepExample example);

    int updateByExample(@Param("record") ExeStep record, @Param("example") ExeStepExample example);

    int updateByPrimaryKeySelective(ExeStepWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ExeStepWithBLOBs record);

    int updateByPrimaryKey(ExeStep record);
}