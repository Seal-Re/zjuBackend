import axios from 'axios'
import { ElMessage } from 'element-plus'

// 401 触发时统一清 token 并跳登录，避免用户在过期 token 下"点啥都报错但回不到登录"
function handleUnauthorized() {
  if (location.pathname === '/login') return
  localStorage.removeItem('access_token')
  localStorage.removeItem('refresh_token')
  const redirect = encodeURIComponent(location.pathname + location.search)
  location.replace(`/login?redirect=${redirect}`)
}

const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '/api',
  timeout: 15000
})

service.interceptors.request.use(
  config => {
    const token = localStorage.getItem('access_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => Promise.reject(error)
)

service.interceptors.response.use(
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

export default service
