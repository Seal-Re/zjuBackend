import request from './request'

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