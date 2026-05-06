import request from './request'

/**
 * 与后端 TestPlanRequestDto 对齐的请求体。
 * 业务逻辑保持向后兼容（额外字段允许通过 `[k: string]: unknown` 透传），
 * 但常用字段先以显式可选属性给 IDE/编译器留下类型保护。
 */
export interface TestPlanRequest {
  planId?: string
  planName?: string
  planNumber?: string
  planRound?: number
  planStartTime?: string
  planEndTime?: string
  status?: number
  suiteId?: number
  entityStructId?: number
  entityId?: number
  subjectId?: number
  funGroupId?: number
  funGroupIds?: number[]
  areaId?: number
  military?: boolean
  dispatcherId?: string
  commanderId?: string
  management?: string
  forRecordData?: number
  remark?: string
  [key: string]: unknown
}

export interface TestPlanBatchDeleteRequest {
  planIdLists: string[]
  [key: string]: unknown
}

export interface TestPlanQueryParams {
  testBaseId?: number
  status?: number
  planName?: string
  page?: number
  size?: number
  [key: string]: unknown
}

export const createTestPlan = (data: TestPlanRequest) => {
  return request({
    url: '/planner/plan/createTestPlan',
    method: 'post',
    data
  })
}

export const updateTestPlan = (data: TestPlanRequest) => {
  return request({
    url: '/planner/plan/updateTestPlan',
    method: 'post',
    data
  })
}

export const remarkTestPlan = (data: { planId: string; remark: string }) => {
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

export const deleteTestPlanWithBatch = (data: TestPlanBatchDeleteRequest) => {
  return request({
    url: '/planner/plan/deleteTestPlanWithBatch',
    method: 'delete',
    data
  })
}

export const getTestPlans = (params?: TestPlanQueryParams) => {
  return request({
    url: '/planner/plan/listAll',
    method: 'get',
    params
  })
}

export const dispatchPlan = (planId: string) => {
  return request({
    url: `/planner/plan/dispatch/${planId}`,
    method: 'post'
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
