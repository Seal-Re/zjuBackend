import request from './request'

// Module Library

export const getTestBaseWithLimit = (params: any) => {
  return request({
    url: '/testBase/getTestBaseWithLimit',
    method: 'get',
    params
  })
}

export const getTestFunctions = (params: any) => {
  return request({
    url: '/designer/testFunction/listByBaseId', // Need to confirm this path, usually list endpoints exist
    method: 'get',
    params
  })
}

export const getTestSuites = (params: any) => {
  return request({
    url: '/designer/testSuite/listByBaseId', 
    method: 'get',
    params
  })
}

export const getCheckTestFunction = (params: any) => {
  return request({
    url: '/designer/testFunction/getCheckTestFunction', 
    method: 'get',
    params
  })
}

export const getCheckTestSuite = (params: any) => {
  return request({
    url: '/designer/testSuite/getCheckTestSuite', 
    method: 'get',
    params
  })
}

export const createTestFunction = (data: any) => {
  return request({
    url: '/designer/testFunction/add',
    method: 'post',
    data
  })
}

// Function Hierarchy
export const addModule = (data: any) => {
  return request({
    url: '/designer/module/add',
    method: 'post',
    data
  })
}

export const checkTestFunction = (data: any) => {
    // API: POST /designer/testFunction/check?funId=...&checkWorker=...
    // The requirement says "Input: funId=1209&checkWorker=worker1&level=0" which implies form data or query params.
    // However, usually axios 'data' is JSON. If backend expects params, we use params.
    // If backend expects x-www-form-urlencoded, we use data with QS or URLSearchParams.
    // Given the example "funId=1209...", it looks like query params or form-urlencoded.
    // I'll use params for now, or form data if it fails.
    // Let's assume JSON for consistency unless specified otherwise, but the example shows query string format.
    // "Input: funId=1209&checkWorker=worker1&level=0"

    // Let's try to pass as params (query string) for safety if it's a POST.
    const params = new URLSearchParams()
    for (const key in data) {
        params.append(key, data[key])
    }
    return request({
        url: '/designer/testFunction/check',
        method: 'post',
        data: params,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
}


// Suite Library
export const createTestSuite = (data: any) => {
  return request({
    url: '/designer/testSuite/add',
    method: 'post',
    data
  })
}

export const bindFunctionToSuite = (data: any) => {
  return request({
    url: '/functionSuite/createFunctionSuite',
    method: 'post',
    data
  })
}

export const checkTestSuite = (data: any) => {
    const params = new URLSearchParams()
    for (const key in data) {
        params.append(key, data[key])
    }
    return request({
        url: '/designer/testSuite/check',
        method: 'post',
        data: params,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
}

// Aux API：获取工艺模块列表（支持可选 testBaseId 筛选，与后端 GET /list 一致）
export const getFunctionList = (params?: { testBaseId?: number }) => {
    return request({
        url: '/designer/testFunction/list',
        method: 'get',
        params
    })
}

// 删除工艺模块（对应后端 POST /designer/testFunction/delete/{funId}）
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

// FunctionSuite relation - need to fetch functions for a suite
// If backend doesn't have direct "get functions by suiteId", we assume details might come from getTestSuiteDetail or similar
// Or use FunctionSuiteController

export const updateTestFunction = (data: any) => {
    return request({
        url: `/designer/testFunction/update`,
        method: 'post',
        data: data
    })
}

export const updateTestSuite = (data: any) => {
    return request({
        url: `/designer/testSuite/update`,
        method: 'post',
        data: data
    })
}

// 删除测试清单（对应后端 POST /designer/testSuite/delete，body 为 suiteId）
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
        url: `/designer/testFunction/submit`,
        method: 'post',
        data: funId, 
        headers: {
          'Content-Type': 'application/json'
        }
    })
}

export const submitTestSuite = (suiteId: number) => {
    return request({
        url: `/designer/testSuite/submit`,
        method: 'post',
        data: suiteId, 
        headers: {
          'Content-Type': 'application/json'
        }
    })
}

export const getRely = (suiteId: number) => {
    return request({
        url: `/functionSuite/rely`,
        method: 'get',
        params: { suiteId }
    })
}

// 获取全部功能-清单关联 (对应后端 GET /functionSuite/listAll)
export const listAllFunctionSuite = () => {
    return request({
        url: '/functionSuite/listAll',
        method: 'get'
    })
}

// 删除功能-清单关联 (对应后端 POST /functionSuite/deleteFunctionSuite)
export const deleteFunctionSuite = (data: { suiteId?: number; funId?: number; [key: string]: any }) => {
    return request({
        url: '/functionSuite/deleteFunctionSuite',
        method: 'post',
        data
    })
}

