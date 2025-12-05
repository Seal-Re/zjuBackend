package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestBase;

public interface TestBaseService {

    Response getListByStructAndGroup(Integer structId, Integer groupId);

    Response getTestBaseInfo(Integer targetGroupId, Integer baseType, Integer entityStructId);

    Response getTestBaseById(Integer baseId);


}
