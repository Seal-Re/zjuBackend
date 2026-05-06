package com.hengtiansoft.fastop.service.planner.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanDelBatchDto;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanRequestDto;

public interface TestPlanService {

    Response createTestPlan(TestPlanRequestDto testPlanRequestDto);
    Response updateTestPlan(TestPlanRequestDto testPlanRequestDto);

    /** 仅更新备注字段，不修改计划结构信息（权限要求低于 updateTestPlan）*/
    Response remarkTestPlan(String planId, String remark);

    Response deleteSingleTestPlan(String planId);
    Response deleteBatchTestPlan(TestPlanDelBatchDto testPlanDelBatchDto);


    Response listAll();

    /**
     * 测试计划状态机推进接口。状态流向（带转移名）：
     *   DISPATCH(5) ──dispatch──► UNEXE(0)
     *   UNEXE(0)    ──start───►  EXEING(2)
     *   EXEING(2)   ──pause───►  PAUSE(3)
     *   PAUSE(3)    ──start───►  EXEING(2)   (恢复)
     *   EXEING(2)   ──verify──►  VERIFY(1)   (执行完毕，待普检)
     *   VERIFY(1)   ──mverify─►  MVERIFY(4)  (普检通过，待军检)
     *   MVERIFY(4)  ──finish──►  FINISH(6)   (军检通过，完工)
     *   any-non-FINISH ──reset──► DISPATCH(5)
     *
     * 非法转移返回 ResponseFactory.failure，不抛异常以便前端展示。
     */
    Response dispatchPlan(String planId);
    Response startPlan(String planId);
    Response pausePlan(String planId);
    Response verifyPlan(String planId);
    Response mverifyPlan(String planId);
    Response finishPlan(String planId);
    Response resetPlan(String planId);
}
