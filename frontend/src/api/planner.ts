import request from './request'

export const createTestPlan = (data: any) => {
  return request({
    url: '/planner/plan/createTestPlan',
    method: 'post',
    data
  })
}

export const getTestPlans = (params: any) => {
    return request({
        url: '/planner/plan/list', // Placeholder
        method: 'get',
        params
    })
}

export const dispatchPlan = (planId: number) => {
    return request({
        url: `/planner/plan/dispatch/${planId}`,
        method: 'get'
    })
}

export const startPlan = (planId: number) => {
    return request({
        url: `/planner/plan/start/${planId}`,
        method: 'post'
    })
}

export const pausePlan = (planId: number) => {
    return request({
        url: `/planner/plan/pause/${planId}`,
        method: 'post'
    })
}
