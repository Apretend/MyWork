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
      </el-tab-pane>
      <el-tab-pane label="细纲">        
        <template #label>
          <span class="custom-tabs-label">
            <el-icon><SetUp /></el-icon>
            <span>细纲</span>
          </span>
        </template>
      </el-tab-pane>
      <el-tab-pane label="本书">        
        <template #label>
          <span class="custom-tabs-label">
            <el-icon><Notebook /></el-icon>
            <span>本书</span>
          </span>
        </template>
      </el-tab-pane>
      <el-tab-pane label="书库">        
        <template #label>
          <span class="custom-tabs-label">
            <el-icon><Collection /></el-icon>
            <span>书库</span>
          </span>
        </template>
      </el-tab-pane>
      <el-tab-pane label="创建新书">        
        <template #label>
          <span class="custom-tabs-label">
            <el-icon><plus /></el-icon>
            <span>创建新书</span>
          </span>
        </template>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { Plus, ArrowDown, Notebook, Collection, SetUp } from '@element-plus/icons-vue'


</script>

<style scoped lang="scss">
.document-management {
    display: flex;
    flex-direction: column;
    min-height: 700px;
    width: 500px;
    // border: 1px solid black;

    .document-tabs {
      width: 500px;
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
}

</style>