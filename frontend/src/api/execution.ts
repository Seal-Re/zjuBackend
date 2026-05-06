import request from './request'

/** 与后端 ExeStepCommand 对齐的指令载荷 */
export interface ExeStepCommandPayload {
  exeStepId: string
  command?: string
  url?: string
  topic?: string
  params?: Record<string, unknown>
  [key: string]: unknown
}

/** 与后端 ExeLog 对齐的执行日志写入载荷 */
export interface ExecutionLogPayload {
  exeStepId?: string
  planId?: string
  content: string
  [key: string]: unknown
}

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

export const operateStep = (data: { exeStepId: string; operation: string }) => {
  return request({
    url: '/exeStep/stepOperate',
    method: 'post',
    data
  })
}

export const executeStepCommand = (data: ExeStepCommandPayload) => {
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

export const saveExecutionLog = (data: ExecutionLogPayload) => {
  return request({
    url: '/exeStep/log/save',
    method: 'post',
    data
  })
}
