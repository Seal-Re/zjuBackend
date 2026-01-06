import request from './request'

export const getExecutionTasks = (StepId: string) => {
    // "POST /exeStep/getinexe/{StepId} (注意：此处作为查询使用)"
    // So it is a POST but used for query.
    return request({
        url: `/exeStep/getinexe/${StepId}`,
        method: 'post'
    })
}

export const saveExecutionLog = (data: any) => {
    return request({
        url: '/exeStep/log/save',
        method: 'post',
        data
    })
}

export const startExecution = (data: any) => {
    return request({
        url: '/exeStep/',
        method: 'post',
        data
    })
}

export const pauseExecution = (id: string) => {
    return request({
        url: `/exeStep/pause/${id}`,
        method: 'post'
    })
}
