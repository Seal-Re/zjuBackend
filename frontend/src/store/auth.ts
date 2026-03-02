import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserInfo } from '@/api/auth'
import { getUserInfo, getToken, revokeToken } from '@/api/auth'
import { getUserRoles, type RoleDto } from '@/api/rbac'

export const useAuthStore = defineStore('auth', () => {
  const user = ref<UserInfo | null>(null)
  const loading = ref(false)
  const isAuthenticated = ref(!!localStorage.getItem('access_token'))
  const roles = ref<string[]>([])

  const loadRoles = async (userId?: string) => {
    if (!userId) return
    try {
      const list: RoleDto[] = await getUserRoles(userId)
      roles.value = Array.isArray(list) ? list.map(r => r.code) : []
    } catch {
      roles.value = []
    }
  }

  const login = async (username: string, password: string) => {
    loading.value = true
    try {
      await getToken({ username, password })
      isAuthenticated.value = true
      const info = await getUserInfo()
      user.value = info
      await loadRoles(info.id)
    } finally {
      loading.value = false
    }
  }

  const fetchUser = async () => {
    if (!localStorage.getItem('access_token')) return
    try {
      const info = await getUserInfo()
      user.value = info
      isAuthenticated.value = true
      await loadRoles(info.id)
    } catch {
      // ignore, token 可能失效，交由路由守卫处理
    }
  }

  const logout = async () => {
    try {
      await revokeToken()
    } finally {
      user.value = null
      isAuthenticated.value = false
      roles.value = []
    }
  }

  const hasRole = (roleCode: string) => {
    return roles.value.includes(roleCode)
  }

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


