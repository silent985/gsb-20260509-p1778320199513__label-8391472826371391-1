<template>
  <div class="min-h-screen bg-gray-50 flex flex-col">
    <!-- 顶部导航栏 -->
    <header class="bg-white border-b border-gray-200 sticky top-0 z-10 backdrop-blur-sm bg-white/90">
      <div class="max-w-7xl mx-auto px-4 h-16 flex items-center justify-between">
        <!-- Logo区 -->
        <div class="flex items-center gap-3">
          <div class="bg-gradient-to-tr from-primary-600 to-primary-400 p-2 rounded-lg shadow-sm">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
          </div>
          <h1 class="text-lg font-bold text-gray-800 tracking-tight">员工管理系统</h1>
        </div>

        <!-- 用户信息 -->
        <div class="flex items-center gap-6">
          <div class="flex items-center gap-3">
            <div class="w-8 h-8 rounded-full bg-primary-100 text-primary-600 flex items-center justify-center text-sm font-bold border border-primary-200">
              {{ authStore.username?.charAt(0).toUpperCase() }}
            </div>
            <span class="text-sm font-medium text-gray-700">{{ authStore.username }}</span>
          </div>
          <button
            @click="handleLogout"
            class="text-sm font-medium text-gray-500 hover:text-red-600 transition-colors flex items-center gap-1.5"
            title="退出登录"
          >
            退出
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
            </svg>
          </button>
        </div>
      </div>
    </header>

    <!-- 主要内容区 -->
    <main class="flex-1 max-w-7xl mx-auto w-full px-4 py-8">
      <!-- 统计卡片 -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-all group">
          <div class="text-gray-500 text-sm font-medium mb-1 group-hover:text-primary-600 transition-colors">总员工数</div>
          <div class="text-3xl font-bold text-gray-900 tracking-tight">{{ employees.length }}</div>
        </div>
        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-all">
          <div class="text-gray-500 text-sm font-medium mb-1">男性员工</div>
          <div class="text-3xl font-bold text-blue-600 tracking-tight">
            {{ employees.filter(e => e.gender === '男').length }}
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 hover:shadow-md transition-all">
          <div class="text-gray-500 text-sm font-medium mb-1">女性员工</div>
          <div class="text-3xl font-bold text-pink-500 tracking-tight">
            {{ employees.filter(e => e.gender === '女').length }}
          </div>
        </div>
      </div>

      <!-- 操作栏 -->
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-xl font-bold text-gray-800 tracking-tight">
          员工列表
        </h2>
        <button
          @click="openAddModal"
          class="flex items-center gap-2 px-5 py-2.5 bg-primary-600 text-white rounded-xl hover:bg-primary-700 active:scale-95 transition-all shadow-md shadow-primary-200"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
          </svg>
          <span class="font-medium">新增员工</span>
        </button>
      </div>

      <!-- 加载中 -->
      <div v-if="loading" class="py-20 flex justify-center">
        <div class="animate-spin rounded-full h-10 w-10 border-4 border-primary-100 border-t-primary-600"></div>
      </div>

      <!-- 员工列表 -->
      <div v-else-if="employees.length > 0" class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <div class="overflow-x-auto">
          <table class="w-full">
            <thead class="bg-gray-50/50 border-b border-gray-100">
              <tr>
                <th class="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">ID</th>
                <th class="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">姓名</th>
                <th class="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">性别</th>
                <th class="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">手机号</th>
                <th class="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">创建时间</th>
                <th class="px-6 py-4 text-right text-xs font-semibold text-gray-500 uppercase tracking-wider">操作</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-50">
              <tr 
                v-for="employee in employees" 
                :key="employee.id" 
                class="hover:bg-gray-50/80 transition-colors group"
              >
                <td class="px-6 py-4 text-sm text-gray-400 font-mono">#{{ employee.id }}</td>
                <td class="px-6 py-4">
                  <div class="flex items-center gap-3">
                    <div class="w-8 h-8 rounded-full flex items-center justify-center text-xs font-bold"
                      :class="employee.gender === '男' ? 'bg-blue-50 text-blue-600' : 'bg-pink-50 text-pink-600'"
                    >
                      {{ employee.name.charAt(0) }}
                    </div>
                    <span class="font-medium text-gray-900">{{ employee.name }}</span>
                  </div>
                </td>
                <td class="px-6 py-4">
                  <span
                    class="inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-xs font-medium"
                    :class="employee.gender === '男' ? 'bg-blue-50 text-blue-700 border border-blue-100' : 'bg-pink-50 text-pink-700 border border-pink-100'"
                  >
                    <span class="w-1.5 h-1.5 rounded-full" :class="employee.gender === '男' ? 'bg-blue-400' : 'bg-pink-400'"></span>
                    {{ employee.gender }}
                  </span>
                </td>
                <td class="px-6 py-4 text-sm text-gray-600 font-mono">{{ employee.phone }}</td>
                <td class="px-6 py-4 text-sm text-gray-400">
                  {{ formatDate(employee.createdAt) }}
                </td>
                <td class="px-6 py-4 text-right">
                  <div class="flex items-center justify-end gap-2">
                    <button
                      @click="openEditModal(employee)"
                      class="p-2 text-gray-400 hover:text-primary-600 hover:bg-primary-50 rounded-lg transition-all"
                      title="编辑"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                      </svg>
                    </button>
                    <button
                      @click="handleDelete(employee)"
                      class="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-all"
                      title="删除"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                      </svg>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-else class="bg-white rounded-2xl shadow-sm border border-gray-100 p-16 text-center">
        <div class="w-16 h-16 bg-gray-50 rounded-2xl flex items-center justify-center mx-auto mb-6">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z" />
          </svg>
        </div>
        <h3 class="text-lg font-bold text-gray-900 mb-2">暂无员工数据</h3>
        <p class="text-gray-500 mb-8 max-w-sm mx-auto">您的员工列表是空的。点击下方按钮添加第一位团队成员，开始高效协作。</p>
        <button
          @click="openAddModal"
          class="px-6 py-2.5 bg-white border border-gray-200 text-gray-700 font-medium rounded-xl hover:bg-gray-50 hover:border-gray-300 transition-all shadow-sm flex items-center gap-2 mx-auto"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
          </svg>
          新增员工
        </button>
      </div>
    </main>

    <!-- 弹窗 -->
    <EmployeeForm
      v-if="showModal"
      :employee="editingEmployee"
      :saving="saving"
      @close="closeModal"
      @save="handleSave"
    />

    <!-- 确认删除弹窗 -->
    <ConfirmModal
      v-if="showConfirmModal"
      title="删除员工"
      :content="`确定要删除员工 ${deletingEmployee?.name} 吗？此操作无法撤销。`"
      @confirm="executeDelete"
      @cancel="cancelDelete"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { useToastStore } from '../stores/toast'
