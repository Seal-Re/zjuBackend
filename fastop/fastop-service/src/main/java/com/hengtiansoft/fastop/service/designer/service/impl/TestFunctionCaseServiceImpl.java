package com.hengtiansoft.fastop.service.designer.service.impl;

import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionCaseMapper;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionStepMapper;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionModuleService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionCaseService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TestFunctionCaseServiceImpl implements TestFunctionCaseService {

    @Autowired
    private TestFunctionModuleService testFunctionModuleService;

    @Autowired
    private TestFunctionCaseMapper testFunctionCaseMapper;

    @Override
    public Response add(TestFunctionCase testFunctionCase) {

        testFunctionCase.setCaseStatus(StatusContants.step_status_unuse);
        testFunctionCase.setCaseDate(String.valueOf(new Date()));
        testFunctionCase.setUpdate(StatusContants.step_update_change);
        // TODO
        testFunctionCase.setChangeUser(null);

        int count = testFunctionCaseMapper.insertSelective(testFunctionCase);

        if (count > 0) {
            return ResponseFactory.success(testFunctionCase);
        }
        return ResponseFactory.failure("增加失败");
    }

    @Override
    public Response update(TestFunctionCase testFunctionCase) {
        TestFunctionCase tFCase = testFunctionCaseMapper.selectByPrimaryKey(testFunctionCase.getCaseId());
        if (tFCase == null) {
            return ResponseFactory.failure("用例不存在");
        }
        tFCase.setUpdate(StatusContants.step_update_change);
        int result = testFunctionCaseMapper.updateByPrimaryKeySelective(testFunctionCase);

        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("更新成功");
        }
        return ResponseFactory.failure("更新失败");
    }

    @Override
    public Response delete(Integer caseId) {
        TestFunctionCase tFCase = testFunctionCaseMapper.selectByPrimaryKey(caseId);
        if (tFCase == null) {
            return ResponseFactory.failure("用例不存在");
        }
        tFCase.setUpdate(StatusContants.step_update_change);
        tFCase.setCaseStatus(StatusContants.step_status_del);
        int result = testFunctionCaseMapper.updateByPrimaryKeySelective(tFCase);
        if (result > CommonConstants.NUM_0) {
            return ResponseFactory.success("删除成功");
        }
        return ResponseFactory.failure("删除用例失败");
    }

    @Override
    public Response getByCaseId(Integer caseId) {
        if (caseId == null) {
            return ResponseFactory.failure("未提供caseId");
        }
        TestFunctionCase testFunctionCase = testFunctionCaseMapper.selectByPrimaryKey(caseId);
        if (testFunctionCase == null) {
            return ResponseFactory.failure("查询失败");
        }
        return ResponseFactory.success(testFunctionCase);
    }

    @Override
    public Response getByModuleId(Integer moduleId) {
        if (moduleId == null) {
            return ResponseFactory.failure("未提供moduleId");
        }
        TestFunctionCaseExample testFunctionCaseExample = new TestFunctionCaseExample();
        TestFunctionCaseExample.Criteria criteria = testFunctionCaseExample.createCriteria();

        criteria.andModuleIdEqualTo(moduleId);

        List<TestFunctionCase> list = testFunctionCaseMapper.selectByExample(testFunctionCaseExample);

        if (list != null && list.size() > 0) {
            return ResponseFactory.success(list);
        }

        return ResponseFactory.failure("查询失败");
    }

    @Override
    public Response listAll() {
         TestFunctionCaseExample testFunctionCaseExample = new TestFunctionCaseExample();

         List<TestFunctionCase> list = testFunctionCaseMapper.selectByExample(testFunctionCaseExample);

         return ResponseFactory.success(list);
    }

    @Override
    public List<TestFunctionCase> getUpdateAll() {
        TestFunctionCaseExample testFunctionCaseExample = new TestFunctionCaseExample();
        List<TestFunctionCase> list = testFunctionCaseMapper.selectByExample(testFunctionCaseExample);
        return list;
    }

    @Override
    public Response deletePhy(Integer caseId) {
        if (caseId == null) {
            return ResponseFactory.failure("caseId is null");
        }
        TestFunctionCase testFunctionCase = testFunctionCaseMapper.selectByPrimaryKey(caseId);
        if (testFunctionCase == null) {
            return ResponseFactory.failure("cant find it" );
        }
        int result = testFunctionCaseMapper.deleteByPrimaryKey(caseId);
        if (result > 0) {
            return ResponseFactory.success("success");
        }
        return ResponseFactory.failure("fail");
    }

    @Override
    public Response updateByModuleIds(List<Integer> testFunctionModuleIds) {
        if (testFunctionModuleIds == null || testFunctionModuleIds.isEmpty()) {
            return ResponseFactory.success("列表为空，无需更新");
        }

        TestFunctionCase record = new TestFunctionCase();
        record.setUpdate(StatusContants.step_update_change);

        TestFunctionCaseExample example = new TestFunctionCaseExample();
        example.createCriteria().andCaseIdIn(testFunctionModuleIds);

        int rows = testFunctionCaseMapper.updateByExampleSelective(record, example);

        return ResponseFactory.success("更新成功，共更新了 " + rows + " 条数据");
    }
}
