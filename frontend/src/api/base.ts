import request from './request'

export const listAllBaseStructAndId = () => {
  return request({
    url: '/base/listAllBaseStructAndId',
    method: 'get'
  })
}
