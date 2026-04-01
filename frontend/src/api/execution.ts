import request from './request'

export const getExeFunctionsByPlanId = (planId: string) => {
  return request({
    url: `/exeFunction/testFunctions/inexe/${planId}`,
    method: 'get'
  })
}

export const getExeFunctionById = (functionId: string) => {
  return request({
    url: `/exeFunction/testFunctions/id/${functionId}`,
    method: 'get'
  })
}

export const getExeStepsByFunction = (functionId: string) => {
  return request({
    url: `/exeStep/getinexe/${functionId}`,
    method: 'get'
  })
}

export const pauseExeFunction = (exeFunctionId: string) => {
  return request({
    url: `/exeStep/pause/${exeFunctionId}`,
    method: 'post'
  })
}

export const operateStep = (data: { exeStepId: string; option: string }) => {
  return request({
    url: '/exeStep/stepOperate',
    method: 'post',
    data
  })
}

export const executeStepCommand = (data: Record<string, unknown>) => {
  return request({
    url: '/exeStep/do',
    method: 'post',
    data
  })
}

export const previewEmsMessage = (exeStepId: string) => {
  return request({
    url: '/exeStep/ems/preview',
    method: 'get',
    params: { exeStepId }
  })
}

export const saveExecutionLog = (data: Record<string, unknown>) => {
  return request({
    url: '/exeStep/log/save',
    method: 'post',
    data
  })
}
