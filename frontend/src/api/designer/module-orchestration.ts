import request from '@/api/request'

/** 子用例（Case）请求载荷，对齐后端 TestFunctionCase 实体可写字段 */
export interface CaseRequest {
  caseId?: number
  caseName?: string
  caseDescription?: string
  moduleId?: number
  [key: string]: unknown
}

/** 步骤（Step）请求载荷，对齐后端 TestFunctionStep 实体可写字段 + 自助 EMS 报文 */
export interface StepRequest {
  stepId?: number
  caseId?: number
  stepName?: string
  stepDescription?: string
  stepOperation?: string
  stepObj?: string
  stepPurpose?: string
  stepCommandExample?: string
  stepCommandParams?: string
  [key: string]: unknown
}

export function getModuleTree(funId: number) {
  return request({
    url: '/designer/module/treeByFunId',
    method: 'get',
    params: { funId }
  })
}

export function addCase(data: CaseRequest) {
  return request({
    url: '/designer/case/add',
    method: 'post',
    data
  })
}

export function updateCase(data: CaseRequest) {
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

export function addStep(data: StepRequest) {
  return request({
    url: '/designer/step/add',
    method: 'post',
    data
  })
}

export function updateStep(data: StepRequest) {
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
    params: { stepId }
  })
}

export function deleteModule(moduleId: number) {
  return request({
    url: '/designer/module/delete',
    method: 'post',
    params: { moduleId }
  })
}
