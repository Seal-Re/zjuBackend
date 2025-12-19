package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionModuleMapper;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TestFunctionModuleServiceImpl implements TestFunctionModuleService {

    @Autowired
    private TestFunctionModuleMapper testFunctionModuleMapper;

    @Override
    public Response add(TestFunctionModule testFunctionModule) {

        testFunctionModule.setModuleStatus(StatusContants.step_status_unuse);
        testFunctionModule.setModuleDate(String.valueOf(new Date()));
        testFunctionModule.setUpdate(StatusContants.step_update_change);
        //TODO
        testFunctionModule.setChangeUser(null);
        int count = testFunctionModuleMapper.insertSelective(testFunctionModule);

        if (count > 0) {
            return ResponseFactory.success(testFunctionModule);
        }
        return ResponseFactory.failure("增加失败");
    }

    @Override
    public Response update(TestFunctionModule testFunctionModule) {
        TestFunctionModule tFModule = testFunctionModuleMapper.selectByPrimaryKey(testFunctionModule.getModuleId());
        if (tFModule == null) {
            return ResponseFactory.failure("用例不存在");
        }
        tFModule.setUpdate(StatusContants.step_update_change);
        int result = testFunctionModuleMapper.updateByPrimaryKeySelective(testFunctionModule);

        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("更新成功");
        }
        return ResponseFactory.failure("更新失败");
    }

    @Override
    public Response delete(Integer ModuleId) {
        TestFunctionModule tFModule = testFunctionModuleMapper.selectByPrimaryKey(ModuleId);
        if (tFModule == null) {
            return ResponseFactory.failure("用例不存在");
        }
        tFModule.setUpdate(StatusContants.step_update_change);
        tFModule.setModuleStatus(StatusContants.step_status_del);
        int result = testFunctionModuleMapper.updateByPrimaryKeySelective(tFModule);
        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("用例删除成功");
        }
        return ResponseFactory.failure("用例删除失败");
    }

    @Override
    public Response getByModuleId(Integer ModuleId) {
        if (ModuleId == null) {
            return ResponseFactory.failure("未提供ModuleId");
        }
        TestFunctionModule testFunctionModule = testFunctionModuleMapper.selectByPrimaryKey(ModuleId);
        if (testFunctionModule == null) {
            return ResponseFactory.failure("查询失败");
        }
        return ResponseFactory.success(testFunctionModule);
    }

    @Override
    public Response getByFunId(Integer funId) {
        if (funId == null) {
            return ResponseFactory.failure("未提供funId");
        }
        TestFunctionModuleExample testFunctionModuleExample = new TestFunctionModuleExample();
        TestFunctionModuleExample.Criteria criteria = testFunctionModuleExample.createCriteria();

        criteria.andFunIdEqualTo(funId);

        List<TestFunctionModule> list = testFunctionModuleMapper.selectByExample(testFunctionModuleExample);

        if (list != null && list.size() > 0) {
            return ResponseFactory.success(list);
        }

        return ResponseFactory.failure("查询失败");
    }

    @Override
    public Response listAll() {
         TestFunctionModuleExample testFunctionModuleExample = new TestFunctionModuleExample();

         List<TestFunctionModule> list = testFunctionModuleMapper.selectByExample(testFunctionModuleExample);

         return ResponseFactory.success(list);
    }

    @Override
    public List<TestFunctionModule> getUpdateAll() {
        TestFunctionModuleExample testFunctionModuleExample = new TestFunctionModuleExample();
        List<TestFunctionModule> list = testFunctionModuleMapper.selectByExample(testFunctionModuleExample);
        return list;
    }

    @Override
    public Response deletePhy(Integer moduleId) {
        if (moduleId == null) {
            return ResponseFactory.failure("moduleId is null");
        }
        TestFunctionModule testFunctionmodule = testFunctionModuleMapper.selectByPrimaryKey(moduleId);
        if (testFunctionmodule == null) {
            return ResponseFactory.failure("cant find it" );
        }
        int result = testFunctionModuleMapper.deleteByPrimaryKey(moduleId);
        if (result > 0) {
            return ResponseFactory.success("success");
        }
        return ResponseFactory.failure("fail");
    }
}
