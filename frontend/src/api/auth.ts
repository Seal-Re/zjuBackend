import authRequest from './authRequest'

export interface TokenResponse {
  access_token: string
  refresh_token?: string
  token_type: string
  expires_in: number
  scope?: string
}

export function getToken(params: { username: string; password: string }) {
  const form = new URLSearchParams()
  form.set('grant_type', 'password')
  form.set('username', params.username)
  form.set('password', params.password)
  return authRequest.post<any, TokenResponse>('/oauth/token', form, {
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
  }).then(data => {
    if (data && (data as TokenResponse).access_token) {
      localStorage.setItem('access_token', (data as TokenResponse).access_token)
      if ((data as TokenResponse).refresh_token) {
        localStorage.setItem('refresh_token', (data as TokenResponse).refresh_token!)
      }
    }
    return data as TokenResponse
  })
}

export function refreshToken(refreshTokenValue: string) {
  const form = new URLSearchParams()
  form.set('grant_type', 'refresh_token')
  form.set('refresh_token', refreshTokenValue)
  return authRequest.post<any, TokenResponse>('/oauth/token', form, {
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
  }).then(data => {
    if (data && (data as TokenResponse).access_token) {
      localStorage.setItem('access_token', (data as TokenResponse).access_token)
    }
    return data as TokenResponse
  })
}

export function checkToken(token?: string) {
  const t = token || localStorage.getItem('access_token')
  return authRequest.post<any, { active: boolean; user_name?: string; user_id?: string; exp?: number }>(
    '/oauth/check_token',
    t ? new URLSearchParams({ token: t }) : undefined,
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
  )
}

export function revokeToken(token?: string) {
  const t = token || localStorage.getItem('access_token')
  if (t) {
    return authRequest.post('/oauth/revoke', new URLSearchParams({ token: t }), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    }).finally(() => {
      localStorage.removeItem('access_token')
      localStorage.removeItem('refresh_token')
    })
  }
  return Promise.resolve()
}

export interface UserInfo {
  id: string
  username: string
  name?: string
  email?: string
  enabled?: boolean
  sub?: string
}

export function getUserInfo() {
  return authRequest.get<any, UserInfo>('/userinfo')
}
