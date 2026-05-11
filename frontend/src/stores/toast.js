import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useToastStore = defineStore('toast', () => {
    const message = ref('')
    const type = ref('success') // success, error, warning, info
    const visible = ref(false)

    let timer = null

    function show(msg, msgType = 'success', duration = 3000) {
        message.value = msg
        type.value = msgType
        visible.value = true

        if (timer) {
            clearTimeout(timer)
        }

        timer = setTimeout(() => {
            hide()
        }, duration)
    }

    function hide() {
        visible.value = false
        message.value = ''
    }

    function success(msg, duration = 3000) {
        show(msg, 'success', duration)
    }

    function error(msg, duration = 3000) {
        show(msg, 'error', duration)
    }

    function warning(msg, duration = 3000) {
        show(msg, 'warning', duration)
    }

    function info(msg, duration = 3000) {
        show(msg, 'info', duration)
    }

    return {
        message,
        type,
        visible,
        show,
        hide,
        success,
        error,
        warning,
        info
    }
})
