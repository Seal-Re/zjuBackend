import request from './request'

// 1. 创建计划 (已确认：正确)
export const createTestPlan = (data: any) => {
  return request({
    url: '/planner/plan/createTestPlan',
    method: 'post',
    data
  })
}

// 2. 更新计划 (新增)
export const updateTestPlan = (data: any) => {
  return request({
    url: '/planner/plan/updateTestPlan',
    method: 'post',
    data
  })
}

// 3. 添加备注 (新增 - 注意权限区别逻辑在后端，前端调用方式类似更新)
export const remarkTestPlan = (data: any) => {
  return request({
    url: '/planner/plan/remarkTestPlan',
    method: 'post',
    data
  })
}

// 4. 删除单个计划 (新增)
export const deleteSingleTestPlan = (planId: string) => {
  return request({
    url: `/planner/plan/deleteTestPlan/${planId}`,
    method: 'delete'
  })
}

// 5. 批量删除计划 (新增)
export const deleteTestPlanWithBatch = (data: any) => {
  return request({
    url: '/planner/plan/deleteTestPlanWithBatch',
    method: 'delete',
    data // 注意：有些 http 库(如 axios) delete 请求传 body 需要写在 { data: ... } 中，视封装而定
  })
}

// 6. 查找全部 (修正：URL 改为 /listAll)
export const getTestPlans = (params?: any) => {
    return request({
        url: '/planner/plan/listAll', 
        method: 'get',
        params 
    })
}

// 7. 派发/同步计划 (类型建议改为 string)
export const dispatchPlan = (planId: string) => {
    return request({
        url: `/planner/plan/dispatch/${planId}`,
        method: 'get'
    })
}

// 8. 开始计划 (类型建议改为 string)
export const startPlan = (planId: string) => {
    return request({
        url: `/planner/plan/start/${planId}`,
        method: 'post'
    })
}

// 9. 暂停计划 (类型建议改为 string)
export const pausePlan = (planId: string) => {
    return request({
        url: `/planner/plan/pause/${planId}`,
        method: 'post'
    })
}
