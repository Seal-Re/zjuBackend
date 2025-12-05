package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.TestBase;
import com.hengtiansoft.fastop.model.designer.entity.TestBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestBaseMapper {
    long countByExample(TestBaseExample example);

    int deleteByExample(TestBaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestBase record);

    int insertSelective(TestBase record);

    List<TestBase> selectByExample(TestBaseExample example);

    TestBase selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestBase record, @Param("example") TestBaseExample example);

    int updateByExample(@Param("record") TestBase record, @Param("example") TestBaseExample example);

    int updateByPrimaryKeySelective(TestBase record);

    int updateByPrimaryKey(TestBase record);
}