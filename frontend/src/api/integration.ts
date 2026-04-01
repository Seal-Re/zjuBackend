import request from './request'

export function getDeviceTopics() {
  return request({
    url: '/integration/device/topics',
    method: 'get'
  })
}
