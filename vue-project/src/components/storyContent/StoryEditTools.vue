<template>
  <div>
    {{ itemId }}
    <div class="story-edit-tools">
        <div class="story-edit-tools-left"></div>
        <div class="story-edit-tools-right"></div>
    </div>
  </div>
</template>

<script setup lang="ts">

const props = defineProps<{
  itemId: number | null;
}>();

</script>

<style scoped lang="scss">
.story-edit-tools {
    display: flex;
    flex-direction: row;
    width: 100%;
    min-height: 700px;
    border: 1px solid blue;
}

</style>