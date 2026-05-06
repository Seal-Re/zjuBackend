
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { ElMessage } from 'element-plus'
import Layout from '@/layout/Layout.vue'
import { useAuthStore } from '@/store/auth'
import { APP_TITLE } from '@/constants/app'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'Login',
    meta: { title: '登录' },
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
    meta: { title: '测试设计' },
    children: [
      {
        path: 'module',
        name: 'ModuleLibrary',
        meta: { title: '模块库' },
        component: () => import('@/views/design/ModuleLibrary.vue')
      },
      {
        path: 'module/:funId',
        name: 'ModuleOrchestration',
        meta: { title: '模块编排' },
        component: () => import('@/views/design/ModuleOrchestration.vue')
      },
      {
        path: 'suite',
        name: 'SuiteLibrary',
        meta: { title: '清单库' },
        component: () => import('@/views/design/SuiteLibrary.vue')
      }
    ]
  },
  {
    path: '/review',
    component: Layout,
    redirect: '/review/list',
    meta: { title: '测试审签' },
    children: [
      {
        path: 'list',
        name: 'TestReview',
        meta: { title: '审签列表' },
        component: () => import('@/views/review/TestReview.vue')
      },
      {
        path: 'logs',
        name: 'SystemLogs',
        meta: { title: '系统日志' },
        component: () => import('@/views/review/SystemLogs.vue')
      }
    ]
  },
  {
    path: '/plan',
    component: Layout,
    redirect: '/plan/list',
    meta: { title: '测试计划' },
    children: [
      {
        path: 'list',
        name: 'TestPlan',
        meta: { title: '测试计划' },
        component: () => import('@/views/plan/TestPlan.vue')
      }
    ]
  },
  {
    path: '/device',
    component: Layout,
    redirect: '/device/list',
    meta: { title: '设备管理', roles: ['ADMIN'] },
    children: [
      {
        path: 'list',
        name: 'DeviceManage',
        meta: { title: '设备管理', roles: ['ADMIN'] },
        component: () => import('@/views/device/DeviceManage.vue')
      }
    ]
  },
  {
    path: '/command',
    component: Layout,
    redirect: '/command/dashboard',
    meta: { title: '测试指挥', roles: ['ADMIN', 'EXECUTOR'] },
    children: [
      {
        path: 'dashboard',
        name: 'CommandDashboard',
        meta: { title: '测试指挥台', roles: ['ADMIN', 'EXECUTOR'] },
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

router.beforeEach(async (to, _from) => {
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

  // RBAC 路由级守卫：meta.roles 声明该路径允许的角色集合
  const required = to.matched
    .map(r => (r.meta as any)?.roles as string[] | undefined)
    .filter(Boolean)
    .flat() as string[]

  if (required.length === 0) {
    return true // 无角色限制，登录即可
  }

  const authStore = useAuthStore()
  // 角色未加载（刷新后 store 内存丢失）时主动拉一次，避免错误拦截
  if (!authStore.roles || authStore.roles.length === 0) {
    try {
      await authStore.fetchUser()
    } catch {
      // ignore：fetchUser 已自带 401 处理
    }
  }

  const allowed = required.some(role => authStore.hasRole(role))
  if (!allowed) {
    ElMessage.warning('当前账号无权限访问该页面')
    return { path: '/' }
  }
  return true
})

router.afterEach((to) => {
  // 更新浏览器 tab 标题为「页面标题 - APP_TITLE」，统一品牌出口
  const pageTitle = (to.meta as any)?.title as string | undefined
  document.title = pageTitle ? `${pageTitle} - ${APP_TITLE}` : APP_TITLE
})

export default router

