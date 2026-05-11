import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getUserInfo, loginRedirect, logout as logoutApi, type UserInfo } from '@/api/auth'

/**
 * 登录态由后端 cookie session 决定。前端只缓存 /me 返回的用户与角色。
 *  - login()      → 整页跳到 Idp 登录
 *  - fetchUser()  → 调 /me 同步用户与角色，401 时返回 false 让调用方决定是否跳转
 *  - logout()     → 调后端 /logout，清前端缓存
 */
export const useAuthStore = defineStore('auth', () => {
  const user = ref<UserInfo | null>(null)
  const loading = ref(false)

  const isAuthenticated = computed(() => user.value !== null)
  const roles = computed<string[]>(() => user.value?.roles ?? [])

  const fetchUser = async (): Promise<boolean> => {
    loading.value = true
    try {
      const info = await getUserInfo()
      user.value = info
      return true
    } catch {
      user.value = null
      return false
    } finally {
      loading.value = false
    }
  }

  /** 触发整页跳转到 Idp 登录 */
  const login = (redirectAfter?: string) => {
    loginRedirect(redirectAfter)
  }

  const logout = async () => {
    await logoutApi()
    user.value = null
    // 登出后回到登录页，让用户重新选择登录
    window.location.href = '/login'
  }

  const hasRole = (roleCode: string) => roles.value.includes(roleCode)

  return {
    user,
    loading,
    isAuthenticated,
    roles,
    login,
    fetchUser,
    logout,
    hasRole
  }
})
