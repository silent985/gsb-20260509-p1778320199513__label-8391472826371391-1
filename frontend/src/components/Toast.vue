<template>
  <transition name="toast">
    <div
      v-if="toastStore.visible"
      class="fixed top-6 left-1/2 -translate-x-1/2 z-[100] flex items-center gap-3 px-6 py-3 rounded-full shadow-xl backdrop-blur-md border transition-all duration-300 min-w-[320px] max-w-md"
      :class="toastClass"
    >
      <!-- 图标 -->
      <span class="text-xl shrink-0">
        <template v-if="toastStore.type === 'success'">✅</template>
        <template v-else-if="toastStore.type === 'error'">❌</template>
        <template v-else-if="toastStore.type === 'warning'">⚠️</template>
        <template v-else>ℹ️</template>
      </span>
      
      <!-- 消息 -->
      <span class="font-medium text-sm flex-1">{{ toastStore.message }}</span>
      
      <!-- 关闭按钮 -->
      <button
        @click="toastStore.hide()"
        class="p-1 rounded-full hover:bg-black/5 transition-colors shrink-0"
      >
        <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 opacity-60" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
    </div>
  </transition>
</template>

<script setup>
import { computed } from 'vue'
import { useToastStore } from '../stores/toast'

const toastStore = useToastStore()

const toastClass = computed(() => {
  const baseClasses = 'border-white/20'
  const classes = {
    success: 'bg-green-50/95 text-green-800 border-green-200 shadow-green-900/5',
    error: 'bg-red-50/95 text-red-800 border-red-200 shadow-red-900/5',
    warning: 'bg-amber-50/95 text-amber-800 border-amber-200 shadow-amber-900/5',
    info: 'bg-blue-50/95 text-blue-800 border-blue-200 shadow-blue-900/5'
  }
  return `${baseClasses} ${classes[toastStore.type] || classes.info}`
})
</script>
