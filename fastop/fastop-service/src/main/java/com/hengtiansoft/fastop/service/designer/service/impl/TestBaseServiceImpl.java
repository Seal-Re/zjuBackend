package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.TestBaseMapper;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestBase;
import com.hengtiansoft.fastop.model.designer.entity.TestBaseExample;
import com.hengtiansoft.fastop.service.designer.service.TestBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TestBaseServiceImpl implements TestBaseService {

    @Autowired TestBaseMapper testBaseMapper;

    @Override
    public Response getTestBaseWithLimit(String model, String profession, String subsystem){



        TestBaseExample example = new TestBaseExample();
        TestBaseExample.Criteria criteria = example.createCriteria();

        criteria.andModelEqualTo(model);
        criteria.andProfessionEqualTo(profession);
        criteria.andSubsystemEqualTo(subsystem);

        List<TestBase> testBases = testBaseMapper.selectByExample(example);

        if(testBases.size()>0){
            return ResponseFactory.success(testBases.get(0).getId());
        }

        TestBase testBase = new TestBase();

        testBase.setModel(model);
        testBase.setProfession(profession);
        testBase.setSubsystem(subsystem);

        testBase.setCreatedAt(new Date());
        testBase.setUpdatedAt(new Date());

        testBase.setDeleted(false);

        testBaseMapper.insert(testBase);

        List<TestBase> testBases1 = testBaseMapper.selectByExample(example);

        if(testBases1.size()>0){
            return ResponseFactory.success(testBases1.get(0).getId());
        }
        else
            return ResponseFactory.failure("出现系统错误");

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
