import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },
  server: {
    fs: {
      // Allow serving files from the real path when Vite is started via a junction (C:\fp)
      allow: ['C:/fp', 'D:/IntelliJIDEAprogram/SpringBootProgram/idp/fastop-docker-master/fastop-docker-master-b0ce8e9177bb719eedeef92a9ea785a8159f9300/frontend']
    },
    proxy: {
      // 业务 API + OAuth2 回调（/api/login/oauth2/code/idp 也走这条规则）
      '/api': {
        target: 'http://localhost:10001/fastop',
        changeOrigin: true,
        rewrite: (p) => p.replace(/^\/api/, ''),
        // 后端 JSESSIONID 的 Path=/fastop 不匹配前端 origin 下任何路径，
        // 这里改成 /，让浏览器在所有 /api/* 调用上都回送 cookie
        cookiePathRewrite: { '/fastop': '/', '*': '/' },
        cookieDomainRewrite: 'localhost'
      }
    }
  }
})
