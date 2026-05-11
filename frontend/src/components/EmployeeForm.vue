<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content scale-in">
      <!-- 标题栏 -->
      <div class="bg-primary-600 px-6 py-4">
        <h2 class="text-lg font-semibold text-white">
          {{ isEdit ? '编辑员工' : '新增员工' }}
        </h2>
      </div>

      <!-- 表单 -->
      <form @submit.prevent="handleSubmit" class="p-6 space-y-5">
        <!-- 姓名 -->
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1.5">
            姓名 <span class="text-red-500">*</span>
          </label>
          <input
            v-model.trim="form.name"
            type="text"
            placeholder="请输入员工姓名"
            class="input"
            maxlength="50"
          />
        </div>

        <!-- 手机号 -->
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1.5">
            手机号 <span class="text-red-500">*</span>
          </label>
          <input
            v-model.trim="form.phone"
            type="tel"
            placeholder="请输入 11 位手机号"
            class="input"
            maxlength="11"
          />
        </div>

        <!-- 性别 -->
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1.5">
            性别 <span class="text-red-500">*</span>
          </label>
          <div class="flex gap-3">
            <div
              @click="form.gender = '男'"
              class="gender-option"
              :class="{ 'male-selected': form.gender === '男' }"
            >
              <span>👨</span>
              <span>男</span>
            </div>
            <div
              @click="form.gender = '女'"
              class="gender-option"
              :class="{ 'female-selected': form.gender === '女' }"
            >
              <span>👩</span>
              <span>女</span>
            </div>
          </div>
        </div>

        <!-- 按钮 -->
        <div class="flex gap-3 pt-2">
          <button
            type="button"
            @click="$emit('close')"
            class="btn btn-secondary flex-1"
          >
            取消
          </button>
          <button
            type="submit"
            :disabled="saving"
            class="btn btn-primary flex-1"
          >
            <span v-if="saving" class="animate-spin">⏳</span>
            <span>{{ isEdit ? '保存修改' : '确认添加' }}</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, watch, nextTick } from 'vue'
import { useToastStore } from '../stores/toast'

const props = defineProps({
  employee: {
    type: Object,
    default: null
  },
  saving: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['close', 'save'])

const toastStore = useToastStore()

const isEdit = computed(() => !!props.employee)

const form = reactive({
  name: '',
  phone: '',
  gender: '男'
})

// 监听 employee 属性变化，使用 nextTick 确保 DOM 正确更新
watch(() => props.employee, async (newVal) => {
  if (newVal) {
    form.name = newVal.name || ''
    form.phone = newVal.phone || ''
    form.gender = newVal.gender || '男'
  } else {
    form.name = ''
    form.phone = ''
    form.gender = '男'
  }
  // 确保 DOM 更新后样式正确渲染
  await nextTick()
}, { immediate: true })

const handleSubmit = () => {
  // 防止重复提交
  if (props.saving) return
  
  // 验证姓名
  if (!form.name.trim()) {
    toastStore.warning('请输入员工姓名')
    return
  }

  // 验证手机号格式
  if (!/^1[3-9]\d{9}$/.test(form.phone)) {
    toastStore.warning('请输入正确的 11 位手机号')
    return
  }

  // 验证性别
  if (!form.gender) {
    toastStore.warning('请选择性别')
    return
  }

  // 提交数据 - 让父组件处理异步操作
  emit('save', {
    name: form.name.trim(),
    phone: form.phone.trim(),
    gender: form.gender
  })
}
</script>

