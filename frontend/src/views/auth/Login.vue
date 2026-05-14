<template>
  <div class="login-page">
    <div class="login-card">
      <p class="login-subtitle">正在跳转到统一身份认证...</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '@/store/auth'

const route = useRoute()
const authStore = useAuthStore()

onMounted(async () => {
  const redirect = (route.query.redirect as string) || '/'
  const ok = await authStore.fetchUser()
  if (ok) {
    window.location.replace(redirect)
    return
  }
  authStore.login(redirect)
})
</script>

<style scoped lang="scss">
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: radial-gradient(circle at top left, #1b4962 0, #0d1b2a 45%, #000814 100%);
}

.login-card {
  width: 360px;
  padding: 32px 32px 24px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.98);
  box-shadow: 0 18px 45px rgba(15, 23, 42, 0.45);
  text-align: center;
}

.login-title {
  margin: 0 0 4px;
  font-size: 20px;
  font-weight: 600;
  color: #0f172a;
}

.login-subtitle {
  margin: 0 0 20px;
  font-size: 13px;
  color: #64748b;
}

.login-button {
  width: 100%;
  margin-bottom: 16px;
}

.login-hint {
  font-size: 12px;
  color: #94a3b8;
  line-height: 1.6;
}
</style>
