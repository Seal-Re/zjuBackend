<template>
  <div class="layout-container">
    <!-- Sidebar -->
    <aside class="sidebar">
      <div class="logo">
        <span>数字化军检测试平台</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical"
        background-color="#001529"
        text-color="#a6adb4"
        active-text-color="#ffffff"
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

    <!-- Main Content -->
    <div class="main-container">
      <!-- Header -->
      <header class="header">
        <div class="header-left">
          <el-icon class="collapse-btn"><Fold /></el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-for="(matched, index) in route.matched" :key="index" :to="matched.path">
              {{ matched.name }} <!-- Simplify for now, ideally use meta title -->
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <div class="user-info">
            <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
            <span class="username">Admin</span>
          </div>
          <span class="current-time">{{ currentTime }}</span>
        </div>
      </header>

      <!-- Page Content -->
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
  VideoPlay,
  Fold
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
  background-color: #001529;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;

  .logo {
    height: 64px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgba(255, 255, 255, 0.1);
    color: white;
    font-size: 18px;
    font-weight: bold;
  }

  .el-menu-vertical {
    border-right: none;

    :deep(.el-menu-item.is-active) {
      background-color: #1890ff !important;
      color: white !important;
    }

    :deep(.el-menu-item:hover) {
      color: white !important;
    }

    :deep(.el-sub-menu__title:hover) {
       color: white !important;
    }
  }
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #f0f2f5;
  overflow: hidden;
}

.header {
  height: 50px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .header-left {
    display: flex;
    align-items: center;
    gap: 20px;

    .collapse-btn {
      font-size: 20px;
      cursor: pointer;
    }
  }

  .header-right {
    display: flex;
    align-items: center;
    gap: 20px;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.65);

    .user-info {
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;

      .username {
        color: rgba(0, 0, 0, 0.85);
      }
    }
  }
}

.page-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;

  .content-card {
    background: white;
    border-radius: 4px;
    padding: 20px;
    min-height: calc(100vh - 50px - 40px - 40px); /* Approx calculation */
  }
}
</style>
