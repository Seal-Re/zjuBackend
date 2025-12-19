package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.FunctionSuite;
import com.hengtiansoft.fastop.model.designer.entity.FunctionSuiteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FunctionSuiteMapper {
    long countByExample(FunctionSuiteExample example);

    int deleteByExample(FunctionSuiteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FunctionSuite record);

    int insertSelective(FunctionSuite record);

    List<FunctionSuite> selectByExample(FunctionSuiteExample example);

    FunctionSuite selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FunctionSuite record, @Param("example") FunctionSuiteExample example);

    int updateByExample(@Param("record") FunctionSuite record, @Param("example") FunctionSuiteExample example);

    int updateByPrimaryKeySelective(FunctionSuite record);

    int updateByPrimaryKey(FunctionSuite record);
}