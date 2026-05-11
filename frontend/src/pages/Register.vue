<template>
  <div class="min-h-screen flex items-center justify-center p-4">
    <div class="w-full max-w-md">
      <!-- 注册卡片 -->
      <div class="bg-white/95 backdrop-blur-lg rounded-3xl shadow-2xl p-8 fade-in">
        <!-- Logo 和标题 -->
        <div class="text-center mb-8">
          <div class="w-16 h-16 bg-gradient-to-br from-purple-500 to-pink-600 rounded-2xl mx-auto mb-4 flex items-center justify-center shadow-lg">
            <span class="text-3xl">✨</span>
          </div>
          <h1 class="text-3xl font-bold bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent">
            创建账号
          </h1>
          <p class="text-gray-500 mt-2">加入 Staff Sync</p>
        </div>

        <!-- 注册表单 -->
        <form @submit.prevent="handleRegister" class="space-y-6">
          <!-- 用户名 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">用户名</label>
            <div class="relative">
              <span class="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400">👤</span>
              <input
                v-model="form.username"
                type="text"
                placeholder="3-20个字符"
                class="w-full pl-12 pr-4 py-3 border border-gray-200 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200 bg-gray-50/50"
                required
                minlength="3"
                maxlength="20"
              />
            </div>
          </div>

          <!-- 密码 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">密码</label>
            <div class="relative">
              <span class="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400">🔒</span>
              <input
                v-model="form.password"
                type="password"
                placeholder="6-20个字符"
                class="w-full pl-12 pr-4 py-3 border border-gray-200 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200 bg-gray-50/50"
                required
                minlength="6"
                maxlength="20"
              />
            </div>
          </div>

          <!-- 确认密码 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">确认密码</label>
            <div class="relative">
              <span class="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400">🔐</span>
              <input
                v-model="form.confirmPassword"
                type="password"
                placeholder="再次输入密码"
                class="w-full pl-12 pr-4 py-3 border border-gray-200 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200 bg-gray-50/50"
                required
              />
            </div>
          </div>

          <!-- 注册按钮 -->
          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 px-6 bg-gradient-to-r from-purple-500 to-pink-600 text-white font-semibold rounded-xl shadow-lg hover:shadow-xl hover:scale-[1.02] active:scale-[0.98] transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:scale-100"
          >
            <span v-if="loading" class="flex items-center justify-center gap-2">
              <span class="animate-spin">⏳</span>
              注册中...
            </span>
            <span v-else>注 册</span>
          </button>
        </form>

        <!-- 登录链接 -->
        <div class="mt-6 text-center">
          <span class="text-gray-500">已有账号？</span>
          <router-link
            to="/login"
            class="text-purple-600 font-medium hover:text-purple-700 hover:underline ml-1 transition-colors"
          >
            返回登录
          </router-link>
        </div>
      </div>

      <!-- 版权信息 -->
      <p class="text-center text-white/60 text-sm mt-6">
        © 2024 员工管理系统. All rights reserved.
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useToastStore } from '../stores/toast'
import { authApi } from '../api'

const router = useRouter()
const toastStore = useToastStore()

const loading = ref(false)
const form = reactive({
  username: '',
  password: '',
  confirmPassword: ''
})

const handleRegister = async () => {
  if (loading.value) return

  const username = form.username.trim()
  const password = form.password

  if (username.length < 3 || username.length > 20) {
    toastStore.error('用户名长度应在3-20之间')
    return
  }
  if (password.length < 6 || password.length > 20) {
    toastStore.error('密码长度应在6-20之间')
    return
  }

  if (password !== form.confirmPassword) {
    toastStore.error('两次密码输入不一致')
    return
  }

  loading.value = true
  try {
    const res = await authApi.register({
      username,
      password
    })
    if (res.code === 200) {
      toastStore.success('注册成功，请登录')
      router.push('/login')
    } else {
      toastStore.error(res.message)
    }
  } catch (error) {
    // 错误已在拦截器中处理
  } finally {
    loading.value = false
  }
}
</script>
