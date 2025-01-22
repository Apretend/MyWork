<template>
  <div class="content-page">
    <!-- 顶部导航 -->
    <header class="header">
      <h1>小说创作系统</h1>
      <button class="logout-btn" @click="handleLogout">退出登录</button>
    </header>

    <div class="main-container">
      <!-- 左侧面板 -->
      <div class="left-panel">
        <!-- 文档管理区域 -->
        <div class="document-section">
          <!-- 搜索框 -->
          <div class="search-box">
            <el-input
              v-model="searchQuery"
              placeholder="搜索小说名称"
              prefix-icon="el-icon-search"
              @input="handleSearch"
            />
          </div>

          <!-- 搜索结果浮窗 -->
          <div v-if="showSearchResults" class="search-results">
            <div
              v-for="result in searchResults"
              :key="result.id"
              class="search-result-item"
              @click="selectNovel(result)"
            >
              {{ result.title }}
            </div>
          </div>

          <!-- 小说标题区域 -->
          <div class="novel-header" v-if="currentNovel">
            <div class="novel-title">
              <span v-if="!isEditingTitle" class="title-text" @click="startEditTitle">
                {{ currentNovelTitle }}
              </span>
              <el-input
                v-else
                v-model="editingTitle"
                ref="titleInput"
                size="small"
                @blur="saveTitle"
                @keyup.enter.native="saveTitle"
              ></el-input>
              <div class="title-actions">
                <i class="el-icon-edit" @click="startEditTitle" title="重命名"></i>
                <i class="el-icon-plus" @click="handleAddDirectory" title="新建目录"></i>
              </div>
            </div>
          </div>

          <!-- 文件目录树 -->
          <div class="file-tree">
            <el-tree
              :data="fileTreeData"
              node-key="id"
              :props="defaultProps"
              @node-click="handleNodeClick"
            >
              <span class="custom-tree-node" slot-scope="{ node, data }">
                <div class="node-wrapper">
                  <span class="node-content">
                    <i :class="[
                      data.type === 'directory' ? 'el-icon-folder' : 'el-icon-document',
                      'node-icon'
                    ]"></i>
                    <span v-if="editingNode !== node" class="node-label">
                      {{ data.label }}
                    </span>
                    <el-input
                      v-else
                      v-model="editingLabel"
                      ref="nodeInput"
                      size="small"
                      @blur="saveEdit"
                      @keyup.enter.native="saveEdit"
                    ></el-input>
                  </span>
                  <span class="node-actions">
                    <i class="el-icon-edit" @click.stop="startEdit(node)" title="重命名"></i>
                    <i class="el-icon-delete" @click.stop="handleDelete(node, data)"></i>
                    <i v-if="data.type === 'directory'" class="el-icon-plus" @click.stop="handleAdd(node, data)" title="新建文档"></i>
                  </span>
                </div>
              </span>
            </el-tree>
          </div>
        </div>

        <!-- 条目管理区域 -->
        <div class="items-section">
          <!-- 按钮网格 -->
          <div class="item-buttons" v-show="!showItemFormType">
            <el-button 
              v-for="item in itemTypes" 
              :key="item.type"
              type="primary"
              size="small"
              plain
              @click="showItemForm(item.type)"
            >
              {{ item.label }}
            </el-button>
          </div>

          <!-- 条目表单 -->
          <div v-show="showItemFormType" class="item-form">
            <h3 class="form-title">{{ formTitle }}</h3>
            <el-form :model="itemForm" label-width="60px">
              <el-form-item label="名称">
                <el-input v-model="itemForm.name" placeholder="请输入名称"></el-input>
              </el-form-item>
              <el-form-item label="性别">
                <el-input v-model="itemForm.gender" placeholder="请输入性别"></el-input>
              </el-form-item>
              <el-form-item label="年龄">
                <el-input-number 
                  v-model="itemForm.age" 
                  :min="0"
                  controls-position="right"
                ></el-input-number>
              </el-form-item>
              <el-form-item label="性格">
                <el-input v-model="itemForm.personality" placeholder="请输入性格特点"></el-input>
              </el-form-item>
              <el-form-item label="描述">
                <el-input
                  type="textarea"
                  v-model="itemForm.description"
                  :rows="2"
                  placeholder="请输入描述"
                ></el-input>
              </el-form-item>
              <el-form-item label="数量">
                <el-input-number 
                  v-model="itemForm.quantity" 
                  :min="0"
                  controls-position="right"
                ></el-input-number>
              </el-form-item>
              <el-form-item class="form-buttons">
                <el-button @click="cancelEdit">取消</el-button>
                <el-button type="primary" @click="saveItem">保存</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>

      <!-- 中间编辑区域 -->
      <div class="center-panel">
        <div class="editor-container">
          <!-- 章节标题输入区域 -->
          <div class="chapter-title">
            <span class="chapter-prefix">第</span>
            <el-input
              v-model="chapterNumber"
              class="chapter-number"
              size="small"
            ></el-input>
            <span class="chapter-middle">章</span>
            <el-input
              v-model="chapterTitle"
              class="chapter-name"
              size="small"
              placeholder="请输入章节名称"
            ></el-input>
          </div>

          <!-- 内容编辑区域 -->
          <div class="editor-content">
            <div class="content-wrapper">
              <el-input
                type="textarea"
                v-model="content"
                :autosize="{ minRows: 30 }"
                placeholder="开始创作..."
                class="content-textarea"
                @input="handleContentChange"
              ></el-input>
              
              <!-- 修改统计信息显示位置 -->
              <div class="content-stats">
                <div class="stat-item">
                  <span class="stat-label">当前字数：</span>
                  <span class="stat-value">{{ wordCount }}</span>
                </div>
                <div class="stat-item" v-if="startTime">
                  <span class="stat-label">开始时间：</span>
                  <span class="stat-value">{{ formatTime(startTime) }}</span>
                </div>
                <div class="stat-item" v-if="endTime">
                  <span class="stat-label">结束时间：</span>
                  <span class="stat-value">{{ formatTime(endTime) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧面板 -->
      <div class="right-panel">
        <!-- 选项卡区域 -->
        <div class="tabs">
          <div 
            class="tab"
            :class="{ 'active': activeTab === 'resource' }"
            @click="switchTab('resource')"
          >
            资源
          </div>
          <div 
            class="tab"
            :class="{ 'active': activeTab === 'outline' }"
            @click="switchTab('outline')"
          >
            细纲
          </div>
        </div>

        <!-- 资源列表区域 -->
        <div v-show="activeTab === 'resource'" class="tab-content">
          <div class="resource-list">
            <div 
              v-for="item in itemTypes" 
              :key="item.type"
              class="resource-section"
            >
              <!-- 资源标题横条 -->
              <div 
                class="resource-header"
                :class="{ 'expanded': expandedType === item.type }"
                @click="toggleExpand(item.type)"
              >
                <span class="resource-title">{{ item.label }}</span>
                <i class="el-icon-arrow-down" :class="{ 'expanded': expandedType === item.type }"></i>
              </div>

              <!-- 展开的内容区域 -->
              <div 
                v-show="expandedType === item.type"
                class="resource-content"
              >
                <!-- 搜索框 -->
                <div class="resource-search">
                  <el-input
                    v-model="searchKeywords[item.type]"
                    placeholder="搜索..."
                    prefix-icon="el-icon-search"
                    clearable
                    @input="handleResourceSearch"
                  ></el-input>
                </div>

                <!-- 资源列表 -->
                <div class="resource-list-content">
                  <div 
                    v-for="(resource, index) in getFilteredResources(item.type)" 
                    :key="index"
                    class="resource-item"
                  >
                    <span class="resource-name">{{ resource.name }}</span>
                    <div class="resource-info">
                      <span v-if="resource.gender">性别：{{ resource.gender }}</span>
                      <span v-if="resource.age">年龄：{{ resource.age }}</span>
                      <span v-if="resource.quantity">数量：{{ resource.quantity }}</span>
                    </div>
                    <div class="resource-desc" v-if="resource.description">
                      {{ resource.description }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 细纲编辑区域 -->
        <div v-show="activeTab === 'outline'" class="tab-content">
          <el-input
            type="textarea"
            v-model="outlineContent"
            :rows="25"
            placeholder="请输入细纲内容..."
          ></el-input>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Content',
  data() {
    return {
      searchQuery: '',
      showSearchResults: false,
      searchResults: [
        { id: 1, title: '稿费起飞从搭建工具开始' },
        { id: 2, title: '我在修仙界开网店' },
        { id: 3, title: '修真界的程序员' },
        { id: 4, title: '重生之修仙大佬' }
      ],
      currentNovel: {
        id: 1,
        title: '稿费起飞从搭建工具开始'
      },
      fileTreeData: [
        {
          id: 1,
          label: '第一卷 天道初现',
          type: 'directory',
          children: [
            { id: 3, label: '第一章 灵气复苏.txt', type: 'file' },
            { id: 4, label: '第二章 觉醒天赋.txt', type: 'file' },
            { id: 5, label: '第三章 踏上修仙路.txt', type: 'file' },
            { id: 6, label: '第四章 初入门派.txt', type: 'file' },
            { id: 7, label: '第五章 修炼基础.txt', type: 'file' },
            { id: 8, label: '第六章 同门切磋.txt', type: 'file' }
          ]
        },
        {
          id: 2,
          label: '第二卷 修行之路',
          type: 'directory',
          children: [
            { id: 9, label: '第一章 下山历练.txt', type: 'file' },
            { id: 10, label: '第二章 遇到妖兽.txt', type: 'file' },
            { id: 11, label: '第三章 这是一个很长很长的标题，用来测试超出宽度时的省略号效果.txt', type: 'file' },
            { id: 12, label: '第四章 意外收获.txt', type: 'file' },
            { id: 13, label: '第五章 突破瓶颈.txt', type: 'file' }
          ]
        },
        {
          id: 14,
          label: '第三卷 大道争锋',
          type: 'directory',
          children: [
            { id: 15, label: '第一章 宗门大比.txt', type: 'file' },
            { id: 16, label: '第二章 惊人发现.txt', type: 'file' },
            { id: 17, label: '第三章 古老传承.txt', type: 'file' },
            { id: 18, label: '第四章 修为精进.txt', type: 'file' }
          ]
        },
        {
          id: 19,
          label: '第四卷 天地变化',
          type: 'directory',
          children: [
            { id: 20, label: '第一章 世界异变.txt', type: 'file' },
            { id: 21, label: '第二章 神秘遗迹.txt', type: 'file' },
            { id: 22, label: '第三章 远古秘密.txt', type: 'file' },
            { id: 23, label: '第四章 惊天真相.txt', type: 'file' },
            { id: 24, label: '第五章 这又是一个超长的标题用来测试水平滚动条的显示效果以及文本是否会被截断.txt', type: 'file' }
          ]
        },
        {
          id: 25,
          label: '第五卷 巅峰对决',
          type: 'directory',
          children: [
            { id: 26, label: '第一章 最终之战.txt', type: 'file' },
            { id: 27, label: '第二章 决战时刻.txt', type: 'file' },
            { id: 28, label: '第三章 胜负已分.txt', type: 'file' }
          ]
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      itemTypes: [
        { type: 'character', label: '人物' },
        { type: 'location', label: '地点' },
        { type: 'activity', label: '活动' },
        { type: 'skill', label: '功法' },
        { type: 'medicine', label: '丹药' },
        { type: 'weapon', label: '武器' },
        { type: 'equipment', label: '装备' }
      ],
      showItemFormType: '',
      itemForm: {
        name: '',
        gender: '',
        age: 0,
        personality: '',
        description: '',
        quantity: 0
      },
      content: '',
      isEditingTitle: false,
      editingTitle: '',
      editingNode: null,
      editingLabel: '',
      chapterNumber: '',
      chapterTitle: '',
      startTime: null,
      endTime: null,
      wordCount: 0,
      lastWordCount: 0,
      expandedType: '',
      mockResources: {
        character: [
          { 
            name: '张三', 
            gender: '男', 
            age: 25,
            description: '主角，性格开朗，天赋异禀' 
          },
          { 
            name: '李四', 
            gender: '女', 
            age: 22,
            description: '女主角，聪明伶俐' 
          }
        ],
        location: [
          { 
            name: '青云门', 
            description: '主角所在门派，位于青云山脉' 
          }
        ],
        skill: [
          { 
            name: '御剑术', 
            description: '基础剑法，入门级功法' 
          }
        ],
        medicine: [
          { name: '回春丹', description: '一种可以快速恢复体力的丹药' },
          { name: '灵力丹', description: '一种可以快速恢复灵力的丹药' },
          { name: '炼体丹', description: '一种可以快速提升体质的丹药' }
        ],
        weapon: [
          { name: '青云剑', description: '一把锋利无比的剑' },
          { name: '紫电锤', description: '一种可以释放紫电的锤子' },
          { name: '天雷枪', description: '一种可以释放天雷的枪' }
        ],
        equipment: [
          { name: '青云袍', description: '一件轻便而舒适的袍子' },
          { name: '紫电甲', description: '一种可以释放紫电的盔甲' },
          { name: '天雷靴', description: '一种可以释放天雷的靴子' }
        ]
      },
      searchKeywords: {},
      activeTab: 'resource',
      outlineContent: ''
    }
  },
  computed: {
    currentNovelTitle() {
      return this.currentNovel ? this.currentNovel.title : '未选择小说'
    },
    currentItemType() {
      return this.itemTypes.find(item => item.type === this.showItemFormType)
    },
    formTitle() {
      const currentType = this.itemTypes.find(item => item.type === this.showItemFormType)
      return currentType ? currentType.label : ''
    }
  },
  methods: {
    handleLogout() {
      localStorage.removeItem('userInfo')
      this.$router.push('/')
    },
    handleSearch() {
      if (this.searchQuery) {
        // 过滤搜索结果
        const filteredResults = this.searchResults.filter(item =>
          item.title.toLowerCase().includes(this.searchQuery.toLowerCase())
        )
        this.showSearchResults = filteredResults.length > 0
      } else {
        this.showSearchResults = false
      }
    },
    selectNovel(novel) {
      this.currentNovel = novel
      this.showSearchResults = false
      // 加载文件树
      this.loadFileTree()
      // 提示选择成功
      this.$message({
        type: 'success',
        message: `已切换到《${novel.title}》`
      })
    },
    loadFileTree() {
      // 模拟文件树数据
      this.fileTreeData = [
        {
          id: 1,
          label: '第一卷 天道初现',
          type: 'directory',
          children: [
            { id: 3, label: '第一章 灵气复苏.txt', type: 'file' },
            { id: 4, label: '第二章 觉醒天赋.txt', type: 'file' },
            { id: 5, label: '第三章 踏上修仙路.txt', type: 'file' },
            { id: 6, label: '第四章 初入门派.txt', type: 'file' },
            { id: 7, label: '第五章 修炼基础.txt', type: 'file' },
            { id: 8, label: '第六章 同门切磋.txt', type: 'file' }
          ]
        },
        {
          id: 2,
          label: '第二卷 修行之路',
          type: 'directory',
          children: [
            { id: 9, label: '第一章 下山历练.txt', type: 'file' },
            { id: 10, label: '第二章 遇到妖兽.txt', type: 'file' },
            { id: 11, label: '第三章 这是一个很长很长的标题，用来测试超出宽度时的省略号效果.txt', type: 'file' },
            { id: 12, label: '第四章 意外收获.txt', type: 'file' },
            { id: 13, label: '第五章 突破瓶颈.txt', type: 'file' }
          ]
        },
        {
          id: 14,
          label: '第三卷 大道争锋',
          type: 'directory',
          children: [
            { id: 15, label: '第一章 宗门大比.txt', type: 'file' },
            { id: 16, label: '第二章 惊人发现.txt', type: 'file' },
            { id: 17, label: '第三章 古老传承.txt', type: 'file' },
            { id: 18, label: '第四章 修为精进.txt', type: 'file' }
          ]
        },
        {
          id: 19,
          label: '第四卷 天地变化',
          type: 'directory',
          children: [
            { id: 20, label: '第一章 世界异变.txt', type: 'file' },
            { id: 21, label: '第二章 神秘遗迹.txt', type: 'file' },
            { id: 22, label: '第三章 远古秘密.txt', type: 'file' },
            { id: 23, label: '第四章 惊天真相.txt', type: 'file' },
            { id: 24, label: '第五章 这又是一个超长的标题用来测试水平滚动条的显示效果以及文本是否会被截断.txt', type: 'file' }
          ]
        },
        {
          id: 25,
          label: '第五卷 巅峰对决',
          type: 'directory',
          children: [
            { id: 26, label: '第一章 最终之战.txt', type: 'file' },
            { id: 27, label: '第二章 决战时刻.txt', type: 'file' },
            { id: 28, label: '第三章 胜负已分.txt', type: 'file' }
          ]
        }
      ]
    },
    handleNodeClick(data) {
      if (data.type === 'file') {
        // 模拟文件内容
        this.content = `这是 ${data.label} 的内容\n\n作者正在奋笔疾书...`
      }
    },
    handleDelete(node, data) {
      // 如果是顶层节点，不允许删除
      if (!node.parent.parent) {
        this.$message({
          type: 'warning',
          message: '顶层目录不能删除'
        })
        return
      }

      this.$confirm(`确定要删除 ${data.label} 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const parent = node.parent
        const children = parent.data.children || parent.data
        const index = children.findIndex(d => d.id === data.id)
        children.splice(index, 1)
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      }).catch(() => {})
    },
    handleAdd(node, data) {
      if (data.type !== 'directory') return;
      
      this.$prompt('请输入文档名称', '新建文档', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValidator: value => {
          return value.length > 0
        },
        inputErrorMessage: '名称不能为空'
      }).then(({ value }) => {
        if (!data.children) {
          this.$set(data, 'children', [])
        }
        data.children.push({
          id: Date.now(),
          label: `${value}.txt`,
          type: 'file'
        })
        this.$message({
          type: 'success',
          message: '创建成功'
        })
      }).catch(() => {})
    },
    showItemForm(type) {
      this.showItemFormType = type
      this.itemForm = {
        name: '',
        gender: '',
        age: 0,
        personality: '',
        description: '',
        quantity: 0
      }
    },
    saveItem() {
      console.log('保存条目:', {
        type: this.showItemFormType,
        ...this.itemForm
      })
      this.showItemFormType = ''
    },
    // 开始编辑小说标题
    startEditTitle() {
      this.isEditingTitle = true
      this.editingTitle = this.currentNovel.title
      this.$nextTick(() => {
        this.$refs.titleInput.$refs.input.focus()
        this.$refs.titleInput.$refs.input.select()
      })
    },

    // 保存小说标题
    saveTitle() {
      if (this.editingTitle.trim()) {
        this.currentNovel.title = this.editingTitle.trim()
        this.$message({
          type: 'success',
          message: '重命名成功'
        })
      }
      this.isEditingTitle = false
    },

    // 开始编辑节点
    startEdit(node) {
      this.editingNode = node
      this.editingLabel = node.data.label
      this.$nextTick(() => {
        this.$refs.nodeInput.$refs.input.focus()
        this.$refs.nodeInput.$refs.input.select()
      })
    },

    // 保存节点编辑
    saveEdit() {
      if (this.editingLabel.trim()) {
        this.editingNode.data.label = this.editingLabel.trim()
        this.$message({
          type: 'success',
          message: '重命名成功'
        })
      }
      this.editingNode = null
    },

    // 新增顶层目录
    handleAddDirectory() {
      this.$prompt('请输入目录名称', '新建目录', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValidator: value => {
          return value.length > 0
        },
        inputErrorMessage: '名称不能为空'
      }).then(({ value }) => {
        const newDirectory = {
          id: Date.now(),
          label: value,
          type: 'directory',
          children: []
        }
        this.fileTreeData.push(newDirectory)
        this.$message({
          type: 'success',
          message: '创建成功'
        })
      }).catch(() => {})
    },
    // 组件创建时自动加载文件树
    created() {
      if (this.currentNovel) {
        this.loadFileTree()
      }
    },
    cancelEdit() {
      this.showItemFormType = ''
    },
    handleContentChange(value) {
      // 计算字数（只统计文本和标点符号）
      this.wordCount = this.countWords(value)
      
      // 处理开始时间
      if (this.wordCount > 0 && !this.startTime) {
        this.startTime = new Date()
      }
      
      // 处理结束时间
      if (this.wordCount >= 2000 && this.lastWordCount < 2000) {
        this.endTime = new Date()
      }
      
      // 更新上一次的字数
      this.lastWordCount = this.wordCount
    },
    countWords(text) {
      if (!text) return 0
      // 移除换行符，只保留文本和标点
      return text.replace(/\n/g, '').length
    },
    formatTime(date) {
      if (!date) return ''
      const pad = num => String(num).padStart(2, '0')
      return `${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`
    },
    toggleExpand(type) {
      this.expandedType = this.expandedType === type ? '' : type
    },
    getFilteredResources(type) {
      const keyword = this.searchKeywords[type]
      if (!keyword) return this.mockResources[type] || []
      
      return (this.mockResources[type] || []).filter(resource => {
        return resource.name.toLowerCase().includes(keyword.toLowerCase()) ||
               (resource.description && resource.description.toLowerCase().includes(keyword.toLowerCase()))
      })
    },
    handleResourceSearch() {
      // 搜索时会自动触发重新渲染
    },
    switchTab(tab) {
      this.activeTab = tab
    }
  }
}
</script>

<style scoped>
.content-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: white;
  padding: 16px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.main-container {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.left-panel,
.right-panel {
  width: 400px;
  min-width: 400px;
  max-width: 400px;
  border-right: 1px solid #e0e0e0;
}

.document-section {
  height: 500px;
  min-height: 500px;
  max-height: 500px;
  padding: 5px 16px 16px 5px;  /* 上右下左，修改底部内边距为 16px */
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.search-box {
  margin: 15px 0 15px 15px;  /* 上右下左 */
  flex-shrink: 0;
}

.search-results {
  position: absolute;
  width: calc(100% - 31px);  /* 调整宽度以适应新的右侧间距 */
  margin: 0 0 0 15px;  /* 上右下左 */
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.search-result-item {
  padding: 8px 16px;
  cursor: pointer;
}

.search-result-item:hover {
  background: #f5f7fa;
}

.novel-header {
  margin: 0 0 15px 15px;  /* 上右下左 */
  padding: 8px 12px;
  background: #f5f7fa;
  border-radius: 4px;
  flex-shrink: 0;
}

.novel-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.title-text {
  flex: 1;
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  cursor: default;
  user-select: none;
}

.title-actions {
  display: flex;
  gap: 8px;
}

.title-actions i {
  font-size: 16px;
  color: #909399;
  cursor: pointer;
  padding: 2px;
}

.title-actions i:hover {
  color: #409EFF;
}

.file-tree {
  flex: 1;
  overflow: auto;
  padding: 0 0 16px 15px;
}

/* 添加一个内容容器来保持底部间距 */
:deep(.el-tree) {
  padding-bottom: 5px; /* 内容区域与滚动条的间距 */
  min-height: 100%;
}

/* 确保最后一个节点也有底部间距 */
:deep(.el-tree-node:last-child) {
  margin-bottom: 5px;
}

.custom-tree-node {
  display: flex;
  align-items: center;
  width: calc(100% - 10px);
  overflow-x: auto;
  user-select: none;
  padding-bottom: 5px; /* 添加底部内边距，让内容与滚动条保持距离 */
}

.node-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-width: 100%;
  padding-right: 5px;
  position: relative;
}

.node-content {
  display: flex;
  align-items: center;
  gap: 8px;
  flex: 1;
  min-width: 0;
  padding-right: 70px; /* 为操作按钮预留空间 */
}

.node-icon {
  font-size: 16px;
  color: #909399;
}

.el-icon-folder {
  color: #e6a23c;
}

.el-icon-document {
  color: #409EFF;
}

.node-label {
  white-space: nowrap;
  overflow: visible; /* 允许文本完整显示 */
  flex: 1;
}

.node-actions {
  position: absolute;
  right: 5px;
  display: none;
  gap: 8px;
  background: inherit;
}

/* 调整滚动条样式 */
.custom-tree-node::-webkit-scrollbar {
  height: 6px;
}

.custom-tree-node::-webkit-scrollbar-thumb {
  background: #c0c4cc;
  border-radius: 3px;
}

.custom-tree-node::-webkit-scrollbar-track {
  background: #f5f7fa;
}

/* 确保树节点容器不会限制内容宽度 */
:deep(.el-tree-node__content) {
  min-width: fit-content;
  padding-right: 0 !important;
}

:deep(.el-tree-node) {
  min-width: fit-content;
  white-space: nowrap;
}

.items-section {
  flex: 1;
  height: 300px;
  min-height: 300px;
  max-height: 300px;
  padding: 20px;
  overflow-y: auto;
}

.item-buttons {
  display: grid;
  grid-template-columns: repeat(2, 1fr);  /* 保持两列布局 */
  gap: 10px;  /* 统一设置按钮之间的间距为 10px */
  width: 100%;
  padding: 0 10px;  /* 添加左右内边距 */
}

.item-buttons .el-button {
  width: 100%;
  height: 32px;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0;  /* 移除按钮默认的外边距 */
}

/* 确保按钮文字居中 */
.item-buttons .el-button span {
  display: inline-block;
  width: 100%;
  text-align: center;
}

.item-form {
  width: 100%;
}

.form-title {
  font-size: 16px;
  font-weight: 500;
  color: #303133;
  margin: 0 0 20px 0;
  text-align: center;
}

.form-buttons {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

/* 调整滚动条样式 */
.items-section::-webkit-scrollbar {
  width: 6px;
}

.items-section::-webkit-scrollbar-thumb {
  background: #c0c4cc;
  border-radius: 3px;
}

.items-section::-webkit-scrollbar-track {
  background: #f5f7fa;
}

.center-panel {
  flex: 1;
  display: flex;
  justify-content: center;
  padding: 32px 0;  /* 保持上下间距 32px */
  background: #f5f7fa;
}

.editor-container {
  width: calc(210mm + 180px);  /* A4纸宽度 + 统计信息宽度 + 间距 */
  height: 100%;
  background: white;
  padding: 32px 20mm 16px;
  display: flex;
  flex-direction: column;
}

/* 章节标题区域样式 */
.chapter-title {
  display: flex;
  align-items: center;
  margin-bottom: 16px;  /* 与编辑区域的间距 */
  gap: 8px;
  flex-shrink: 0;  /* 防止标题区域被压缩 */
}

.chapter-prefix,
.chapter-middle {
  font-size: 14px;
  color: #303133;
}

.chapter-number {
  width: 60px;
}

.chapter-name {
  flex: 1;
}

/* 编辑器内容区域 */
.editor-content {
  flex: 1;
  display: flex;
  gap: 16px;  /* 输入框和统计信息之间的间距 */
}

/* 内容包装器样式 */
.content-wrapper {
  flex: 1;
  display: flex;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
}

/* 文本输入框样式 */
.content-textarea {
  width: 100%;
}

:deep(.content-textarea .el-textarea__inner) {
  height: 100% !important;
  border: none;
  padding: 16px;
  line-height: 1.6;
  font-size: 14px;
  resize: none;
  background: transparent;
}

/* 统计信息显示样式 */
.content-stats {
  width: 140px;
  flex-shrink: 0;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 16px;
  background: #fff;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-label {
  font-size: 12px;
  color: #909399;
}

.stat-value {
  font-size: 14px;
  color: #303133;
  font-family: monospace;
}

.logout-btn {
  padding: 8px 16px;
  background: #ff3b30;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.logout-btn:hover {
  background: #ff453a;
}

/* 添加输入框样式 */
.el-input {
  margin: -5px 0;
  cursor: text !important;
  user-select: text !important;
}

.el-input.el-input--small .el-input__inner {
  height: 28px;
  line-height: 28px;
}

/* 覆盖 element-ui 的默认样式 */
:deep(.el-tree-node__content) {
  user-select: none !important;
}

:deep(.el-tree-node__expand-icon) {
  user-select: none !important;
  cursor: pointer !important;
}

/* 调整 tooltip 样式 */
:deep(.el-tooltip__popper) {
  max-width: 300px;
  word-break: break-all;
}

/* 调整滚动条样式 */
.file-tree::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

.file-tree::-webkit-scrollbar-thumb {
  background: #c0c4cc;
  border-radius: 3px;
}

.file-tree::-webkit-scrollbar-track {
  background: #f5f7fa;
}

.right-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 0;  /* 移除内边距 */
}

.tabs {
  height: 40px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #dcdfe6;
  background: #fff;
  padding: 0;
  margin: 0;
  user-select: none;  /* 整个选项卡区域都不可选中 */
}

.tab {
  height: 40px;
  line-height: 40px;
  padding: 0 20px;
  font-size: 14px;
  color: #606266;
  cursor: pointer;
  position: relative;
  transition: all 0.3s;
  margin: 0;
  border: none;
  user-select: none;  /* 防止文本被选中 */
  outline: none;      /* 移除点击时的轮廓 */
}

.tab:hover {
  color: #409EFF;
}

.tab.active {
  color: #409EFF;
  font-weight: 500;
}

.tab.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: #409EFF;
}

.resource-list {
  flex: 1;
  overflow-y: auto;
}

.resource-section {
  margin-bottom: 10px;
}

.resource-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 48px;
  padding: 0 16px;
  background: #f5f7fa;
  cursor: pointer;
  user-select: none;
}

.resource-header:hover {
  background: #ecf5ff;
}

.resource-title {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
}

.el-icon-arrow-down {
  font-size: 14px;
  color: #909399;
  transition: transform 0.3s ease;
}

.el-icon-arrow-down.expanded {
  transform: rotate(180deg);
}

.resource-content {
  background: #fff;
  border: 1px solid #e4e7ed;
  border-top: none;
  margin-bottom: 8px;
}

.resource-search {
  padding: 16px;
  border-bottom: 1px solid #e4e7ed;
}

.resource-list-content {
  padding: 16px;
}

.resource-item {
  padding: 12px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  margin-bottom: 8px;
  background: #fff;
}

.resource-item:last-child {
  margin-bottom: 0;
}

.resource-name {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
}

.resource-info {
  margin-top: 5px;
  font-size: 12px;
  color: #909399;
}

.resource-desc {
  margin-top: 5px;
  font-size: 12px;
  color: #606266;
}

.tab-content {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  background: #fff;
}
</style> 