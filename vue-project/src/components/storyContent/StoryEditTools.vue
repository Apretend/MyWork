<template>
  <div>
    <div class="story-edit-tools">
      <div class="story-edit-tools-header">
        <div class="header-input">
          <div class="document-number">
            <span>第</span>
            <span>{{ documentInfo.documentName }}</span>
            <span>章：</span>
          </div>
          <el-input v-model="documentInfo.documentTitle" placeholder="章节名称"></el-input>
        </div>
      </div>
      <div class="story-edit-tools-body">
        <div class="story-deity-stage">
          {{ documentInfo.documentContent }}
        </div>
      </div>
      <div class="story-edit-tools-footer"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import { ref, reactive, onMounted, watch } from "vue";
const props = defineProps<{
  itemId: number | null;
}>();

const documentInfo = reactive({
  documentName: "___",
  documentTitle: ""
})
const pageNumber = ref("")

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
      Object.assign(documentInfo, response.data.data);
      documentInfo.documentTitle = documentInfo.documentTitle == null ? "" : documentInfo.documentTitle;
    }
  } catch (error) {
    console.log("获取失败：", error);
  }
}





</script>

<style scoped lang="scss">
.story-edit-tools {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 750px;
    overflow: hidden;
    border: 1px solid blue;
    padding: 10px 0px;

    .story-edit-tools-header {
      width: 100%;
      display: flex;
      flex-direction: row;
      background-color: white;
      align-items: center;
      font-size: 15px;
      font-weight: 700;
      white-space: normal;

      .header-input {
        margin: 8px 32px;
        display: flex;
        flex-direction: row;
        width: auto;
        white-space: normal;
        align-items: center;
        
      }
      .document-number {
        margin-right: 4px;
        white-space: nowrap;
        min-width: 10px;
        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
      }
    }

    .story-edit-tools-body {
      width: 100%;
      height: 600px;
      padding: 10px 40px;
      text-align: center;
      font-size: 20px;
      font-weight: 700;
      background-color: #f2f2f2;
      overflow-y: scroll;

      .story-deity-stage {
        width: 100%;
        text-align: left;
      }

    }
}

</style>