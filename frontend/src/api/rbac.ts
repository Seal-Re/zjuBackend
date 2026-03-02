/**
 * 权限与角色控制 API（RBAC）
 * 规范：角色列表/创建、权限列表、角色绑定权限、用户角色查询
 */
import authRequest from './authRequest'

export interface RoleDto {
  id: string
  code: string
  name?: string
  description?: string
}

export interface PermissionDto {
  id: string
  code: string
  name?: string
  resource?: string
}

/** 角色列表 */
export function getRoles() {
  return authRequest.get<any, RoleDto[]>('/api/v1/roles')
}

/** 创建角色 */
export function createRole(data: { code: string; name?: string; description?: string }) {
  return authRequest.post<any, RoleDto>('/api/v1/roles', data)
}

/** 为角色绑定权限点 */
export function bindRolePermissions(roleId: string, permissions: string[] | PermissionDto[]) {
  const codes = permissions.map(p => typeof p === 'string' ? p : p.code)
  return authRequest.post<any, { roleId: string; permissions: string[] }>(
    `/api/v1/roles/${roleId}/permissions`,
    { permissions: codes }
  )
}

/** 获取所有权限标识 */
export function getPermissions() {
  return authRequest.get<any, PermissionDto[]>('/api/v1/permissions')
}

/** 查询指定用户的角色集合 */
export function getUserRoles(userId: string) {
  return authRequest.get<any, RoleDto[]>(`/api/v1/users/${userId}/roles`)
}
