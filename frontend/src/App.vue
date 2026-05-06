<template>
  <router-view />
</template>

<script setup lang="ts">
</script>

<style>
/* 数字化军检测试平台 - 全局变量与基础样式 */
:root {
  --app-sidebar-bg: #0d1b2a;
  --app-sidebar-active: #1b4962;
  --app-accent: #2ec4b6;
  --app-accent-hover: #20a89a;
  --app-card-bg: #ffffff;
  --app-page-bg: #f0f4f8;
  --app-border: #e2e8f0;
  --app-text: #1e293b;
  --app-text-secondary: #64748b;
  --app-radius: 8px;
  --app-shadow: 0 1px 3px rgba(0, 21, 41, 0.08);
  --app-shadow-card: 0 2px 12px rgba(0, 21, 41, 0.06);
}

* {
  box-sizing: border-box;
}

body {
  margin: 0;
  padding: 0;
  font-family: 'PingFang SC', 'Microsoft YaHei', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  font-size: 14px;
  color: var(--app-text);
  background: var(--app-page-bg);
  -webkit-font-smoothing: antialiased;
}

#app {
  min-height: 100vh;
}

/* 统一页面标题 */
.page-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--app-text);
  margin: 0 0 16px 0;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--app-border);
}

/* 统一筛选栏 */
.filter-bar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
  padding: 12px 0;
}

/* 统一表格容器 */
.table-wrap {
  border-radius: var(--app-radius);
  overflow: hidden;
  box-shadow: var(--app-shadow-card);
}
</style>
