package com.hengtiansoft.fastop.service.planner.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.hengtiansoft.fastop.base.common.constants.Status.CommonConstants;
import com.hengtiansoft.fastop.base.common.constants.Status.StatusContants;
import com.hengtiansoft.fastop.base.common.constants.Status.TestPlanStatusContants;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.FunctionSuiteDto;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionRelyMapper;
import com.hengtiansoft.fastop.model.designer.entity.*;
import com.hengtiansoft.fastop.model.planner.dto.ExeFunctionMapper;
import com.hengtiansoft.fastop.model.planner.dto.ExeStepMapper;
import com.hengtiansoft.fastop.model.planner.entity.*;
import com.hengtiansoft.fastop.service.designer.service.FunctionSuiteService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionService;
import com.hengtiansoft.fastop.service.designer.service.TestFunctionStepService;
import com.hengtiansoft.fastop.service.planner.service.ExeFunctionService;
import com.hengtiansoft.fastop.service.planner.service.ExeStepService;
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
public class ExeFunctionServiceImpl implements ExeFunctionService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

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
            return ResponseFactory.success(java.util.Collections.emptyList());
        }

        return ResponseFactory.success(exeFunctions);
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
    @Transactional(rollbackFor = Exception.class)
    public boolean conveyTestFunction2ExeFunction(Integer suiteId, String planId) {
        List<FunctionSuiteDto> functionSuites = functionSuiteService.listDtoBySuiteWithExample(suiteId);
        if (CollectionUtil.isEmpty(functionSuites)) {
            return true;
        }

        // 批量拉 TestFunction，避免在循环里逐个查询
        List<Integer> funIds = functionSuites.stream()
                .map(FunctionSuiteDto::getFunId)
                .collect(Collectors.toList());
        Map<Integer, TestFunction> testFunctionMap = testFunctionService.getFunctionsByIds(funIds);
        if (CollectionUtil.isEmpty(testFunctionMap)) {
            return true;
        }

        for (FunctionSuiteDto functionSuiteDto : functionSuites) {
            TestFunction testFunction = testFunctionMap.get(functionSuiteDto.getFunId());
            if (testFunction == null) continue;

            FunctionSuite functionSuite = new FunctionSuite();
            BeanUtils.copyProperties(functionSuiteDto, functionSuite);
            functionSuite.setTestFunId(functionSuiteDto.getFunId());

            String exeFunId = saveExeFunction(functionSuite, planId, testFunction);
            // TODO 性能优化（已记 KNOWN_ISSUES）：conveyTestStep2ExeStep 内部为
            // module→case→step 三层 N+1，单次派发可能产生数十至上百次 SQL，
            // 待批量 IN 查询 + bulk insert 改造。
            exeStepService.conveyTestStep2ExeStep(testFunction.getFunId(), exeFunId);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public String saveExeFunction(FunctionSuite functionSuite, String planId, TestFunction function) {
        // 显式抛错而非返回 null：上游用返回值作为 exeStep 的外键 exeFunctionId，
        // 一旦 null 会导致 ExeStep 表外键写入空值，做静默数据损坏；
        // 在事务边界内抛异常配合 @Transactional(rollbackFor = Exception.class) 整体回滚。
        if (function == null) {
            throw new IllegalArgumentException("saveExeFunction: TestFunction 不能为 null");
        }
        if (planId == null || planId.trim().isEmpty()) {
            throw new IllegalArgumentException("saveExeFunction: planId 不能为空");
        }
        if (functionSuite == null) {
            throw new IllegalArgumentException("saveExeFunction: FunctionSuite 不能为 null");
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

        // 查询该功能在当前套件中的依赖关系
        TestFunctionRelyExample relyExample = new TestFunctionRelyExample();
        relyExample.createCriteria()
                .andTestFunctionIdEqualTo(function.getFunId())
                .andSuiteIdEqualTo(functionSuite.getSuiteId())
                .andDeletedEqualTo(false);
        List<TestFunctionRely> relies = testFunctionRelyMapper.selectByExample(relyExample);

        List<Integer> relyIds = relies.stream()
                .map(TestFunctionRely::getRelyFunctionId)
                .collect(Collectors.toList());

        if (!relyIds.isEmpty()) {
            eFunction.setDependsOn(StringUtils.join(relyIds, ','));
            eFunction.setIsReady(false);
        } else {
            eFunction.setDependsOn("");
            eFunction.setIsReady(true);
        }

        eFunction.setCaution(function.getCaution());
        eFunction.setDetectId(function.getDetectId());

        exeFunctionMapper.insertSelective(eFunction);
        return exeFunId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    // --- Ported Methods from Reference ---

    /**
     * 通过testPlanId获取用例数量
     */
    public int countExeFunctionByPlanId(String planId) {
        ExeFunctionExample example = new ExeFunctionExample();
        example.createCriteria().andPlanIdEqualTo(planId);
        long count = exeFunctionMapper.countByExample(example);
        return (int) count;
    }

    /*
     * TODO: Feature pending due to missing entity [TestFunctionGroup], [User], [ExecutorGroup]
     * Method: listSortExeFunction
     * Reference logic heavily relies on TestFunctionGroup for sorting/grouping, and User/ExecutorGroup for names.
     * Logic commented out.
     */
    // public Map<String, List<ExeFunctionResponseDto>> listSortExeFunction(String planId) { ... }

    /**
     * 根据操作指令推进执行功能的状态，带合法性校验。
     * 状态流转：
     *   runFunction  : UNEXE(0) / PAUSE(3)  → EXEING(2)
     *   runPause     : EXEING(2)             → PAUSE(3)
     *   doFinish     : EXEING(2)             → FINISH(6)
     *   doInvalid    : 任意未完成状态        → UNEXE(0)（重置）
     *   restartRun   : PAUSE(3)              → EXEING(2)
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateFunctionStatusByOption(String exeFunctionId, String option) {
        ExeFunction exeFunction = exeFunctionMapper.selectByPrimaryKey(exeFunctionId);
        if (exeFunction == null) return false;

        int current = exeFunction.getExeStatus();
        int targetStatus = -1;

        switch (option) {
            case "runFunction":
                if (current == TestPlanStatusContants.PLAN_STATUS_UNEXE
                        || current == TestPlanStatusContants.PLAN_STATUS_PAUSE) {
                    targetStatus = TestPlanStatusContants.PLAN_STATUS_EXEING;
                }
                break;
            case "runPause":
                if (current == TestPlanStatusContants.PLAN_STATUS_EXEING) {
                    targetStatus = TestPlanStatusContants.PLAN_STATUS_PAUSE;
                }
                break;
            case "doFinish":
                if (current == TestPlanStatusContants.PLAN_STATUS_EXEING) {
                    targetStatus = TestPlanStatusContants.PLAN_STATUS_FINISH;
                }
                break;
            case "doInvalid":
                if (current != TestPlanStatusContants.PLAN_STATUS_FINISH) {
                    targetStatus = TestPlanStatusContants.PLAN_STATUS_UNEXE;
                }
                break;
            case "restartRun":
                if (current == TestPlanStatusContants.PLAN_STATUS_PAUSE) {
                    targetStatus = TestPlanStatusContants.PLAN_STATUS_EXEING;
                }
                break;
            default:
                LOG.warn("updateFunctionStatusByOption: 未知操作指令 [{}]", option);
                return false;
        }

        if (targetStatus == -1) {
            LOG.warn("updateFunctionStatusByOption: 当前状态 [{}] 不允许执行操作 [{}]", current, option);
            return false;
        }

        exeFunction.setExeStatus(targetStatus);
        return exeFunctionMapper.updateByPrimaryKeySelective(exeFunction) > 0;
    }

    /**
     * 更新指定测试计划下正在执行的用例为暂停状态
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCaseExeToPause(String planId) {
        boolean result = true;
        ExeFunctionExample example = new ExeFunctionExample();
        example.createCriteria().andPlanIdEqualTo(planId).andExeStatusEqualTo(TestPlanStatusContants.PLAN_STATUS_EXEING);
        List<ExeFunction> exeFunctions = exeFunctionMapper.selectByExample(example);

        for (ExeFunction func : exeFunctions) {
            func.setExeStatus(TestPlanStatusContants.PLAN_STATUS_PAUSE);

            // Call ExeStepService to pause steps
            // result = exeStepService.updateStepExeToPause(func.getExeFunctionId());
            // Target ExeStepService needs this method.

            if (result) {
                exeFunctionMapper.updateByPrimaryKeySelective(func);
            }
        }
        return result;
    }

    /**
     * 获取指定测试作业计划中是否有未完成的功能
     */
    public boolean isExistIncompleteFunctions(String planId) {
        ExeFunctionExample example = new ExeFunctionExample();
        example.createCriteria()
               .andPlanIdEqualTo(planId)
               .andExeStatusNotEqualTo(TestPlanStatusContants.PLAN_STATUS_FINISH);
        return exeFunctionMapper.countByExample(example) > 0;
    }

}
