package com.hengtiansoft.fastop.model.planner.dto;

import com.hengtiansoft.fastop.model.planner.entity.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper {

    int insert(Device record);

    int updateByPrimaryKeySelective(Device record);

    Device selectByPrimaryKey(String id);

    Device selectByCode(@Param("code") String code);

    int softDeleteByPrimaryKey(@Param("id") String id, @Param("updatedBy") String updatedBy);

    long countByCondition(@Param("name") String name,
                          @Param("type") String type,
                          @Param("status") Integer status);

    /** 按条件分页；offset/limit 由 service 层根据 page/size 计算 */
    List<Device> listByCondition(@Param("name") String name,
                                 @Param("type") String type,
                                 @Param("status") Integer status,
                                 @Param("offset") int offset,
                                 @Param("limit") int limit);
}
