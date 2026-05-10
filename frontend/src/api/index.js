import axios from 'axios'
import { useAuthStore } from '../stores/auth'
import { useToastStore } from '../stores/toast'
import router from '../router'

// 创建 axios 实例
const api = axios.create({
    baseURL: '/api',
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
})

// 请求拦截器
api.interceptors.request.use(
    (config) => {
        const authStore = useAuthStore()
        if (authStore.token) {
            config.headers.Authorization = `Bearer ${authStore.token}`
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// 响应拦截器
api.interceptors.response.use(
    (response) => {
        return response.data
    },
    (error) => {
        const toastStore = useToastStore()
        const authStore = useAuthStore()

        if (error.response) {
            const { status, data } = error.response

            if (status === 401) {
                authStore.logout()
                router.push('/login')
                toastStore.error('登录已过期，请重新登录')
            } else if (data && data.message) {
                toastStore.error(data.message)
            } else {
                toastStore.error('请求失败，请稍后重试')
            }
        } else {
            toastStore.error('网络错误，请检查网络连接')
        }

        return Promise.reject(error)
    }
)

// API 方法
export const authApi = {
    login: (data) => api.post('/auth/login', data),
    register: (data) => api.post('/auth/register', data)
}

export const employeeApi = {
    list: () => api.get('/employees'),
    getById: (id) => api.get(`/employees/${id}`),
    create: (data) => api.post('/employees', data),
    update: (id, data) => api.put(`/employees/${id}`, data),
    delete: (id) => api.delete(`/employees/${id}`)
}

export default api