import api from '../api'
import EmployeeForm from '../components/EmployeeForm.vue'
import ConfirmModal from '../components/ConfirmModal.vue'

const router = useRouter()
const authStore = useAuthStore()
const toastStore = useToastStore()

const employees = ref([])
const loading = ref(false)
const showModal = ref(false)
const editingEmployee = ref(null)
const saving = ref(false)

// 删除确认相关状态
const showConfirmModal = ref(false)
const deletingEmployee = ref(null)

// 格式化日期
const formatDate = (isoString) => {
  if (!isoString) return '-'
  const date = new Date(isoString)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 获取员工列表
const fetchEmployees = async () => {
  loading.value = true
  try {
    const res = await api.get('/employees')
    if (res.code === 200) {
      employees.value = res.data || []
    } else {
      toastStore.error(res.message || '获取员工列表失败')
    }
  } catch (error) {
    console.error('Fetch failed:', error)
  } finally {
    loading.value = false
  }
}

// 退出登录
const handleLogout = () => {
  authStore.logout()
  toastStore.success('退出成功')
  router.push('/login')
}

// 弹窗操作
const openAddModal = () => {
  editingEmployee.value = null
  showModal.value = true
}

const openEditModal = (employee) => {
  editingEmployee.value = { ...employee } // 复制对象
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
  editingEmployee.value = null
}

// 保存逻辑
const handleSave = async (formData) => {
  console.log('Submitting:', formData)
  saving.value = true
  try {
    if (editingEmployee.value) {
      // 更新
      const res = await api.put(`/employees/${editingEmployee.value.id}`, formData)
      if (res.code === 200) {
        toastStore.success('更新成功')
      } else {
        toastStore.error(res.message || '更新失败')
        return
      }
    } else {
      // 新增
      const res = await api.post('/employees', formData)
      if (res.code === 200) {
        toastStore.success('新增成功')
      } else {
        toastStore.error(res.message || '新增失败')
        return
      }
    }
    closeModal()
    fetchEmployees()
  } catch (error) {
    console.error('Save failed:', error)
  } finally {
    saving.value = false
  }
}

// 删除逻辑 - 触发确认弹窗
const handleDelete = (employee) => {
  deletingEmployee.value = employee
  showConfirmModal.value = true
}

// 执行删除
const executeDelete = async () => {
  if (!deletingEmployee.value) return
  
  try {
    const res = await api.delete(`/employees/${deletingEmployee.value.id}`)
    if (res.code === 200) {
      toastStore.success('删除成功')
      fetchEmployees()
    } else {
      toastStore.error(res.message || '删除失败')
    }
  } catch (error) {
    console.error('Delete failed:', error)
  } finally {
    showConfirmModal.value = false
    deletingEmployee.value = null
  }
}

// 取消删除
const cancelDelete = () => {
  showConfirmModal.value = false
  deletingEmployee.value = null
}

onMounted(() => {
  fetchEmployees()
})
</script>
