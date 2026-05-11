
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Layout.vue'
import { useAuthStore } from '@/store/auth'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/Login.vue')
  },
  {
    path: '/',
    redirect: '/design/module'
  },
  {
    path: '/design',
    component: Layout,
    redirect: '/design/module',
    children: [
      {
        path: 'module',
        name: 'ModuleLibrary',
        component: () => import('@/views/design/ModuleLibrary.vue')
      },
      {
        path: 'module/:funId',
        name: 'ModuleOrchestration',
        component: () => import('@/views/design/ModuleOrchestration.vue')
      },
      {
        path: 'suite',
        name: 'SuiteLibrary',
        component: () => import('@/views/design/SuiteLibrary.vue')
      }
    ]
  },
  {
    path: '/review',
    component: Layout,
    redirect: '/review/list',
    children: [
      {
        path: 'list',
        name: 'TestReview',
        component: () => import('@/views/review/TestReview.vue')
      },
      {
        path: 'logs',
        name: 'SystemLogs',
        component: () => import('@/views/review/SystemLogs.vue')
      }
    ]
  },
  {
    path: '/plan',
    component: Layout,
    redirect: '/plan/list',
    children: [
      {
        path: 'list',
        name: 'TestPlan',
        component: () => import('@/views/plan/TestPlan.vue')
      }
    ]
  },
  {
    path: '/device',
    component: Layout,
    redirect: '/device/list',
    children: [
      {
        path: 'list',
        name: 'DeviceManage',
        component: () => import('@/views/device/DeviceManage.vue')
      }
    ]
  },
  {
    path: '/command',
    component: Layout,
    redirect: '/command/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'CommandDashboard',
        component: () => import('@/views/command/CommandDashboard.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

const whiteList = ['/login']

router.beforeEach(async to => {
  if (whiteList.includes(to.path)) {
    return true
  }
  const authStore = useAuthStore()
  if (authStore.isAuthenticated) {
    return true
  }
  // 还没拿过 /me 就先拉一次：cookie 仍有效的情况下不打扰用户
  const ok = await authStore.fetchUser()
  if (ok) {
    return true
  }
  return {
    path: '/login',
    query: { redirect: to.fullPath }
  }
})

export default router
