
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Layout.vue'

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

router.beforeEach((to, _from) => {
  if (whiteList.includes(to.path)) {
    return true
  }
  const token = localStorage.getItem('access_token')
  if (!token) {
    return {
      path: '/login',
      query: { redirect: to.fullPath }
    }
  }
  return true
})

export default router

