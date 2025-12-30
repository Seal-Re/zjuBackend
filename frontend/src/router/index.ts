
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Layout.vue'

const routes: Array<RouteRecordRaw> = [
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

export default router
