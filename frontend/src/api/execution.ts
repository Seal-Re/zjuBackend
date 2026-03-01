import request from './request'

// ========== ExeFunction（执行功能）==========
// 获取指定计划下正在执行的功能列表 (对应后端 GET /exeFunction/testFunctions/inexe/{planId})
export const getExeFunctionsByPlanId = (planId: string) => {
  return request({
    url: `/exeFunction/testFunctions/inexe/${planId}`,
    method: 'get'
  })
}

// 根据功能ID获取执行功能详情 (对应后端 GET /exeFunction/testFunctions/id/{functionId})
export const getExeFunctionById = (functionId: string) => {
  return request({
    url: `/exeFunction/testFunctions/id/${functionId}`,
    method: 'get'
  })
}

// ========== ExeStep（执行步骤）==========
// 1. 获取指定测试作业计划下的步骤 (对应后端 /getinexe/{functionId})
export const getExeStepsByFunction = (functionId: string) => {
  return request({
    url: `/exeStep/getinexe/${functionId}`,
    method: 'get'
  })
}

// 2. 批量暂停步骤 (对应后端 /pause/{exeFunctionId})
export const pauseExeFunction = (exeFunctionId: string) => {
  return request({
    url: `/exeStep/pause/${exeFunctionId}`,
    method: 'post'
  })
}

// 3. 步骤单体操作 (对应后端 /stepOperate)
// params: { exeStepId: string, option: string }
export const operateStep = (data: { exeStepId: string, option: string }) => {
  return request({
    url: '/exeStep/stepOperate',
    method: 'post',
    data
  })
}

// 4. 步骤执行，发送设备指令 (对应后端 /do)
export const executeStepCommand = (data: any) => {
  return request({
    url: '/exeStep/do',
    method: 'post',
    data
  })
}

// 5. 保存日志 (对应后端 /log/save)
export const saveExecutionLog = (data: any) => {
  return request({
    url: '/exeStep/log/save',
    method: 'post',
    data
  })
}