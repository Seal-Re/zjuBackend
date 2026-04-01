import request from './request'

/** 设备主数据 CRUD：后端实现前仅占位，路径约定见 docs/DEVICE_API_SPEC.md */

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

export function getDeviceList(params?: {
  page?: number
  size?: number
  name?: string
  type?: string
  status?: number
}) {
  return request({
    url: '/devices/list',
    method: 'get',
    params
  })
}

export function getDevice(id: string) {
  return request({
    url: `/devices/${id}`,
    method: 'get'
  })
}

export function createDevice(data: Partial<DeviceDto>) {
  return request({
    url: '/devices',
    method: 'post',
    data
  })
}

export function updateDevice(id: string, data: Partial<DeviceDto>) {
  return request({
    url: `/devices/${id}`,
    method: 'put',
    data
  })
}

export function deleteDevice(id: string) {
  return request({
    url: `/devices/${id}`,
    method: 'delete'
  })
}
