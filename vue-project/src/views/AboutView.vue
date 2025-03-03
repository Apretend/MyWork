<template>
  <div class="about">
    <h1>This is an about page</h1>
    <div>{{ dataList }}</div>
    <div>{{ showTip }}</div>
  </div>
</template>
<script setup lang="ts">
  // import TheWelcome from '../components/TheWelcome.vue'
  import {ref} from 'vue'

  const dataList = ref("545614564568465132561");
  const showTip = ref(100)
</script>
<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
