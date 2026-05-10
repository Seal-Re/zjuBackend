import request from '@/api/request'

export function getModuleTree(funId: number) {
  return request({
    url: '/designer/module/treeByFunId',
    method: 'get',
    params: { funId }
  })
}

export function addCase(data: Record<string, unknown>) {
  return request({
    url: '/designer/case/add',
    method: 'post',
    data
  })
}

export function updateCase(data: Record<string, unknown>) {
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

export function addStep(data: Record<string, unknown>) {
  return request({
    url: '/designer/step/add',
    method: 'post',
    data
  })
}

export function updateStep(data: Record<string, unknown>) {
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
    params: { StepId: stepId }
  })
}

export function deleteModule(moduleId: number) {
  return request({
    url: '/designer/module/delete',
    method: 'post',
    params: { ModuleId: moduleId }
  })
}
