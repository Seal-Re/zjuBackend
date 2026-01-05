import request from './request'

export const getExecutionTasks = (functionId: string) => {
    // "POST /exeFunction/getinexe/{functionId} (注意：此处作为查询使用)"
    // So it is a POST but used for query.
    return request({
        url: `/exeFunction/getinexe/${functionId}`,
        method: 'post'
    })
}

export const saveExecutionLog = (data: any) => {
    return request({
        url: '/exeFunction/log/save',
        method: 'post',
        data
    })
}

export const startExecution = (data: any) => {
    return request({
        url: '/exeFunction/',
        method: 'post',
        data
    })
}

export const pauseExecution = (id: string) => {
    return request({
        url: `/exeFunction/pause/${id}`,
        method: 'post'
    })
}
