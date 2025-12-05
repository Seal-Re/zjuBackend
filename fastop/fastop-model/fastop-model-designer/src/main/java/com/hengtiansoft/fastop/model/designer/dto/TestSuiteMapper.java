package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.TestSuite;
import com.hengtiansoft.fastop.model.designer.entity.TestSuiteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestSuiteMapper {
    long countByExample(TestSuiteExample example);

    int deleteByExample(TestSuiteExample example);

    int deleteByPrimaryKey(Integer suiteId);

    int insert(TestSuite record);

    int insertSelective(TestSuite record);

    List<TestSuite> selectByExample(TestSuiteExample example);

    TestSuite selectByPrimaryKey(Integer suiteId);

    int updateByExampleSelective(@Param("record") TestSuite record, @Param("example") TestSuiteExample example);

    int updateByExample(@Param("record") TestSuite record, @Param("example") TestSuiteExample example);

    int updateByPrimaryKeySelective(TestSuite record);

    int updateByPrimaryKey(TestSuite record);
}