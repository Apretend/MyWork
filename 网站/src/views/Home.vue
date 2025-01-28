<template>
  <div class="chat-interface">
    <left-sidebar />
    <main-content />
    <right-sidebar />
  </div>
</template>

<script>
import LeftSidebar from '@/components/LeftSidebar.vue'
import MainContent from '@/components/MainContent.vue'
import RightSidebar from '@/components/RightSidebar.vue'

export default {
  name: 'Home',
  components: {
    LeftSidebar,
    MainContent,
    RightSidebar
  }
}
</script>

<style lang="scss" scoped>
.chat-interface {
  display: flex;
  height: 100vh;
  background-color: white;
}
</style> 