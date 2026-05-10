import axios from 'axios'
import { ElMessage } from 'element-plus'

/**
 * 统一 axios 实例：
 *  - withCredentials 让浏览器自动带后端 JSESSIONID
 *  - 后端响应统一封装 { code, data, message }
 *  - 401 时不在拦截器里弹 toast（避免每次未登录都吵），交给路由守卫处理
 */
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '/api',
  timeout: 10000,
  withCredentials: true,
  xsrfCookieName: 'XSRF-TOKEN',
  xsrfHeaderName: 'X-XSRF-TOKEN'
})

service.interceptors.response.use(
  response => {
    const res = response.data
    // 后端非业务接口（如 OIDC userinfo）可能不返回标准信封，直接透传
    if (typeof res !== 'object' || res === null || !('code' in res)) {
      return res
    }
    if (res.code !== 200) {
      ElMessage.error(res.msg || res.message || 'Error')
      return Promise.reject(new Error(res.msg || res.message || 'Error'))
    }
    return res.data
  },
  error => {
    const status = error.response?.status
    if (status === 401) {
      // 静默：路由守卫会触发跳转 Idp
      return Promise.reject(error)
    }
    ElMessage.error(
      error.response?.data?.msg ||
        error.response?.data?.message ||
        error.message
    )
    return Promise.reject(error)
  }
)

export default service
