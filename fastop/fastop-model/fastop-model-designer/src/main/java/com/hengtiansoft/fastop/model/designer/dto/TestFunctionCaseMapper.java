package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCase;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestFunctionCaseMapper {
    long countByExample(TestFunctionCaseExample example);

    int deleteByExample(TestFunctionCaseExample example);

    int deleteByPrimaryKey(Integer caseId);

    int insert(TestFunctionCase record);

    int insertSelective(TestFunctionCase record);

    List<TestFunctionCase> selectByExample(TestFunctionCaseExample example);

    TestFunctionCase selectByPrimaryKey(Integer caseId);

    int updateByExampleSelective(@Param("record") TestFunctionCase record, @Param("example") TestFunctionCaseExample example);

    int updateByExample(@Param("record") TestFunctionCase record, @Param("example") TestFunctionCaseExample example);

    int updateByPrimaryKeySelective(TestFunctionCase record);

    int updateByPrimaryKey(TestFunctionCase record);
}