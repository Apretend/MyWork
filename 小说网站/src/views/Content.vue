<template>
  <div class="content-page">
    <!-- 左侧边栏 -->
    <div class="left-sidebar">
      <!-- 上半部分：文档目录 -->
      <div class="directory-section">
        <!-- 搜索框 -->
        <div class="search-box">
          <el-select
            v-model="selectedBook"
            filterable
            remote
            reserve-keyword
            placeholder="搜索小说"
            :remote-method="handleSearch"
            :loading="loading"
            class="book-search"
          >
            <el-option
              v-for="item in searchResults"
              :key="item.id"
              :label="item.title"
              :value="item.id"
            >
              <span class="book-option">
                <span class="book-title">{{ item.title }}</span>
                <span class="book-author">{{ item.author }}</span>
              </span>
            </el-option>
          </el-select>
        </div>

        <!-- 书名区域 -->
        <div class="book-title">
          <h3>{{ currentBook.title || '请选择小说' }}</h3>
        </div>
        
        <!-- 目录树结构 -->
        <el-tree
          v-if="currentBook.id"
          :data="bookStructure"
          :props="defaultProps"
          @node-click="handleNodeClick"
          :expand-on-click-node="false"
          default-expand-all
          class="doc-tree"
        >
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span class="node-content">
              <i :class="getIconClass(data)"></i>
              <template v-if="data.isEditing">
                <el-input
                  v-model="data.editingLabel"
                  size="mini"
                  @blur="handleSaveEdit(data)"
                  @keyup.enter.native="handleSaveEdit(data)"
                  ref="editInput"
                ></el-input>
              </template>
              <template v-else>
                <el-tooltip :content="node.label" placement="right" :disabled="!isTextOverflow(node.label)">
                  <span class="node-label">{{ node.label }}</span>
                </el-tooltip>
              </template>
            </span>
            <span class="node-actions">
              <el-button
                v-if="data.type === 'chapter'"
                type="text"
                size="mini"
                @click.stop="() => handleAdd(data)"
              >
                <i class="el-icon-plus"></i>
              </el-button>
              <el-button
                type="text"
                size="mini"
                @click.stop="() => handleEdit(data)"
              >
                <i class="el-icon-edit"></i>
              </el-button>
            </span>
          </span>
        </el-tree>

        <!-- 新建节点的输入框 -->
        <el-dialog
          title="新建"
          :visible.sync="dialogVisible"
          width="30%"
          :close-on-click-modal="false"
        >
          <el-input
            v-model="newNodeName"
            placeholder="请输入名称"
            @keyup.enter.native="handleSaveAdd"
          ></el-input>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="handleSaveAdd">确 定</el-button>
          </span>
        </el-dialog>
      </div>

      <!-- 下半部分：待添加 -->
      <div class="bottom-section">
        <div class="section-title">
          <span>其他功能区域</span>
        </div>
      </div>
    </div>

    <!-- 中间编辑区域 -->
    <div class="main-content">
      <div class="a4-container">
        <el-input
          type="textarea"
          v-model="content"
          :rows="30"
          placeholder="请输入内容"
          class="content-editor"
        ></el-input>
      </div>
    </div>

    <!-- 右侧边栏 -->
    <div class="right-sidebar">
      <el-card class="tools-panel">
        <div slot="header">
          <span>工具栏</span>
        </div>
        <el-button type="primary" size="small" icon="el-icon-document-add" round>新建</el-button>
        <el-button type="success" size="small" icon="el-icon-check" round>保存</el-button>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Content',
  data() {
    return {
      content: '',
      searchText: '',
      dialogVisible: false,
      newNodeName: '',
      currentParentNode: null,
      mockBooks: [
        { 
          id: '1', 
          title: '武林外传', 
          author: '宁财神',
          structure: [
            {
              id: '1-1',
              label: '正文',
              type: 'chapter',
              isEditing: false,
              editingLabel: '',
              children: [
                {
                  id: '1-1-1',
                  label: '第一章 白展堂的来历',
                  type: 'section',
                  isEditing: false,
                  editingLabel: ''
                },
                {
                  id: '1-1-2',
                  label: '第二章 佟湘玉的同福客栈',
                  type: 'section',
                  isEditing: false,
                  editingLabel: ''
                },
                {
                  id: '1-1-3',
                  label: '第三章 郭芙蓉下山',
                  type: 'section',
                  isEditing: false,
                  editingLabel: ''
                }
              ]
            },
            {
              id: '1-2',
              label: '设定集',
              type: 'chapter',
              isEditing: false,
              editingLabel: '',
              children: [
                {
                  id: '1-2-1',
                  label: '人物志',
                  type: 'section',
                  isEditing: false,
                  editingLabel: ''
                },
                {
                  id: '1-2-2',
                  label: '同福客栈布局',
                  type: 'section',
                  isEditing: false,
                  editingLabel: ''
                }
              ]
            }
          ]
        },
        { 
          id: '2', 
          title: '笑傲江湖', 
          author: '金庸',
          structure: [
            {
              id: '2-1',
              label: '正文',
              type: 'chapter',
              isEditing: false,
              editingLabel: '',
              children: [
                {
                  id: '2-1-1',
                  label: '第一章 令狐冲被逐出师门',
                  type: 'section',
                  isEditing: false,
                  editingLabel: ''
                },
                {
                  id: '2-1-2',
                  label: '第二章 林平之父母遇害',
                  type: 'section',
                  isEditing: false,
                  editingLabel: ''
                }
              ]
            }
          ]
        }
      ],
      selectedBook: '1',
      currentBook: {},
      bookStructure: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      searchResults: [],
      loading: false
    }
  },
  created() {
    this.handleBookSelect(this.selectedBook)
  },
  methods: {
    handleNodeClick(data) {
      if (data.type === 'content') {
        console.log('打开内容:', data.label)
      }
    },
    getIconClass(data) {
      switch (data.type) {
        case 'chapter':
          return 'el-icon-folder'
        case 'section':
          return 'el-icon-document'
        default:
          return 'el-icon-folder'
      }
    },
    handleAdd(data) {
      this.currentParentNode = data
      this.dialogVisible = true
      this.newNodeName = ''
    },
    handleSaveAdd() {
      if (!this.newNodeName.trim()) {
        this.$message.warning('名称不能为空')
        return
      }

      const newNode = {
        id: Date.now().toString(),
        label: this.newNodeName,
        isEditing: false,
        editingLabel: '',
        type: 'section'
      }

      if (!this.currentParentNode.children) {
        this.$set(this.currentParentNode, 'children', [])
      }
      
      this.currentParentNode.children.push(newNode)
      this.dialogVisible = false
      this.$message.success('添加成功')
    },
    handleEdit(data) {
      data.editingLabel = data.label
      this.$set(data, 'isEditing', true)
      this.$nextTick(() => {
        const input = this.$refs.editInput
        if (input && input.length) {
          input[0].focus()
        }
      })
    },
    handleSaveEdit(data) {
      if (data.editingLabel.trim()) {
        data.label = data.editingLabel
        this.$message.success('修改成功')
      }
      this.$set(data, 'isEditing', false)
    },
    handleSearch(query) {
      if (query) {
        this.loading = true
        setTimeout(() => {
          this.searchResults = this.mockBooks.filter(item => {
            return item.title.toLowerCase().includes(query.toLowerCase()) ||
                   item.author.toLowerCase().includes(query.toLowerCase())
          })
          this.loading = false
        }, 200)
      } else {
        this.searchResults = this.mockBooks
      }
    },
    async handleBookSelect(value) {
      if (value) {
        const book = this.mockBooks.find(item => item.id === value)
        if (book) {
          this.currentBook = book
          await this.loadBookStructure(value)
        }
      }
    },
    async loadBookStructure(bookId) {
      this.loading = true
      setTimeout(() => {
        const book = this.mockBooks.find(item => item.id === bookId)
        if (book) {
          this.bookStructure = book.structure
        }
        this.loading = false
      }, 300)
    },
    isTextOverflow(text) {
      const span = document.createElement('span')
      span.style.cssText = 'position:absolute; visibility:hidden; white-space:nowrap;'
      span.innerText = text
      document.body.appendChild(span)
      const width = span.offsetWidth
      document.body.removeChild(span)
      return width > 200
    }
  },
  watch: {
    selectedBook: {
      handler: 'handleBookSelect',
      immediate: true
    }
  }
}
</script>

