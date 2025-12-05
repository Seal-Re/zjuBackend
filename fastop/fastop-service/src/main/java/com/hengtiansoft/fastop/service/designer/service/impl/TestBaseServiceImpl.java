package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.TestBaseMapper;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestBase;
import com.hengtiansoft.fastop.model.designer.entity.TestBaseExample;
import com.hengtiansoft.fastop.service.designer.service.TestBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestBaseServiceImpl implements TestBaseService {

    @Autowired TestBaseMapper testBaseMapper;

    @Override
    public Response getListByStructAndGroup(Integer structId, Integer groupId){
        if (structId == null) {
            return ResponseFactory.failure("查询失败，缺少structId");
        }
        if (groupId == null) {
            return ResponseFactory.failure("查询失败，缺少groupId");
        }

        TestBaseExample example = new TestBaseExample();
        TestBaseExample.Criteria criteria = example.createCriteria();

        criteria.andEntityStructIdEqualTo(structId);
        criteria.andFunGroupIdEqualTo(groupId);

        List<TestBase> testBases = testBaseMapper.selectByExample(example);

        return ResponseFactory.success(testBases);
    }

    @Override
    public Response getTestBaseInfo(Integer targetGroupId, Integer baseType, Integer entityStructId) {
        if (targetGroupId == null) {
            return ResponseFactory.failure("查询失败，缺少targetGroupId");
        }
        if (baseType == null) {
            return ResponseFactory.failure("查询失败，缺少baseType");
        }
        if (entityStructId == null) {
            return ResponseFactory.failure("查询失败，缺少entityStructId");
        }

        TestBaseExample example = new TestBaseExample();
        TestBaseExample.Criteria criteria = example.createCriteria();

        criteria.andFunGroupIdEqualTo(targetGroupId);
        criteria.andBaseTypeEqualTo(baseType);
        criteria.andEntityStructIdEqualTo(entityStructId);

        List<TestBase> testBases = testBaseMapper.selectByExample(example);

        return ResponseFactory.success(testBases);
    }

    @Override
    public Response getTestBaseById(Integer baseId) {
        if (baseId == null) {
            return ResponseFactory.failure("查询失败，缺少baseId");
        }

        TestBase testBase = testBaseMapper.selectByPrimaryKey(baseId);

        if (testBase == null) {
            return ResponseFactory.failure("查询失败，没有找到对应的TestBase");
        }

        return ResponseFactory.success(testBase);
    }


}
