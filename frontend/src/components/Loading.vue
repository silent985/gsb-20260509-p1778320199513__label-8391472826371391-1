<template>
  <div
    v-if="visible"
    class="fixed inset-0 z-40 flex items-center justify-center bg-black/50 backdrop-blur-sm"
  >
    <div class="flex flex-col items-center gap-4">
      <!-- 加载动画 -->
      <div class="relative w-16 h-16">
        <div class="absolute inset-0 border-4 border-white/30 rounded-full"></div>
        <div class="absolute inset-0 border-4 border-transparent border-t-white rounded-full animate-spin"></div>
      </div>
      
      <!-- 加载文字 -->
      <span class="text-white font-medium text-lg">{{ message }}</span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  message: {
    type: String,
    default: '加载中...'
  }
})
</script>
