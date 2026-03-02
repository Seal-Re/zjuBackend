/**
 * 认证/用户服务专用请求实例
 * 基地址为独立认证服务（可通过 .env 配置 VITE_AUTH_API），
 * 请求头自动携带 Authorization: Bearer {token}
 */
import axios, { type AxiosInstance } from 'axios'
import { ElMessage } from 'element-plus'

const baseURL = import.meta.env.VITE_AUTH_API || '/auth-api'

const authService: AxiosInstance = axios.create({
  baseURL,
  timeout: 15000,
  headers: { 'Content-Type': 'application/json' }
})

authService.interceptors.request.use(config => {
  const token = localStorage.getItem('access_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}, err => Promise.reject(err))

authService.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200 && res.code !== 201) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || 'Error'))
    }
    return res.data
  },
  error => {
    ElMessage.error(error.response?.data?.message || error.message)
    return Promise.reject(error)
  }
)

export default authService
