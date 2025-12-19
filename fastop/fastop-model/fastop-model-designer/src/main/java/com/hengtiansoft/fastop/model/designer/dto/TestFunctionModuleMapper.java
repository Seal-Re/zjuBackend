package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModule;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestFunctionModuleMapper {
    long countByExample(TestFunctionModuleExample example);

    int deleteByExample(TestFunctionModuleExample example);

    int deleteByPrimaryKey(Integer moduleId);

    int insert(TestFunctionModule record);

    int insertSelective(TestFunctionModule record);

    List<TestFunctionModule> selectByExample(TestFunctionModuleExample example);

    TestFunctionModule selectByPrimaryKey(Integer moduleId);

    int updateByExampleSelective(@Param("record") TestFunctionModule record, @Param("example") TestFunctionModuleExample example);

    int updateByExample(@Param("record") TestFunctionModule record, @Param("example") TestFunctionModuleExample example);

    int updateByPrimaryKeySelective(TestFunctionModule record);

    int updateByPrimaryKey(TestFunctionModule record);
}