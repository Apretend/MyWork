<template>
  <div>
    <div class="story-edit-tools">
        <div class="story-edit-tools-left"></div>
        <div class="story-edit-tools-right"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import { ref, reactive, onMounted, watch } from "vue";
const props = defineProps<{
  itemId: number | null;
}>();

watch(() => props.itemId, (newId) => {
  console.log('StoryEditTools 接收到的新的 Item ID:', newId);
  getDocumentContent(newId)
  
})

onMounted(() => {
  

})

const getDocumentContent = async (itemId) => {
  try {
    const response = await axios.get(`/api/document/getDocumentById/${itemId}`);
    if (response.data.code === 0) {
      console.log(response.data.data);
    }
  } catch (error) {
    console.log("获取失败：", error);
  }
}





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