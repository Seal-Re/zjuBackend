import request from './request'

/** 执行日志：分页查询（按步骤、计划、时间） */
export const getExeLogList = (params: {
  stepId?: string
  planId?: string
  startTime?: string
  endTime?: string
  page?: number
  size?: number
}) => {
  return request({
    url: '/exeStep/log/list',
    method: 'get',
    params
  })
}

/** 操作日志：分页查询 */
export const getOperationLogList = (params: {
  operatorName?: string
  module?: string
  action?: string
  startTime?: string
  endTime?: string
  page?: number
  size?: number
}) => {
  return request({
    url: '/log/operation/list',
    method: 'get',
    params
  })
}
