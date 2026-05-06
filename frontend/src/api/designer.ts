import request from './request'

/**
 * 业务请求载荷接口集合（沿用历史宽松字段，保留 [key:string]: unknown 透传，
 * 渐进式收紧类型而不破坏现有调用方）。
 */
export interface BaseQueryParams {
  testBaseId?: number
  page?: number
  size?: number
  [key: string]: unknown
}

export interface TestFunctionRequest {
  funId?: number | string
  num?: number
  funName?: string
  military?: boolean
  versionDescription?: string
  planeEffectMin?: number
  planeEffectMax?: number
  testBaseId?: number
  approveStatus?: number
  otherTechFiles?: unknown[]
  devicePool?: unknown[]
  [key: string]: unknown
}

export interface TestSuiteRequest {
  suiteId?: number
  testBaseId?: number
  suiteName?: string
  suiteDesc?: string
  military?: boolean
  submitter?: string
  planeEffectMin?: number
  planeEffectMax?: number
  funIds?: number[]
  [key: string]: unknown
}

export interface ModuleAddRequest {
  moduleName: string
  funId: number
  [key: string]: unknown
}

export interface FunctionSuiteBindRequest {
  suiteId: number
  testFunctions: Array<{ funId: number; num?: number; version?: number }>
  [key: string]: unknown
}

export const listAllBaseStructAndId = () => {
  return request({
    url: '/base/listAllBaseStructAndId',
    method: 'get'
  })
}

export const getTestBaseWithLimit = (params: BaseQueryParams) => {
  return request({
    url: '/testBase/getTestBaseWithLimit',
    method: 'get',
    params
  })
}

export const getTestFunctions = (params: BaseQueryParams) => {
  return request({
    url: '/designer/testFunction/listByBaseId',
    method: 'get',
    params
  })
}

export const getTestSuites = (params: BaseQueryParams) => {
  return request({
    url: '/designer/testSuite/listByBaseId',
    method: 'get',
    params
  })
}

export const getCheckTestFunction = (params: BaseQueryParams) => {
  return request({
    url: '/designer/testFunction/getCheckTestFunction',
    method: 'get',
    params
  })
}

export const getCheckTestSuite = (params: BaseQueryParams) => {
  return request({
    url: '/designer/testSuite/getCheckTestSuite',
    method: 'get',
    params
  })
}

export const createTestFunction = (data: TestFunctionRequest) => {
  return request({
    url: '/designer/testFunction/add',
    method: 'post',
    data
  })
}

export const addModule = (data: ModuleAddRequest) => {
  return request({
    url: '/designer/module/add',
    method: 'post',
    data
  })
}

export const checkTestFunction = (data: {
  funId: number | string
  checkWorker: string
  level: number
  result?: string
  comment?: string
}) => {
  return request({
    url: '/designer/testFunction/check',
    method: 'post',
    params: { funId: data.funId, checkWorker: data.checkWorker, level: data.level }
  })
}

export const createTestSuite = (data: TestSuiteRequest) => {
  return request({
    url: '/designer/testSuite/add',
    method: 'post',
    data
  })
}

export const bindFunctionToSuite = (data: FunctionSuiteBindRequest) => {
  return request({
    url: '/functionSuite/createFunctionSuite',
    method: 'post',
    data
  })
}

export const checkTestSuite = (data: {
  suiteId: number | string
  checkWorker: string
  level: number
  result?: string
  comment?: string
}) => {
  return request({
    url: '/designer/testSuite/check',
    method: 'post',
    params: { suiteId: data.suiteId, checkWorker: data.checkWorker, level: data.level }
  })
}

export const getFunctionList = (params?: { testBaseId?: number }) => {
  return request({
    url: '/designer/testFunction/list',
    method: 'get',
    params
  })
}

export const deleteTestFunction = (funId: number) => {
  return request({
    url: `/designer/testFunction/delete/${funId}`,
    method: 'post'
  })
}

export const getTestSuiteDetail = (suiteId: string | number) => {
  return request({
    url: `/designer/testSuite/get/${suiteId}`,
    method: 'get'
  })
}

export const updateTestFunction = (data: TestFunctionRequest) => {
  return request({
    url: '/designer/testFunction/update',
    method: 'post',
    data
  })
}

export const updateTestSuite = (data: TestSuiteRequest) => {
  return request({
    url: '/designer/testSuite/update',
    method: 'post',
    data
  })
}

export const deleteTestSuite = (suiteId: number) => {
  return request({
    url: '/designer/testSuite/delete',
    method: 'post',
    data: suiteId,
    headers: { 'Content-Type': 'application/json' }
  })
}

export const submitTestFunction = (funId: number) => {
  return request({
    url: '/designer/testFunction/submit',
    method: 'post',
    data: funId,
    headers: { 'Content-Type': 'application/json' }
  })
}

export const submitTestSuite = (suiteId: number) => {
  return request({
    url: '/designer/testSuite/submit',
    method: 'post',
    data: suiteId,
    headers: { 'Content-Type': 'application/json' }
  })
}

export const getRely = (suiteId: number) => {
  return request({
    url: '/functionSuite/rely',
    method: 'get',
    params: { suiteId }
  })
}

export const listAllFunctionSuite = () => {
  return request({
    url: '/functionSuite/listAll',
    method: 'get'
  })
}

export const deleteFunctionSuite = (data: {
  suiteId?: number
  funId?: number
  [key: string]: unknown
}) => {
  return request({
    url: '/functionSuite/deleteFunctionSuite',
    method: 'post',
    data
  })
}
