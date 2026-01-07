import request from '@/api/request'

// Adjust path based on your setup. Usually just '/...' if proxy is set up.
// Memory says: Vite proxies  to backend.

export function getModuleTree(funId: number) {
  return request({
    url: '/designer/module/treeByFunId',
    method: 'get',
    params: { funId }
  })
}

// Case APIs
export function addCase(data: any) {
  return request({
    url: '/designer/case/add',
    method: 'post',
    data
  })
}

export function updateCase(data: any) {
  return request({
    url: '/designer/case/update',
    method: 'post',
    data
  })
}

export function deleteCase(caseId: number) {
  return request({
    url: '/designer/case/delete',
    method: 'post',
    params: { caseId }
  })
}

// Step APIs
export function addStep(data: any) {
  return request({
    url: '/designer/step/add',
    method: 'post',
    data
  })
}

export function updateStep(data: any) {
  return request({
    url: '/designer/step/update',
    method: 'post',
    data
  })
}

export function deleteStep(stepId: number) {
  return request({
    url: '/designer/step/delete',
    method: 'post',
    params: { StepId: stepId } // Note: Param name matches Controller (capital S)
  })
}
