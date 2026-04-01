import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '/api',
  timeout: 10000
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
    if (res.code !== 200) {
      ElMessage.error(res.msg || 'Error')
      return Promise.reject(new Error(res.msg || 'Error'))
    }
    return res.data
  },
  error => {
    ElMessage.error(error.response?.data?.msg || error.message)
    return Promise.reject(error)
  }
)

export default service
