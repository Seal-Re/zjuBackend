package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStep;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStepExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestFunctionStepMapper {
    long countByExample(TestFunctionStepExample example);

    int deleteByExample(TestFunctionStepExample example);

    int deleteByPrimaryKey(Integer stepId);

    int insert(TestFunctionStep record);

    int insertSelective(TestFunctionStep record);

    List<TestFunctionStep> selectByExample(TestFunctionStepExample example);

    TestFunctionStep selectByPrimaryKey(Integer stepId);

    int updateByExampleSelective(@Param("record") TestFunctionStep record, @Param("example") TestFunctionStepExample example);

    int updateByExample(@Param("record") TestFunctionStep record, @Param("example") TestFunctionStepExample example);

    int updateByPrimaryKeySelective(TestFunctionStep record);

    int updateByPrimaryKey(TestFunctionStep record);
}