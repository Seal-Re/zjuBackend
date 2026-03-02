/**
 * 用户与身份管理 API
 * 规范：POST/GET/PUT 用户、重置密码
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

/** 注册/创建用户 */
export function createUser(data: Partial<UserDto>) {
  return authRequest.post<any, UserDto>('/api/v1/users', data)
}

/** 获取指定用户信息 */
export function getUser(id: string) {
  return authRequest.get<any, UserDto>(`/api/v1/users/${id}`)
}

/** 更新用户（PUT/PATCH） */
export function updateUser(id: string, data: Partial<UserDto>) {
  return authRequest.put<any, UserDto>(`/api/v1/users/${id}`, data)
}

/** 强制重置密码 */
export function resetUserPassword(id: string, newPassword: string) {
  return authRequest.post<any, { userId: string; message?: string }>(
    `/api/v1/users/${id}/password/reset`,
    { newPassword }
  )
}
