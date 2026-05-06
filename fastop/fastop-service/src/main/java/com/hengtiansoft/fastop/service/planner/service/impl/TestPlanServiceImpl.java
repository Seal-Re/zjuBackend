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
import com.hengtiansoft.fastop.model.planner.utils.OperationLog;
import com.hengtiansoft.fastop.model.planner.utils.TestPlanEnum;
import com.hengtiansoft.fastop.service.designer.service.TestBaseService;
import com.hengtiansoft.fastop.service.designer.service.TestSuiteService;
import com.hengtiansoft.fastop.service.planner.service.ExeFunctionService;
import com.hengtiansoft.fastop.service.planner.service.OperationLogService;
import com.hengtiansoft.fastop.service.planner.service.TestPlanService;
import com.hengtiansoft.fastop.base.common.context.UserContextHolder;
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

    @Autowired
    private OperationLogService operationLogService;

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
                recordOperationLog("测试计划", "创建", "计划", testPlanRequestDto.getPlanName(), "计划名称: " + testPlanRequestDto.getPlanName());
                return ResponseFactory.success(testPlanRequestDto);
            } else {
                throw new Exception("创建计划逻辑内部失败");
            }

        } catch (RuntimeException e) {
            LOG.warn("业务创建失败: {}", e);
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
            testPlan.setSuiteId(testPlanRequestDto.getSuiteId());

            LOG.info("Remark message{}",testPlanRequestDto.getRemark());

            // 执行本地数据库更新
            int col = testPlanMapper.updateByPrimaryKeySelective(testPlan);

            // 检查数据库更新结果
            if (col <= 0) {
                // 没更新到任何数据（ID不存在），直接返回失败
                return ResponseFactory.failure("更新失败：未找到对应计划或无变更");
            }
            recordOperationLog("测试计划", "修改", "计划", testPlanRequestDto.getPlanId(), testPlanRequestDto.getPlanName());
            return ResponseFactory.success("更新计划成功");

        } catch (Exception e) {
            LOG.error("修改计划发生异常: ", e);
            // 标记回滚，防止脏数据提交
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseFactory.failure("更新计划失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response remarkTestPlan(String planId, String remark) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("planId 不能为空");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("未找到对应计划");
        }
        // 仅更新 remark 列；用 selective update 避免覆盖其他业务字段
        TestPlan patch = new TestPlan();
        patch.setPlanId(planId);
        patch.setRemark(remark);
        int rows = testPlanMapper.updateByPrimaryKeySelective(patch);
        if (rows <= 0) {
            return ResponseFactory.failure("备注更新失败");
        }
        recordOperationLog("测试计划", "备注", "计划", planId,
                remark != null ? remark : "");
        return ResponseFactory.success("备注已记录");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteSingleTestPlan(String planId) {

        if (StringUtils.isBlank(planId)) {
            // 失败必须返回 failure 而非 success，否则前端把 success 当作"删除成功"误导用户
            return ResponseFactory.failure("计划 ID 不能为空");
        }

        TestPlan testPlanDel = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlanDel == null) {
            return ResponseFactory.failure("查找不到planId对应的测试计划");
        }

        if (TestPlanStatusContants.PLAN_STATUS_EXEING.equals(testPlanDel.getStatus())) {
            return ResponseFactory.failure("当前测试计划状态不能删除");
        }

        Integer result;

        testPlanDel.setDeleted(true);

        result = testPlanMapper.updateByPrimaryKeySelective(testPlanDel);

        if (result <= 0) {
            return ResponseFactory.failure("删除对应测试计划失败/或对应测试计划已被删除");
        }

        exeFunctionService.deleteExeFunction(planId);
        recordOperationLog("测试计划", "删除", "计划", planId, testPlanDel.getPlanName());
        return ResponseFactory.success("删除测试计划成功" + result);
    }

    @Override
    public Response deleteBatchTestPlan(TestPlanDelBatchDto testPlanDelBatchDto) {
        List<String> planIds = testPlanDelBatchDto.getPlanIdLists();
        if (planIds.isEmpty()){
            // 失败必须返回 failure，避免前端 ElMessage.success 误导
            return ResponseFactory.failure("批量删除：计划 ID 列表为空");
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
        if (CollectionUtil.isEmpty(listTestPlan)) {
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
            return ResponseFactory.failure("计划 ID 不能为空");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("未找到对应的测试计划");
        }

        // 仅允许 DISPATCH(待派工) 或 UNEXE(已下发未执行) 触发派发；其余状态拒绝
        Integer cur = testPlan.getStatus();
        if (!Objects.equals(cur, TestPlanStatusContants.PLAN_STATUS_DISPATCH)
                && !Objects.equals(cur, TestPlanStatusContants.PLAN_STATUS_UNEXE)) {
            return ResponseFactory.failure(illegalTransitionMsg(cur, "派发"));
        }

        Map<String, Object> dispatchData = new HashMap<>();
        dispatchData.put("plan", testPlan);

        ExeFunctionExample exeFunctionExample = new ExeFunctionExample();
        exeFunctionExample.createCriteria().andPlanIdEqualTo(planId);
        List<ExeFunction> exeFunctions = exeFunctionMapper.selectByExample(exeFunctionExample);
        dispatchData.put("functions", exeFunctions);

        // 一次性 IN 查询所有 ExeFunction 下的 step，避免逐个 function 一次 SQL 的 N+1
        List<ExeStep> allSteps;
        if (CollectionUtil.isNotEmpty(exeFunctions)) {
            List<String> exeFunctionIds = exeFunctions.stream()
                    .map(ExeFunction::getExeFunctionId)
                    .collect(Collectors.toList());
            ExeStepExample exeStepExample = new ExeStepExample();
            exeStepExample.createCriteria().andExeFunctionIdIn(exeFunctionIds);
            allSteps = exeStepMapper.selectByExample(exeStepExample);
            if (allSteps == null) allSteps = new ArrayList<>();
        } else {
            allSteps = new ArrayList<>();
        }
        dispatchData.put("steps", allSteps);

        testPlan.setStatus(TestPlanStatusContants.PLAN_STATUS_UNEXE);

        int updateCount = testPlanMapper.updateByPrimaryKeySelective(testPlan);
        if (updateCount <= 0) {
            return ResponseFactory.failure("更新计划状态失败");
        }
        recordOperationLog("测试计划", "派发", "计划", planId, testPlan.getPlanName());
        return ResponseFactory.success(dispatchData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response startPlan(String planId) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("计划 ID 不能为空");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("未找到对应的测试计划");
        }

        // 仅允许 UNEXE(待执行) 或 PAUSE(暂停后恢复) 进入 EXEING；阻止 DISPATCH/VERIFY/MVERIFY/FINISH 误转
        Integer cur = testPlan.getStatus();
        if (!Objects.equals(cur, TestPlanStatusContants.PLAN_STATUS_UNEXE)
                && !Objects.equals(cur, TestPlanStatusContants.PLAN_STATUS_PAUSE)) {
            return ResponseFactory.failure(illegalTransitionMsg(cur, "开始/恢复"));
        }

        testPlan.setStatus(TestPlanStatusContants.PLAN_STATUS_EXEING);
        // 仅首次开始时记录实际开始时间；恢复（PAUSE→EXEING）保留原值
        if (Objects.equals(cur, TestPlanStatusContants.PLAN_STATUS_UNEXE)
                && testPlan.getActualStartTime() == null) {
            testPlan.setActualStartTime(new Date());
        }
        testPlanMapper.updateByPrimaryKeySelective(testPlan);

        // 级联：将该计划下所有 ExeFunction 状态批量改为 EXEING（一条 SQL 替代 N+1）
        ExeFunction template = new ExeFunction();
        template.setExeStatus(TestPlanStatusContants.PLAN_STATUS_EXEING);
        ExeFunctionExample example = new ExeFunctionExample();
        example.createCriteria().andPlanIdEqualTo(planId);
        exeFunctionMapper.updateByExampleSelective(template, example);

        recordOperationLog("测试计划", "开始", "计划", planId, testPlan.getPlanName());
        return ResponseFactory.success("计划已启动");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response pausePlan(String planId) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("计划 ID 不能为空");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("未找到对应的测试计划");
        }

        // 仅 EXEING 可暂停；FINISH/PAUSE/VERIFY 等不允许
        Integer cur = testPlan.getStatus();
        if (!Objects.equals(cur, TestPlanStatusContants.PLAN_STATUS_EXEING)) {
            return ResponseFactory.failure(illegalTransitionMsg(cur, "暂停"));
        }

        testPlan.setStatus(TestPlanStatusContants.PLAN_STATUS_PAUSE);
        testPlanMapper.updateByPrimaryKeySelective(testPlan);

        // 级联：将该计划下所有 ExeFunction 状态批量改为 PAUSE
        ExeFunction template = new ExeFunction();
        template.setExeStatus(TestPlanStatusContants.PLAN_STATUS_PAUSE);
        ExeFunctionExample example = new ExeFunctionExample();
        example.createCriteria().andPlanIdEqualTo(planId);
        exeFunctionMapper.updateByExampleSelective(template, example);

        recordOperationLog("测试计划", "暂停", "计划", planId, testPlan.getPlanName());
        return ResponseFactory.success("计划已暂停");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response verifyPlan(String planId) {
        return moveToStatus(planId, "提交检验",
                TestPlanStatusContants.PLAN_STATUS_VERIFY,
                Collections.singletonList(TestPlanStatusContants.PLAN_STATUS_EXEING));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response mverifyPlan(String planId) {
        return moveToStatus(planId, "转军检",
                TestPlanStatusContants.PLAN_STATUS_MVERIFY,
                Collections.singletonList(TestPlanStatusContants.PLAN_STATUS_VERIFY));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response finishPlan(String planId) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("计划 ID 不能为空");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("未找到对应的测试计划");
        }
        Integer cur = testPlan.getStatus();
        if (!Objects.equals(cur, TestPlanStatusContants.PLAN_STATUS_MVERIFY)) {
            return ResponseFactory.failure(illegalTransitionMsg(cur, "完工"));
        }
        testPlan.setStatus(TestPlanStatusContants.PLAN_STATUS_FINISH);
        if (testPlan.getActualEndTime() == null) {
            testPlan.setActualEndTime(new Date());
        }
        int rows = testPlanMapper.updateByPrimaryKeySelective(testPlan);
        if (rows <= 0) {
            return ResponseFactory.failure("更新计划状态失败");
        }
        recordOperationLog("测试计划", "完工", "计划", planId, testPlan.getPlanName());
        return ResponseFactory.success("计划已完工");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response resetPlan(String planId) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("计划 ID 不能为空");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("未找到对应的测试计划");
        }
        Integer cur = testPlan.getStatus();
        // FINISH 不允许重置；其余状态都可回到 DISPATCH 重新派工
        if (Objects.equals(cur, TestPlanStatusContants.PLAN_STATUS_FINISH)) {
            return ResponseFactory.failure(illegalTransitionMsg(cur, "重置"));
        }
        testPlan.setStatus(TestPlanStatusContants.PLAN_STATUS_DISPATCH);
        testPlan.setActualStartTime(null);
        testPlan.setActualEndTime(null);
        int rows = testPlanMapper.updateByPrimaryKeySelective(testPlan);
        if (rows <= 0) {
            return ResponseFactory.failure("更新计划状态失败");
        }
        // 级联：把该计划下所有 ExeFunction 状态重置为 UNEXE，避免遗留中间态
        ExeFunction template = new ExeFunction();
        template.setExeStatus(TestPlanStatusContants.PLAN_STATUS_UNEXE);
        ExeFunctionExample example = new ExeFunctionExample();
        example.createCriteria().andPlanIdEqualTo(planId);
        exeFunctionMapper.updateByExampleSelective(template, example);

        recordOperationLog("测试计划", "重置", "计划", planId, testPlan.getPlanName());
        return ResponseFactory.success("计划已重置");
    }

    /**
     * 状态推进通用模板。校验当前态在 allowedFrom 内 → 写入 to。失败返回 failure，不抛异常。
     */
    private Response moveToStatus(String planId, String actionLabel, Integer to, List<Integer> allowedFrom) {
        if (StringUtils.isBlank(planId)) {
            return ResponseFactory.failure("计划 ID 不能为空");
        }
        TestPlan testPlan = testPlanMapper.selectByPrimaryKey(planId);
        if (testPlan == null) {
            return ResponseFactory.failure("未找到对应的测试计划");
        }
        Integer cur = testPlan.getStatus();
        if (cur == null || !allowedFrom.contains(cur)) {
            return ResponseFactory.failure(illegalTransitionMsg(cur, actionLabel));
        }
        testPlan.setStatus(to);
        int rows = testPlanMapper.updateByPrimaryKeySelective(testPlan);
        if (rows <= 0) {
            return ResponseFactory.failure("更新计划状态失败");
        }
        recordOperationLog("测试计划", actionLabel, "计划", planId, testPlan.getPlanName());
        return ResponseFactory.success(actionLabel + "成功");
    }

    /** 状态流转拒绝时统一文案；带可读状态名便于排查。*/
    private static String illegalTransitionMsg(Integer cur, String action) {
        String name = cur == null ? "未知" : TestPlanEnum.getValue(cur);
        return "当前计划状态[" + (name == null ? cur : name) + "]不允许执行[" + action + "]";
    }

    private void recordOperationLog(String module, String action, String targetType, String targetId, String detail) {
        try {
            OperationLog log = new OperationLog();
            String currentUser = UserContextHolder.getCurrentUser();
            log.setOperatorId(currentUser);
            log.setOperatorName(currentUser);
            log.setModule(module);
            log.setAction(action);
            log.setTargetType(targetType);
            log.setTargetId(targetId);
            log.setDetail(detail != null ? (detail.length() > 500 ? detail.substring(0, 500) : detail) : null);
            log.setCreateTime(new Date());
            operationLogService.record(log);
        } catch (Exception e) {
            LOG.debug("操作日志记录忽略: {}", e.getMessage());
        }
    }
}
