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

export function getRoles() {
  return authRequest.get<any, RoleDto[]>('/api/v1/roles')
}

export function createRole(data: { code: string; name?: string; description?: string }) {
  return authRequest.post<any, RoleDto>('/api/v1/roles', data)
}

export function bindRolePermissions(roleId: string, permissions: string[] | PermissionDto[]) {
  const codes = permissions.map(p => (typeof p === 'string' ? p : p.code))
  return authRequest.post<any, { roleId: string; permissions: string[] }>(
    `/api/v1/roles/${roleId}/permissions`,
    { permissions: codes }
  )
}

export function getPermissions() {
  return authRequest.get<any, PermissionDto[]>('/api/v1/permissions')
}

export function getUserRoles(userId: string) {
  return authRequest.get<any, RoleDto[]>(`/api/v1/users/${userId}/roles`)
}
