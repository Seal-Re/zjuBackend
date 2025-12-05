package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.TestFunctionRely;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionRelyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestFunctionRelyMapper {
    long countByExample(TestFunctionRelyExample example);

    int deleteByExample(TestFunctionRelyExample example);

    int deleteByPrimaryKey(Integer testFunctionRelyId);

    int insert(TestFunctionRely record);

    int insertSelective(TestFunctionRely record);

    List<TestFunctionRely> selectByExample(TestFunctionRelyExample example);

    TestFunctionRely selectByPrimaryKey(Integer testFunctionRelyId);

    int updateByExampleSelective(@Param("record") TestFunctionRely record, @Param("example") TestFunctionRelyExample example);

    int updateByExample(@Param("record") TestFunctionRely record, @Param("example") TestFunctionRelyExample example);

    int updateByPrimaryKeySelective(TestFunctionRely record);

    int updateByPrimaryKey(TestFunctionRely record);
}