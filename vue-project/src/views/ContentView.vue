<template>
  <div class="content-view">
    <div class="content-head">
        <div class="content-title">{{ 暂存 }}</div>
        <div class="content-action">
            <el-button type="warning" plain>注销</el-button>
        </div>
    </div>
    <div class="content-body">
        <StoryManagementTools :updateDocumentSuccessed="updateDocumentSuccessed" class="content-story-management-tools" @itemClicked="handelItemClick"></StoryManagementTools>
        <StoryEditTools :itemId="selectedItemId" @updateDocumentSuccessed="handleRequestSuccessed" class="content-story-edit-tools"></StoryEditTools>
        <StoryDocumentManagement></StoryDocumentManagement>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, reactive, onMounted } from 'vue'
import StoryManagementTools from '../components/storyContent/StoryManagementTools.vue'
import StoryEditTools from '../components/storyContent/StoryEditTools.vue'
import StoryDocumentManagement from '../components/storyContent/StoryDocumentManagement.vue'

const selectedItemId = ref<number | null>(null)
const updateDocumentSuccessed = ref(0);

function handleRequestSuccessed() {
    updateDocumentSuccessed.value = updateDocumentSuccessed.value + 1;
}



const handelItemClick = (id: number) => {
    selectedItemId.value = id
}

</script>

<style scoped lang="scss">
.content-view {
    height: 100vh;
    width: 100vw;
    display: flex;
    flex-direction: column;
    background-color: #e0e0e0;

    .content-head {
        display: flex;
        flex-direction: row;
        height: 100px;
        // border: 1px solid red;
        .content-title {
            flex: 1;
        }
        .content-action {
            width: 300px;
            display: flex;
            flex-direction: row;
            border: 1px solid blue;
            justify-content: right;
            align-items: flex-end;
        }
    }
    .content-body {
        display: flex;
        flex-direction: row;

        .content-story-edit-tools {
            flex-grow: 1;
        }
    }
}
</style>