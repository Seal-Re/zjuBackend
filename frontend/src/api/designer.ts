import request from './request'

export const getTestBaseWithLimit = (params: Record<string, unknown>) => {
  return request({
    url: '/testBase/getTestBaseWithLimit',
    method: 'get',
    params
  })
}

export const getTestFunctions = (params: Record<string, unknown>) => {
  return request({
    url: '/designer/testFunction/listByBaseId',
    method: 'get',
    params
  })
}

export const getTestSuites = (params: Record<string, unknown>) => {
  return request({
    url: '/designer/testSuite/listByBaseId',
    method: 'get',
    params
  })
}

export const getCheckTestFunction = (params: Record<string, unknown>) => {
  return request({
    url: '/designer/testFunction/getCheckTestFunction',
    method: 'get',
    params
  })
}

export const getCheckTestSuite = (params: Record<string, unknown>) => {
  return request({
    url: '/designer/testSuite/getCheckTestSuite',
    method: 'get',
    params
  })
}

export const createTestFunction = (data: Record<string, unknown>) => {
  return request({
    url: '/designer/testFunction/add',
    method: 'post',
    data
  })
}

export const addModule = (data: Record<string, unknown>) => {
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

export const createTestSuite = (data: Record<string, unknown>) => {
  return request({
    url: '/designer/testSuite/add',
    method: 'post',
    data
  })
}

export const bindFunctionToSuite = (data: Record<string, unknown>) => {
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

export const updateTestFunction = (data: Record<string, unknown>) => {
  return request({
    url: '/designer/testFunction/update',
    method: 'post',
    data
  })
}

export const updateTestSuite = (data: Record<string, unknown>) => {
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
