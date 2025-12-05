package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.TestFunction;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestFunctionMapper {
    long countByExample(TestFunctionExample example);

    int deleteByExample(TestFunctionExample example);

    int deleteByPrimaryKey(Integer funId);

    int insert(TestFunction record);

    int insertSelective(TestFunction record);

    List<TestFunction> selectByExample(TestFunctionExample example);

    TestFunction selectByPrimaryKey(Integer funId);

    int updateByExampleSelective(@Param("record") TestFunction record, @Param("example") TestFunctionExample example);

    int updateByExample(@Param("record") TestFunction record, @Param("example") TestFunctionExample example);

    int updateByPrimaryKeySelective(TestFunction record);

    int updateByPrimaryKey(TestFunction record);
}