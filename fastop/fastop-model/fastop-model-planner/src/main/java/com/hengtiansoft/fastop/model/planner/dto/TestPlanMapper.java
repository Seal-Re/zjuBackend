package com.hengtiansoft.fastop.model.planner.dto;

import com.hengtiansoft.fastop.model.planner.entity.TestPlan;
import com.hengtiansoft.fastop.model.planner.entity.TestPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestPlanMapper {
    long countByExample(TestPlanExample example);

    int deleteByExample(TestPlanExample example);

    int deleteByPrimaryKey(String planId);

    int insert(TestPlan record);

    int insertSelective(TestPlan record);

    List<TestPlan> selectByExample(TestPlanExample example);

    TestPlan selectByPrimaryKey(String planId);

    int updateByExampleSelective(@Param("record") TestPlan record, @Param("example") TestPlanExample example);

    int updateByExample(@Param("record") TestPlan record, @Param("example") TestPlanExample example);

    int updateByPrimaryKeySelective(TestPlan record);

    int updateByPrimaryKey(TestPlan record);
}