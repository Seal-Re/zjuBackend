import request from './request'

/**
 * 与 Idp（Spring Authorization Server）对接：
 *   - 登录：浏览器整页跳转到后端 /oauth2/authorization/idp，由后端代理 OAuth2 dance
 *   - 用户信息：GET /me（基于 cookie session）
 *   - 退出：POST /logout，后端会清 session 并返回 204
 *
 * dev 环境通过 Vite 代理把 /api/* 转到 http://localhost:10001/fastop。
 */

const API_BASE = (import.meta.env.VITE_API_BASE as string) || '/api'
// OAuth2 流程必须在同一 origin 完成（避免跨 5173↔10001 的 session cookie 丢失），
// 所以登录跳转直接到 fastop 后端，不走 Vite 代理。
const OAUTH2_ORIGIN = (import.meta.env.VITE_OAUTH2_ORIGIN as string) || 'http://localhost:10001/fastop'

export interface UserInfo {
  username: string
  sub?: string
  name?: string
  email?: string
  roles: string[]
}

/** 整页跳转到后端发起 OAuth2 授权码流程；登录后会被重定向回前端首页 */
export function loginRedirect(redirectAfter?: string) {
  const target = redirectAfter
    ? `${OAUTH2_ORIGIN}/oauth2/authorization/idp?redirect=${encodeURIComponent(redirectAfter)}`
    : `${OAUTH2_ORIGIN}/oauth2/authorization/idp`
  window.location.href = target
}

/** 拉取当前登录用户。未登录时后端会返回 401，调用方自行处理 */
export function getUserInfo() {
  return request.get<any, UserInfo>('/me')
}

/** 退出登录：清后端 session 后整页回到前端登录页 */
export async function logout() {
  try {
    await request.post('/logout')
  } catch {
    // 即使 logout 报错也继续清前端状态
  }
}
