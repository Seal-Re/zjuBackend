<template>
  <div class="login-page">
    <div class="login-card">
      <h2 class="login-title">{{ APP_TITLE }} · 登录</h2>
      <p class="login-subtitle">请使用分配的账号登录系统</p>

      <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            autocomplete="username"
          />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="form.password"
            placeholder="请输入密码"
            type="password"
            show-password
            autocomplete="current-password"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            class="login-button"
            :loading="submitting"
            @click="handleSubmit"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>

      <div v-if="isDev" class="login-hint">
        测试环境可使用：
        <span class="code">admin / 123456</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, type FormInstance } from 'element-plus'
import { useAuthStore } from '@/store/auth'
import { APP_TITLE } from '@/constants/app'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const formRef = ref<FormInstance>()
const submitting = ref(false)
const isDev = import.meta.env.DEV

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleSubmit = () => {
  if (!formRef.value) return
  formRef.value.validate(async (valid) => {
    if (!valid) return
    submitting.value = true
    try {
      await authStore.login(form.username, form.password)
      ElMessage.success('登录成功')
      const redirect = (route.query.redirect as string) || '/design/module'
      router.replace(redirect)
    } catch (e) {
      // 错误信息由拦截器弹出
    } finally {
      submitting.value = false
    }
  })
}
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
}

.login-title {
  margin: 0 0 4px;
  font-size: 20px;
  font-weight: 600;
  color: #0f172a;
  text-align: center;
}

.login-subtitle {
  margin: 0 0 20px;
  font-size: 13px;
  color: #64748b;
  text-align: center;
}

.login-button {
  width: 100%;
}

.login-hint {
  margin-top: 8px;
  font-size: 12px;
  color: #94a3b8;
  text-align: center;

  .code {
    font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace;
    color: #0f172a;
    background: #e2e8f0;
    padding: 2px 6px;
    border-radius: 4px;
    margin-left: 4px;
  }
}
</style>

