/**
 * 设备管理 API
 * 规范见 docs/DEVICE_API_SPEC.md，待后端实现后对接同一 baseURL 或单独配置
 */
import request from './request'

export interface DeviceDto {
  id?: string
  code: string
  name: string
  type?: string
  status?: number
  description?: string
  createdAt?: string
  updatedAt?: string
}

export interface DeviceListResult {
  list: DeviceDto[]
  total: number
}

/** 设备列表（分页与筛选），路径与后端规范一致 */
export function getDeviceList(params?: { page?: number; size?: number; name?: string; type?: string; status?: number }) {
  return request({
    url: '/devices/list',
    method: 'get',
    params
  })
}

/** 获取单个设备 */
export function getDevice(id: string) {
  return request({
    url: `/devices/${id}`,
    method: 'get'
  })
}

/** 新增设备 */
export function createDevice(data: Partial<DeviceDto>) {
  return request({
    url: '/devices',
    method: 'post',
    data
  })
}

/** 更新设备 */
export function updateDevice(id: string, data: Partial<DeviceDto>) {
  return request({
    url: `/devices/${id}`,
    method: 'put',
    data
  })
}

/** 删除设备 */
export function deleteDevice(id: string) {
  return request({
    url: `/devices/${id}`,
    method: 'delete'
  })
}
