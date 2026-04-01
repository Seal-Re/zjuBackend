import request from './request'

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
