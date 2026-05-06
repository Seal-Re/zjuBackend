/**
 * 认证/用户服务专用请求实例
 * 基地址为独立认证服务（可通过 .env 配置 VITE_AUTH_API），
 * 请求头自动携带 Authorization: Bearer {token}
 */
import axios, { type AxiosInstance } from 'axios'
import { ElMessage } from 'element-plus'

// 401 触发时统一清 token 并跳登录
function handleUnauthorized() {
  if (location.pathname === '/login') return
  localStorage.removeItem('access_token')
  localStorage.removeItem('refresh_token')
  const redirect = encodeURIComponent(location.pathname + location.search)
  location.replace(`/login?redirect=${redirect}`)
}

// 默认直连本地认证 mock，避免非 Vite 代理环境下出现 /auth-api 404
const baseURL = import.meta.env.VITE_AUTH_API || 'http://localhost:5000'

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
    if (res.code === 401) {
      handleUnauthorized()
      return Promise.reject(new Error('未登录或登录已过期'))
    }
    if (res.code !== 200 && res.code !== 201) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || 'Error'))
    }
    return res.data
  },
  error => {
    if (error.response?.status === 401) {
      handleUnauthorized()
      return Promise.reject(error)
    }
    ElMessage.error(error.response?.data?.message || error.message)
    return Promise.reject(error)
  }
)

export default authService
