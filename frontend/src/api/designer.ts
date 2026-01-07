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

export const getCheckTestFunction = (params: any) => {
  return request({
    url: '/designer/testFunction/getCheckTestFunction', 
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

// Aux API
// Need API to get list of modules/functions for the Transfer component
export const getFunctionList = (params: any) => {
    // Reuse list endpoint or specific search
    return request({
        url: '/designer/testFunction/list', // Placeholder
        method: 'get',
        params
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
