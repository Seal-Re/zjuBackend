import request from './request'

export const createTestPlan = (data: Record<string, unknown>) => {
  return request({
    url: '/planner/plan/createTestPlan',
    method: 'post',
    data
  })
}

export const updateTestPlan = (data: Record<string, unknown>) => {
  return request({
    url: '/planner/plan/updateTestPlan',
    method: 'post',
    data
  })
}

export const remarkTestPlan = (data: Record<string, unknown>) => {
  return request({
    url: '/planner/plan/remarkTestPlan',
    method: 'post',
    data
  })
}

export const deleteSingleTestPlan = (planId: string) => {
  return request({
    url: `/planner/plan/deleteTestPlan/${planId}`,
    method: 'delete'
  })
}

export const deleteTestPlanWithBatch = (data: Record<string, unknown>) => {
  return request({
    url: '/planner/plan/deleteTestPlanWithBatch',
    method: 'delete',
    data
  })
}

export const getTestPlans = (params?: Record<string, unknown>) => {
  return request({
    url: '/planner/plan/listAll',
    method: 'get',
    params
  })
}

export const dispatchPlan = (planId: string) => {
  return request({
    url: `/planner/plan/dispatch/${planId}`,
    method: 'get'
  })
}

export const startPlan = (planId: string) => {
  return request({
    url: `/planner/plan/start/${planId}`,
    method: 'post'
  })
}

export const pausePlan = (planId: string) => {
  return request({
    url: `/planner/plan/pause/${planId}`,
    method: 'post'
  })
}
