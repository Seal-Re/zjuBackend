import axios, { type AxiosInstance } from 'axios'
import { ElMessage } from 'element-plus'

const baseURL =
  import.meta.env.VITE_AUTH_API ||
  (import.meta.env.DEV ? '/auth-api' : 'http://localhost:5000')

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
      ElMessage.error(res.message || res.msg || '请求失败')
      return Promise.reject(new Error(res.message || res.msg || 'Error'))
    }
    return res.data
  },
  error => {
    ElMessage.error(error.response?.data?.message || error.response?.data?.msg || error.message)
    return Promise.reject(error)
  }
)

export default authService