<style scoped>
.content-page {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.left-sidebar {
  width: 300px;
  background-color: #f5f7fa;
  border-right: 1px solid #e6e6e6;
  display: flex;
  flex-direction: column;
}

.directory-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid #e6e6e6;
}

.book-title {
  padding: 10px 15px;
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
}

.book-title h3 {
  margin: 0;
  font-size: 16px;
  color: #303133;
  text-align: center;
}

.bottom-section {
  height: 300px;
  padding: 15px;
}

.section-title {
  font-size: 14px;
  color: #606266;
  margin-bottom: 10px;
}

.doc-tree {
  flex: 1;
  overflow-x: auto;
  overflow-y: auto;
  padding: 10px;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
  width: 100%;
}

.node-actions {
  opacity: 0;
  transition: opacity 0.2s;
  white-space: nowrap;
  display: flex;
  align-items: center;
}

.custom-tree-node:hover .node-actions {
  opacity: 1;
}

.node-actions .el-button {
  padding: 2px 6px;
  margin-left: 4px;
}

.main-content {
  flex: 1;
  padding: 20px;
  background-color: #f0f2f5;
  overflow-y: auto;
  display: flex;
  justify-content: center;
}

.a4-container {
  width: 210mm;
  background-color: white;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.content-editor {
  width: 100%;
}

.right-sidebar {
  width: 200px;
  background-color: #f5f7fa;
  border-left: 1px solid #e6e6e6;
  padding: 10px;
}

.tools-panel {
  height: 100%;
}

.tools-panel .el-button {
  margin: 5px 0;
  width: 100%;
  border-radius: 20px;
}

/* 设置文本域的样式 */
.content-editor >>> .el-textarea__inner {
  font-size: 14px;
  line-height: 1.6;
  padding: 15px;
  border-radius: 8px;
}

/* 树形控件的样式优化 */
.doc-tree >>> .el-tree-node__content {
  height: auto;
  min-height: 32px;
  padding: 4px 0;
}

.doc-tree >>> .el-tree-node__content:hover {
  background-color: #e6f2ff;
}

i[class^="el-icon-"] {
  margin-right: 5px;
  font-size: 16px;
}

.search-box {
  padding: 15px;
  border-bottom: 1px solid #e6e6e6;
}

.book-search {
  width: 100%;
}

.book-option {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
}

.book-author {
  color: #909399;
  font-size: 12px;
}

/* 自定义el-select样式 */
.book-search >>> .el-input__inner {
  border-radius: 20px;
}

.book-search >>> .el-select-dropdown__item {
  padding: 8px 15px;
}

/* 添加节点内容容器样式 */
.node-content {
  display: flex;
  align-items: center;
  min-width: 0;
  flex: 1;
  margin-right: 8px;
}

/* 文本溢出处理 */
.node-content span {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-right: 8px;
}

/* 编辑输入框样式 */
.node-content .el-input {
  flex: 1;
  margin-right: 8px;
  max-width: 220px;
}

/* 修改节点标签样式 */
.node-label {
  display: inline-block;
  max-width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  vertical-align: middle;
}

/* 确保tooltip正常显示 */
.el-tooltip {
  display: inline-block;
  max-width: 100%;
}

/* 优化树节点样式 */
.doc-tree >>> .el-tree-node__content {
  padding: 8px 0;
}

.doc-tree >>> .el-tree-node__children {
  overflow: visible;
}
</style> 