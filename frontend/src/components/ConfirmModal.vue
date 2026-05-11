<template>
  <div class="modal-overlay" @click.self="$emit('cancel')">
    <div class="modal-content scale-in max-w-sm">
      <div class="p-6 text-center">
        <!-- Warning Icon -->
        <div class="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-red-100 mb-4">
          <svg class="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
          </svg>
        </div>
        
        <h3 class="text-lg font-medium text-gray-900 mb-2">
          {{ title }}
        </h3>
        
        <p class="text-sm text-gray-500 mb-6">
          {{ content }}
        </p>
        
        <div class="flex gap-3 justify-center">
          <button
            @click="$emit('cancel')"
            class="btn btn-secondary flex-1"
          >
            取消
          </button>
          <button
            @click="$emit('confirm')"
            class="btn bg-red-600 text-white hover:bg-red-700 flex-1"
          >
            确认删除
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    default: '确认操作'
  },
  content: {
    type: String,
    default: '您确定要执行此操作吗？此操作无法撤销。'
  }
})

defineEmits(['confirm', 'cancel'])
</script>
