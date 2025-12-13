package com.hengtiansoft.fastop.service.planner.service.impl;



import cn.hutool.core.collection.CollectionUtil;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.FunctionSuiteDto;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionRelyMapper;
import com.hengtiansoft.fastop.model.designer.entity.FunctionSuite;
import com.hengtiansoft.fastop.model.designer.entity.TestFunction;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionRely;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionRelyExample;
import com.hengtiansoft.fastop.model.planner.dto.ExeFunctionMapper;
import com.hengtiansoft.fastop.model.planner.entity.ExeFunction;
import com.hengtiansoft.fastop.model.planner.entity.ExeFunctionExample;
import com.hengtiansoft.fastop.service.designer.service.FunctionSuiteService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionService;
import com.hengtiansoft.fastop.service.planner.service.ExeFunctionService;
import com.hengtiansoft.fastop.service.planner.service.ExeStepService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExeFunctionServiceImpl implements ExeFunctionService {

    @Autowired
    private ExeStepService exeStepService;

    @Autowired
    private FunctionSuiteService functionSuiteService;

    @Autowired
    private TestFunctionService testFunctionService;

    @Autowired
    private ExeFunctionMapper exeFunctionMapper;

    @Autowired
    private TestFunctionRelyMapper testFunctionRelyMapper;

    @Override
    public Response getExeFunctionInExeListByPlanId(String planId) {
        if (planId == null) {
            return ResponseFactory.failure("查询失败，缺少targetGroupId");
        }

        ExeFunctionExample example = new ExeFunctionExample();
        ExeFunctionExample.Criteria criteria = example.createCriteria();

        criteria.andPlanIdEqualTo(planId);

        List<ExeFunction> exeFunctions = exeFunctionMapper.selectByExample(example);

        if (exeFunctions == null || exeFunctions.isEmpty()) {
            return ResponseFactory.failure("查询不到对应planId的ExeFunction");
        }

        return ResponseFactory.success(exeFunctions.get(0));
    }

    @Override
    public Response getExeFunctionByFunctionId(String functionId) {
        if (functionId == null) {
            return ResponseFactory.failure("查询失败，缺少targetGroupId");
        }

        ExeFunctionExample example = new ExeFunctionExample();
        ExeFunctionExample.Criteria criteria = example.createCriteria();

        criteria.andFunctionIdEqualTo(functionId);

        List<ExeFunction> exeFunctions = exeFunctionMapper.selectByExample(example);

        if (exeFunctions == null || exeFunctions.isEmpty()) {
            return ResponseFactory.failure("查询不到对应functionId的ExeFunction");
        }

        return ResponseFactory.success(exeFunctions.get(0));

    }

    @Override
    @Transactional(readOnly = false)
    public boolean conveyTestFunction2ExeFunction(Integer suiteId, String planId) {

        List<FunctionSuiteDto> functionSuites = functionSuiteService.listDtoBySuiteWithExample(suiteId);

        if (CollectionUtil.isEmpty(functionSuites)) {
            return true;
        }

        List<Integer> funIds = functionSuites.stream()
                .map(FunctionSuiteDto::getFunId)
                .collect(Collectors.toList());

        Map<Integer, TestFunction> testFunctionMap = testFunctionService.getFunctionsByIds(funIds);

        if (CollectionUtil.isEmpty(testFunctionMap)) {
            // 如果功能 ID 列表非空但实际查不到任何功能实体，可能存在数据问题
            throw new RuntimeException("未找到对应的测试功能实体数据");
        }

        for (FunctionSuiteDto functionSuiteDto : functionSuites) {

            TestFunction testFunction = testFunctionMap.get(functionSuiteDto.getFunId());

            if (testFunction != null) {

                FunctionSuite functionSuite = new FunctionSuite();

                BeanUtils.copyProperties(functionSuiteDto, functionSuite);
                functionSuite.setTestFunId(functionSuiteDto.getFunId());

                TestFunction function = testFunctionMap.get(functionSuite.getTestFunId());
                String exeFunId = saveExeFunction(functionSuite, planId, function);

                exeStepService.conveyTestStep2ExeStep(function.getFunId(), exeFunId);
            }
        }

        return true;
    }

    @Transactional(readOnly = false)
    public String saveExeFunction(FunctionSuite functionSuite, String planId, TestFunction function) {
        if (function == null || planId == null || planId.trim().isEmpty()) {
            return null;
        }

        ExeFunction eFunction = new ExeFunction();
        String exeFunId = UUID.randomUUID().toString();
        eFunction.setExeFunctionId(exeFunId);
        eFunction.setPlanId(planId);

        eFunction.setFunctionId(function.getFunId().toString());
        eFunction.setFunctionName(function.getFunName());
        eFunction.setVersion(function.getVersion());
        eFunction.setFlowVersion(function.getFlowVersion());
        eFunction.setNum(function.getNum());
        eFunction.setSecurity(function.getSecurityLevel());
        eFunction.setExpectTime(function.getExpectTime());
        eFunction.setTestCautionId(function.getTestCautionId());
        eFunction.setSubjectSourceId(function.getSubjectSourceId());

        eFunction.setExeFunctionOrder(functionSuite.getFunOrder());
        eFunction.setVersionDescription(function.getVersionDescription());
        eFunction.setMilitary(function.getMilitary());
        eFunction.setKeyProCount(function.getKeyProCount());

        List<Integer> lists = getRelyByExample(function.getFunId(), functionSuite.getSuiteId());
        if (lists != null && !lists.isEmpty()) {
            eFunction.setDependsOn(StringUtils.join(lists, ','));
        } else {
            eFunction.setDependsOn("");
        }
        eFunction.setCaution(function.getCaution());
        eFunction.setDetectId(function.getDetectId());

        // 设置是否准备就绪
        if (lists.isEmpty()) {
            eFunction.setIsReady(true);
        }

        exeFunctionMapper.insertSelective(eFunction);
        return exeFunId;
    }

    public List<Integer> getRelyByExample(Integer testFunctionId, Integer suiteId) {

        TestFunctionRelyExample example = new TestFunctionRelyExample();

        example.createCriteria()
                .andTestFunctionIdEqualTo(testFunctionId)
                .andSuiteIdEqualTo(suiteId);

        List<TestFunctionRely> relyList = testFunctionRelyMapper.selectByExample(example);

        if (relyList.isEmpty()) {
            return Collections.emptyList();
        }

        return relyList.stream()
                .map(TestFunctionRely::getRelyFunctionId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteExeFunction(String planId) {
        // 记录update成功的数量
        int result = 0;
        // 查出planId对应的所有数据
        ExeFunctionExample exeFunctionExample = new ExeFunctionExample();
        ExeFunctionExample.Criteria criteria = exeFunctionExample.createCriteria();
        criteria.andPlanIdEqualTo(planId);
        List<ExeFunction> list = exeFunctionMapper.selectByExample(exeFunctionExample);

        for (ExeFunction exeFunction : list) {
            exeFunction.setDeleted(true);
            // 更新exeFunction表中对应的值
            exeFunctionMapper.updateByPrimaryKeySelective(exeFunction);

            // 逻辑删除exeStep表中的数据
            exeStepService.deleteExeStep(exeFunction.getExeFunctionId());

            result++;
        }

        return result;
    }

}
