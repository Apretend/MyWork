<template>
  <div>
    <div class="story-edit-tools">
      <div class="story-edit-tools-header">
        <div class="header-input">
          <div class="document-number">
            <span style="margin:0px 4px 0px 4px;">{{ documentInfo.documentName }}</span>
          </div>
          <div class="document-title">
            <el-input v-model="documentInfo.documentTitle" placeholder="章节名称"></el-input>
          </div>
          <div>
            <el-button type="primary" @click="save">保存</el-button>
          </div>
        </div>
      </div>
      <div class="story-edit-tools-body">
        <div class="story-deity-stage">
          <el-input
            v-model="documentInfo.documentContent"
            maxlength="4000"
            style="width: 100%;height:100%;"
            placeholder="正文内容"
            show-word-limit
            type="textarea"
            :rows="32"
          />
        </div>
      </div>
      <div class="story-edit-tools-footer">
        <div class="createTime">创建时间：{{ documentInfo.createTime }}</div>
        <div class="updateTime">更新时间：{{ documentInfo.updateTime }}</div>
        <div class="saveButton">
          <el-button size="small" type="primary" @click="save">保存</el-button>
        </div>
        <div></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import { ref, reactive, onMounted, watch } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';

const emit = defineEmits(['updateDocumentSuccessed'])

const props = defineProps<{
  itemId: number | null;
}>();

const userId = ref(localStorage.getItem("userId"));

const documentInfo = reactive({
  documentName: "", // 第几章或者文档标题
  documentTitle: "",   // 章节标题
  documentContent:"",
  createTime: "0000-00-00T00:00:00",
  updateTime: "0000-00-00T00:00:00"
})

watch(() => props.itemId, (newId) => {
  getDocumentContent(newId);
  
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

const save = async () => {
  try {
    const documentInfes = {
      documentId: props.itemId,
      documentTitle: documentInfo.documentTitle,
      documentContent: documentInfo.documentContent,
      documentName: documentInfo.documentName,
    }
    const response = await axios.post(`/api/fileManagementTools/updateFile`, documentInfes);
    if (response.data.code === 0) {
      ElMessage.success("保存成功");
      getDocumentContent(props.itemId);
      emit('updateDocumentSuccessed');
    }
  } catch (error) {
    console.log("保存失败：", error);
  }
}



</script>

<style scoped lang="scss">
.story-edit-tools {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 800px;
    overflow: hidden;
    padding: 10px 0px;

    .story-edit-tools-header {
      width: 100%;
      display: flex;
      flex-direction: row;
      background-color: white;
      align-items: center;
      font-size: 15px;
      font-weight: 700;
      border-radius: 8px 8px 0px 0px;

      .header-input {
        margin: 4px 32px;
        display: flex;
        flex-direction: row;
        width: auto;
        // white-space: normal;
        align-items: center;
        width: 100%;
        
      }
      .document-number {
        margin-right: 4px;
        white-space: nowrap;
        min-width: 10px;
        display: flex;
        flex-direction: row;
        flex-wrap: nowrap;
      }
      .document-title {
        flex: 1;
        margin-right: 8px;
      }

    }

    .story-edit-tools-body {
      width: 100%;
      padding: 10px 40px;
      text-align: center;
      font-size: 20px;
      font-weight: 700;
      background-color: #f2f2f2;

      .story-deity-stage {
        width: 100%;
        text-align: left;
      }

    }

    .story-edit-tools-footer {
      width: 100%;
      flex: 1;
      display: flex;
      flex-direction: row;
      font-size: 15px;
      font-weight: 700;
      padding: 4px 32px;
      border-bottom: 1px solid #f2f2f2;
      border-left: 1px solid #f2f2f2;
      border-right: 1px solid #f2f2f2;
      border-radius: 0px 0px 8px 8px;

      .createTime {
        margin-right: 16px;
      }

      .saveButton {
        margin-left: auto;
      }
    }

}

</style>