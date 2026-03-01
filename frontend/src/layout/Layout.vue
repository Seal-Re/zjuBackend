<template>
  <div class="layout-container">
    <aside class="sidebar">
      <div class="logo">
        <span class="logo-text">大飞机军检平台</span>
        <span class="logo-sub">数字化测试</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical"
        background-color="transparent"
        text-color="rgba(255,255,255,0.78)"
        active-text-color="#fff"
        router
      >
        <el-sub-menu index="/design">
          <template #title>
            <el-icon><Edit /></el-icon>
            <span>测试设计</span>
          </template>
          <el-menu-item index="/design/module">模块库</el-menu-item>
          <el-menu-item index="/design/suite">清单库</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/review">
          <template #title>
            <el-icon><Check /></el-icon>
            <span>测试审签</span>
          </template>
          <el-menu-item index="/review/list">审签列表</el-menu-item>
          <el-menu-item index="/review/logs">系统日志</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/plan/list">
          <template #title>
            <el-icon><Calendar /></el-icon>
            <span>测试计划</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/command/dashboard">
          <template #title>
            <el-icon><Monitor /></el-icon>
            <span>测试指挥</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/execution/run">
          <template #title>
            <el-icon><VideoPlay /></el-icon>
            <span>测试执行</span>
          </template>
        </el-menu-item>
      </el-menu>
    </aside>

    <div class="main-container">
      <header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-for="(matched, index) in route.matched" :key="index">
              {{ matched.name || '当前' }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <span class="current-time">{{ currentTime }}</span>
          <div class="user-info">
            <el-avatar :size="28" class="user-avatar">管</el-avatar>
            <span class="username">管理员</span>
          </div>
        </div>
      </header>

      <main class="page-content">
        <div class="content-card">
          <router-view />
        </div>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import {
  Edit,
  Check,
  Calendar,
  Monitor,
  VideoPlay
} from '@element-plus/icons-vue'

const route = useRoute()
const activeMenu = computed(() => route.path)

const currentTime = ref('')
let timer: any = null

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', { hour12: false })
}

onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 1000)
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
})
</script>

<style scoped lang="scss">
.layout-container {
  display: flex;
  height: 100vh;
  width: 100%;
}

.sidebar {
  width: 220px;
  background: linear-gradient(180deg, #0d1b2a 0%, #1b4962 100%);
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 12px rgba(0, 0, 0, 0.15);

  .logo {
    height: 64px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 0 12px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);

    .logo-text {
      font-size: 16px;
      font-weight: 600;
      color: #fff;
      letter-spacing: 0.5px;
    }
    .logo-sub {
      font-size: 11px;
      color: rgba(255, 255, 255, 0.65);
      margin-top: 2px;
    }
  }

  .el-menu-vertical {
    border-right: none;
    padding: 8px 0;

    :deep(.el-menu-item),
    :deep(.el-sub-menu__title) {
      margin: 2px 8px;
      border-radius: 6px;
      height: 44px;
      line-height: 44px;
    }
    :deep(.el-menu-item.is-active) {
      background: rgba(46, 196, 182, 0.25) !important;
      color: #2ec4b6 !important;
    }
    :deep(.el-menu-item:hover) {
      background: rgba(255, 255, 255, 0.08);
      color: #fff !important;
    }
    :deep(.el-sub-menu__title:hover) {
      background: rgba(255, 255, 255, 0.08);
      color: #fff !important;
    }
    :deep(.el-sub-menu .el-menu-item) {
      min-width: auto;
      padding-left: 48px !important;
    }
  }
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: var(--app-page-bg);
  overflow: hidden;
}

.header {
  height: 52px;
  background: var(--app-card-bg);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  box-shadow: var(--app-shadow);

  .header-left {
    :deep(.el-breadcrumb__inner) {
      color: var(--app-text-secondary);
      font-weight: 400;
    }
    :deep(.el-breadcrumb__item:last-child .el-breadcrumb__inner) {
      color: var(--app-text);
    }
  }

  .header-right {
    display: flex;
    align-items: center;
    gap: 16px;
    font-size: 13px;
    color: var(--app-text-secondary);

    .current-time {
      font-variant-numeric: tabular-nums;
    }
    .user-info {
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 4px 10px;
      border-radius: 20px;
      background: var(--app-page-bg);
      cursor: pointer;

      .user-avatar {
        background: var(--app-accent);
        color: #fff;
        font-size: 12px;
      }
      .username {
        color: var(--app-text);
        font-weight: 500;
      }
    }
  }
}

.page-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;

  .content-card {
    background: var(--app-card-bg);
    border-radius: var(--app-radius);
    padding: 24px;
    min-height: calc(100vh - 52px - 40px - 48px);
    box-shadow: var(--app-shadow-card);
  }
}
</style>
