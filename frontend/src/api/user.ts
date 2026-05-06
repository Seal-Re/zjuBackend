/**
 * 用户管理 API（RBAC 后台用）。
 * 当前前端无 UI 入口直接调用，函数已实现等待后续后台页面接入；
 * 切勿删除——保留接口契约方便后端联调对齐 mock 服务路径。
 */
import authRequest from './authRequest'

export interface UserDto {
  id?: string
  username: string
  name?: string
  email?: string
  enabled?: boolean
  createdAt?: string
}

export function createUser(data: Partial<UserDto>) {
  return authRequest.post<any, UserDto>('/api/v1/users', data)
}

export function getUser(id: string) {
  return authRequest.get<any, UserDto>(`/api/v1/users/${id}`)
}

export function updateUser(id: string, data: Partial<UserDto>) {
  return authRequest.put<any, UserDto>(`/api/v1/users/${id}`, data)
}

export function resetUserPassword(id: string, newPassword: string) {
  return authRequest.post<any, { userId: string; message?: string }>(
    `/api/v1/users/${id}/password/reset`,
    { newPassword }
  )
}
