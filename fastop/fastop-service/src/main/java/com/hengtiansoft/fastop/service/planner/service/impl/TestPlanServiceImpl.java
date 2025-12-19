package com.hengtiansoft.fastop.service.planner.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.TestPlanStatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.entity.TestBase;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCase;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCaseExample;
import com.hengtiansoft.fastop.model.designer.entity.TestSuite;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanDelBatchDto;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanMapper;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanRequestDto;
import com.hengtiansoft.fastop.model.planner.dto.ExeFunctionMapper;
import com.hengtiansoft.fastop.model.planner.dto.ExeStepMapper;
import com.hengtiansoft.fastop.model.planner.entity.*;
import com.hengtiansoft.fastop.model.planner.utils.TestPlanEnum;
import com.hengtiansoft.fastop.service.designer.service.TestBaseService;
import com.hengtiansoft.fastop.service.designer.service.TestSuiteService;
import com.hengtiansoft.fastop.service.planner.service.ExeFunctionService;
import com.hengtiansoft.fastop.service.planner.service.TestPlanService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestPlanServiceImpl implements TestPlanService {

    private static final Logger LOG = LoggerFactory.getLogger(TestPlanServiceImpl.class);

    @Autowired
    private TestBaseService testBaseService;

    @Autowired
    private TestSuiteService testSuiteService;

    @Autowired
    private ExeFunctionService exeFunctionService;

    @Autowired
    private TestPlanMapper testPlanMapper;

    @Autowired
    private ExeFunctionMapper exeFunctionMapper;

    @Autowired
    private ExeStepMapper exeStepMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Response createTestPlan(TestPlanRequestDto testPlanRequestDto) {

        if (testPlanRequestDto.getEntityId() == null || testPlanRequestDto.getSuiteId() == null) {
            return ResponseFactory.failure("创建计划失败, 请输入测试目标以及测试方案信息");
        }

        try {
            testPlanRequestDto.setStatus(TestPlanEnum.DISPATCH.getKey());

            boolean result = Boolean.TRUE;

            // 处理多模块循环
            if (CollectionUtil.isNotEmpty(testPlanRequestDto.getFunGroupIds())) {
                for (Integer funcGroupId : testPlanRequestDto.getFunGroupIds()) {

                    TestPlanRequestDto currentDto = new TestPlanRequestDto();
                    BeanUtils.copyProperties(testPlanRequestDto, currentDto);

                    currentDto.setFunGroupId(funcGroupId);

                    result = result && this.executeSinglePlanCreation(currentDto);
                }
            } else {
                // 单模块创建
                result = this.executeSinglePlanCreation(testPlanRequestDto);
            }

            if (result) {
                return ResponseFactory.success(testPlanRequestDto);
            } else {
                throw new Exception("创建计划逻辑内部失败");
            }

        } catch (RuntimeException e) {
            LOG.warn("业务创建失败: {}", e.getMessage());
            // 事务回滚
            return ResponseFactory.failure(e.getMessage());
        } catch (Exception e) {
            // 捕获系统级别异常
            LOG.warn("系统创建失败: {}", e.getMessage());
            // 事务回滚
            return ResponseFactory.failure("系统内部错误: " + e.getMessage());
        }
    }

    private boolean executeSinglePlanCreation(TestPlanRequestDto testPlanRequestDto) throws Exception {

        if (CollectionUtil.isNotEmpty(getListByPlanName(testPlanRequestDto.getPlanName()))) {
            throw new RuntimeException("测试计划名称已存在");
        }

        TestSuite testSuite = testSuiteService.getTestSuiteInfoById(testPlanRequestDto.getSuiteId());

        if (testSuite == null) {
            throw new RuntimeException("获取测试集信息失败，测试集不存在");
        }

        TestBase testBase = (TestBase) testBaseService.getTestBaseById(testSuite.getTestBaseId()).getData();

        if (testBase == null) {
            throw new RuntimeException("获取测试库信息失败，测试库不存在");
        }

        String planId = UUID.randomUUID().toString();
        TestPlan plan = new TestPlan();
        BeanUtils.copyProperties(testPlanRequestDto, plan);

        if (testBase.getBaseType().equals(2)) {
            plan.setBaseType(2); // 临时清单
        }

        plan.setPlanId(planId);

        TestPlan existingPlan = getPlanRound(testPlanRequestDto.getEntityId(), testPlanRequestDto.getSuiteId());

        if (existingPlan != null) {
            plan.setPlanRound(existingPlan.getPlanRound() + CommonConstants.NUM_1);
        } else {
            plan.setPlanRound(CommonConstants.NUM_1);
        }

        boolean result = testPlanMapper.insertSelective(plan) > 0;

        if (result) {
            exeFunctionService.conveyTestFunction2ExeFunction(testPlanRequestDto.getSuiteId(), planId);
            if (testBase.getBaseType().equals(2)) {
                // 设置逻辑删除标志
                testSuite.setDeleted(true);
                // 调用Service 更新
                boolean updateSuccess = testSuiteService.updateTestSuite(testSuite);

                if (!updateSuccess) {
                    throw new RuntimeException("更新临时测试集状态失败");
                }
            }
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Response updateTestPlan(TestPlanRequestDto testPlanRequestDto) {

        // 基础参数校验
        if (StringUtils.isBlank(testPlanRequestDto.getPlanId())) {
            return ResponseFactory.failure("更新失败：计划ID不能为空");
        }

        try {
            // 组装更新实体对象
            TestPlan testPlan = new TestPlan();
            testPlan.setPlanId(testPlanRequestDto.getPlanId());
            testPlan.setPlanName(testPlanRequestDto.getPlanName());
            testPlan.setPlanNumber(testPlanRequestDto.getPlanNumber());
            testPlan.setPlanStartTime(testPlanRequestDto.getPlanStartTime());
            testPlan.setPlanEndTime(testPlanRequestDto.getPlanEndTime());
            testPlan.setDispatcherId(testPlanRequestDto.getDispatcherId());

            LOG.info("Remark message{}",testPlanRequestDto.getRemark());

            // 执行本地数据库更新
            int col = testPlanMapper.updateByPrimaryKeySelective(testPlan);

            // 检查数据库更新结果
            if (col <= 0) {
                // 没更新到任何数据（ID不存在），直接返回失败
                return ResponseFactory.failure("更新失败：未找到对应计划或无变更");
            }


            return ResponseFactory.success("更新计划成功");

        } catch (Exception e) {
            LOG.error("修改计划发生异常: ", e);
            // 标记回滚，防止脏数据提交
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseFactory.failure("更新计划失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = false) // 添加事务管理
    public Response deleteSingleTestPlan(String planId) {

        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.success("planId为空");
        }

        TestPlan testPlanDel = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlanDel == null) {
            return ResponseFactory.failure("查找不到planId对应的测试计划");
        }

        if (!TestPlanStatusContants.PLAN_STATUS_UNEXE.equals(testPlanDel.getStatus())) {
            return ResponseFactory.failure("当前测试计划状态不能删除");
        }

        Integer result;

        testPlanDel.setDeleted(true);

        result = testPlanMapper.updateByPrimaryKeySelective(testPlanDel);

        if (result <= 0) {
            return ResponseFactory.failure("删除对应测试计划失败/或对应测试计划已被删除");
        }

        exeFunctionService.deleteExeFunction(planId);

        return ResponseFactory.success("删除测试计划成功" + result);
    }

    @Override
    public Response deleteBatchTestPlan(TestPlanDelBatchDto testPlanDelBatchDto) {
        List<String> planIds = testPlanDelBatchDto.getPlanIdLists();
        if (planIds.isEmpty()){
            return ResponseFactory.success("planId为空");
        }
        List<TestPlan> testPlanDelBatch = getTestPlanListByPlanId(planIds);
        List<String> planIdsDel = testPlanDelBatch.stream().map(TestPlan::getPlanId).collect(Collectors.toList());

        StringBuffer result = new StringBuffer();

        for (String planId :planIds ) {
            if (!planIdsDel.contains(planId)) {
                result.append("查找不到planId = "+ planId + "对应的测试计划" + ",");
            }
            else {
                result.append(deleteSingleTestPlan(planId));
            }
        }

        return ResponseFactory.success(result.toString());
    }

    List<TestPlan> getTestPlanListByPlanId(List<String> planIds) {
        TestPlanExample testPlanExample = new TestPlanExample();
        testPlanExample.createCriteria().andPlanIdIn(planIds);

        return testPlanMapper.selectByExample(testPlanExample);
    }

    TestPlan getPlanRound(Integer EntityId, Integer SuiteId) {

        TestPlanExample testPlanExample = new TestPlanExample();
        TestPlanExample.Criteria criteria = testPlanExample.createCriteria();

        criteria.andEntityIdEqualTo(EntityId);
        criteria.andSuiteIdEqualTo(SuiteId);

        List<TestPlan> listTestPlan = testPlanMapper.selectByExample(testPlanExample);
        if (!CollectionUtil.isNotEmpty(listTestPlan)) {
            return null;
        }
        return listTestPlan.get(0);
    }

    public List<TestPlan> getListByPlanName(String planName){

        TestPlanExample testPlanExample = new TestPlanExample();
        TestPlanExample.Criteria criteria = testPlanExample.createCriteria();

        criteria.andPlanNameEqualTo(planName);

        List<TestPlan> listTestPlan = testPlanMapper.selectByExample(testPlanExample);

        return listTestPlan;
    }

    public Response listAll() {
        TestPlanExample testPlanExample = new TestPlanExample();
        TestPlanExample.Criteria criteria = testPlanExample.createCriteria();
        List<TestPlan> listTestPlan = testPlanMapper.selectByExample(testPlanExample);
        return ResponseFactory.success(listTestPlan);
    }

    @Override
    public Response dispatchPlan(String planId) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("planId is null");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("Plan not found");
        }

        // Validate Plan status (must be UNEXE or DISPATCH)
        // Assuming UNEXE=0, DISPATCH=0? The enum says DISPATCH.getKey() for create.
        // Let's assume UNEXE is also OK or maybe just DISPATCH.
        // Task description: "Validate Plan status (must be UNEXE or DISPATCH)."
        if (!testPlan.getStatus().equals(TestPlanEnum.DISPATCH.getKey()) && !testPlan.getStatus().equals(TestPlanStatusContants.PLAN_STATUS_UNEXE)) {
            return ResponseFactory.failure("Status invalid for dispatch");
        }

        // Aggregate data
        Map<String, Object> dispatchData = new HashMap<>();
        dispatchData.put("plan", testPlan);

        ExeFunctionExample exeFunctionExample = new ExeFunctionExample();
        exeFunctionExample.createCriteria().andPlanIdEqualTo(planId);
        List<ExeFunction> exeFunctions = exeFunctionMapper.selectByExample(exeFunctionExample);
        dispatchData.put("functions", exeFunctions);

        List<ExeStep> allSteps = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(exeFunctions)) {
            for (ExeFunction func : exeFunctions) {
                ExeStepExample exeStepExample = new ExeStepExample();
                exeStepExample.createCriteria().andExeFunctionIdEqualTo(func.getExeFunctionId());
                List<ExeStep> steps = exeStepMapper.selectByExample(exeStepExample);
                if (CollectionUtil.isNotEmpty(steps)) {
                    allSteps.addAll(steps);
                }
            }
        }
        dispatchData.put("steps", allSteps);

        return ResponseFactory.success(dispatchData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response startPlan(String planId) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("planId is null");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("Plan not found");
        }

        testPlan.setStatus(TestPlanStatusContants.PLAN_STATUS_EXEING); // 2
        testPlan.setActualStartTime(new Date());
        testPlanMapper.updateByPrimaryKeySelective(testPlan);

        // Cascade Update: All ExeFunction records under this plan must also update their status to EXEING.
        ExeFunctionExample example = new ExeFunctionExample();
        example.createCriteria().andPlanIdEqualTo(planId);
        List<ExeFunction> exeFunctions = exeFunctionMapper.selectByExample(example);

        for (ExeFunction exeFunc : exeFunctions) {
            exeFunc.setExeStatus(TestPlanStatusContants.PLAN_STATUS_EXEING); // Assuming ExeStatus follows same constants
            exeFunctionMapper.updateByPrimaryKeySelective(exeFunc);
        }

        return ResponseFactory.success("Plan started");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response pausePlan(String planId) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("planId is null");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("Plan not found");
        }

        testPlan.setStatus(TestPlanStatusContants.PLAN_STATUS_PAUSE); // 3
        testPlanMapper.updateByPrimaryKeySelective(testPlan);

        // Cascade Update: All ExeFunction records under this plan must also update their status to PAUSE.
        ExeFunctionExample example = new ExeFunctionExample();
        example.createCriteria().andPlanIdEqualTo(planId);
        List<ExeFunction> exeFunctions = exeFunctionMapper.selectByExample(example);

        for (ExeFunction exeFunc : exeFunctions) {
            exeFunc.setExeStatus(TestPlanStatusContants.PLAN_STATUS_PAUSE);
            exeFunctionMapper.updateByPrimaryKeySelective(exeFunc);
        }

        return ResponseFactory.success("Plan paused");
    }

}
