<template>
  <div class="login-page">
    <div class="login-card">
      <h2 class="login-title">军检测试平台登录</h2>
      <p class="login-subtitle">通过统一身份认证 (Idp) 登录</p>

      <el-button
        type="primary"
        class="login-button"
        :loading="submitting"
        @click="handleLogin"
      >
        前往 Idp 登录
      </el-button>

      <div class="login-hint">
        登录将跳转到统一身份认证服务，认证完成后自动返回本系统。
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '@/store/auth'

const route = useRoute()
const authStore = useAuthStore()

const submitting = ref(false)

const handleLogin = () => {
  submitting.value = true
  const redirect = (route.query.redirect as string) || '/'
  // 整页跳转，不需要 finally，下一次回到本页时组件会重建
  authStore.login(redirect)
}

// 进入 /login 时尝试静默拉一次 /me：如果 cookie 还有效就直接跳回业务页
onMounted(async () => {
  const ok = await authStore.fetchUser()
  if (ok) {
    const redirect = (route.query.redirect as string) || '/'
    window.location.replace(redirect)
  }
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
