package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.BaseStruct;
import com.hengtiansoft.fastop.model.designer.entity.BaseStructExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseStructMapper {
    long countByExample(BaseStructExample example);

    int deleteByExample(BaseStructExample example);

    int deleteByPrimaryKey(Integer baseId);

    int insert(BaseStruct record);

    int insertSelective(BaseStruct record);

    List<BaseStruct> selectByExample(BaseStructExample example);

    BaseStruct selectByPrimaryKey(Integer baseId);

    int updateByExampleSelective(@Param("record") BaseStruct record, @Param("example") BaseStructExample example);

    int updateByExample(@Param("record") BaseStruct record, @Param("example") BaseStructExample example);

    int updateByPrimaryKeySelective(BaseStruct record);

    int updateByPrimaryKey(BaseStruct record);
}