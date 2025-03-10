<template>
  <div class="document-management">
    <el-tabs type="border-card" class="document-tabs">
      <el-tab-pane>
        <template #label>
          <span class="custom-tabs-label">
            <span>资源</span>
            <el-icon><ArrowDown /></el-icon>
          </span>
        </template>
        <div class="document-tabs-content">
        </div>
      </el-tab-pane>
      <el-tab-pane label="细纲">        
        <template #label>
          <span class="custom-tabs-label">
            <el-icon><SetUp /></el-icon>
            <span>细纲</span>
          </span>
        </template>
        <div class="document-tabs-content">
        </div>
      </el-tab-pane>
      <el-tab-pane label="本书">        
        <template #label>
          <span class="custom-tabs-label">
            <el-icon><Notebook /></el-icon>
            <span>本书</span>
          </span>
        </template>
        <div class="document-tabs-content">
        </div>
      </el-tab-pane>
      <el-tab-pane label="书库">        
        <template #label>
          <span class="custom-tabs-label">
            <el-icon><Collection /></el-icon>
            <span>书库</span>
          </span>
        </template>
        <div class="document-tabs-content">
        </div>
      </el-tab-pane>
      <el-tab-pane label="创建新书">        
        <template #label>
          <span class="custom-tabs-label">
            <el-icon><plus /></el-icon>
            <span>创建新书</span>
          </span>
        </template>
        <div class="document-tabs-content">
          <div class="createBook">
            <div class="createBookBox">
              <span style="margin-bottom: 4px">书名：</span>
              <div v-for="(title, index) in bookTitleList" :key="index">
                <el-input placeholder="书名" v-model="title.value" style="width: 70%;margin: 4px 0px 8px 0px;" show-word-limit maxlength="15"></el-input>
              </div>
              <div>
                <el-button 
                  type="text" 
                  class="add-title-btn"
                  @click="addBookTitle"
                  :disabled="bookTitleList.length >= 5"
                  >
                  <el-icon><plus /></el-icon>
                    添加书名
                </el-button>
              </div>
            </div>
            <div class="createBookBox">
              <span style="margin-bottom: 8px">简介：</span>
              <el-input 
                placeholder="简介" 
                v-model="bookIntro" 
                show-word-limit 
                type="textarea"
                maxlength="700"
                rows="5"></el-input>
            </div>
            <div class="createBookBox">
              <span style="margin-bottom: 8px">故事大纲：</span>
              <el-input 
                placeholder="故事大纲" 
                v-model="bookOutline" 
                show-word-limit 
                type="textarea"
                maxlength="2000"
                rows="5"></el-input>
            </div>
            <div class="createBookBox">
              <span style="margin-bottom: 8px">封面：</span>
              <el-upload action="#" list-type="picture-card" :auto-upload="false">
                <el-icon><Plus /></el-icon>
                
              </el-upload>
            </div>
            <div class="save-new-book-button" >
              <el-button type="primary" @click="createBook" >创建</el-button>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue';
import { Plus, ArrowDown, Notebook, Collection, SetUp } from '@element-plus/icons-vue'
const userId = ref(localStorage.getItem("userId"));
const props = defineProps<{
  bookId: number | null;
}>();
watch(() => props.bookId, (newId) => {
  // getDocumentContent(newId);
  console.log("StoryDocumentManagement：" + newId);
})


const bookName = ref("");
const bookTitleList = reactive([{
  value: "",
}]);
const bookIntro = ref("");
const bookOutline = ref("");
const addBookTitle = () => {
  bookTitleList.push({value: ""})
}
const createBook = () => {
  console.log(bookTitleList);
  
}

</script>

<style scoped lang="scss">
.document-management {
    display: flex;
    flex-direction: column;
    height: 800px;
    width: 500px;
    padding: 10px 25px;
    // border: 1px solid black;


    .document-tabs {
      // width: 500px;
      height: 100%;
      overflow-y: auto;
    }
    .document-tabs > .el-tabs__content {
      padding: 32px;
      color: #6b778c;
      font-size: 32px;
      font-weight: 600;
    }
    .document-tabs .custom-tabs-label .el-icon {
      vertical-align: middle;
    }
    .document-tabs .custom-tabs-label span {
      vertical-align: middle;
      margin-left: 4px;
    }

    .document-tabs-content {
      max-height: 710px;
      overflow: hidden;
      display: flex;
      flex-direction: column;
      .createBook {
        flex: 1;
        max-height: 100%;
        overflow-y: auto;
        display: flex;
        flex-direction: column;
        padding: 0px 12px 32px 4px;


        .createBookBox {
          display: flex;
          flex-direction: column;
          font-size: 18px;
          font-weight: 700;
          margin-bottom: 10px;
        }

        .save-new-book-button {
          margin-left: auto;
          margin-top: 10px;
        }

      }

    }

}

</style>