import request from '@/api/request'

// Adjust path based on your setup. Usually just '/api/...' if proxy is set up.
// Memory says: Vite proxies /api to backend.

export function getModuleTree(funId: number) {
  return request({
    url: '/api/designer/module/treeByFunId',
    method: 'get',
    params: { funId }
  })
}

// Case APIs
export function addCase(data: any) {
  return request({
    url: '/api/designer/case/add',
    method: 'post',
    data
  })
}

export function updateCase(data: any) {
  return request({
    url: '/api/designer/case/update',
    method: 'post',
    data
  })
}

export function deleteCase(caseId: number) {
  return request({
    url: '/api/designer/case/delete',
    method: 'post',
    params: { caseId }
  })
}

// Step APIs
export function addStep(data: any) {
  return request({
    url: '/api/designer/step/add',
    method: 'post',
    data
  })
}

export function updateStep(data: any) {
  return request({
    url: '/api/designer/step/update',
    method: 'post',
    data
  })
}

export function deleteStep(stepId: number) {
  return request({
    url: '/api/designer/step/delete',
    method: 'post',
    params: { StepId: stepId } // Note: Param name matches Controller (capital S)
  })
}

export function deleteModule(moduleId: number) {
    return request({
        url: '/api/designer/module/delete',
        method: 'post',
        params: { ModuleId: moduleId }
    })
}
