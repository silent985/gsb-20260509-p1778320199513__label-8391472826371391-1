import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useAuthStore = defineStore('auth', () => {
    // 状态
    const token = ref(localStorage.getItem('token') || '')
    const username = ref(localStorage.getItem('username') || '')

    // 计算属性
    const isLoggedIn = computed(() => !!token.value)

    // 方法
    function setAuth(tokenValue, usernameValue) {
        token.value = tokenValue
        username.value = usernameValue
        localStorage.setItem('token', tokenValue)
        localStorage.setItem('username', usernameValue)
    }

    function logout() {
        token.value = ''
        username.value = ''
        localStorage.removeItem('token')
        localStorage.removeItem('username')
    }

    return {
        token,
        username,
        isLoggedIn,
        setAuth,
        logout
    }
})
