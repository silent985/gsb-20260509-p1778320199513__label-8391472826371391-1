<template>
  <div id="app">
    <!-- Toast 消息 -->
    <Toast />
    
    <!-- 路由视图 -->
    <router-view v-slot="{ Component }">
      <transition name="page" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </div>
</template>

<script setup>
import Toast from './components/Toast.vue'
</script>

<style scoped>
#app {
  min-height: 100vh;
}
</style>
