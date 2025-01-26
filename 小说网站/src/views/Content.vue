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
            
            <!-- 人物表单 -->
            <el-form v-if="showItemFormType === 'character'" :model="characterForm" label-width="80px">
              <el-form-item label="姓名" required>
                <el-input v-model="characterForm.name" placeholder="请输入姓名"></el-input>
              </el-form-item>
              <el-form-item label="性别" required>
                <el-select v-model="characterForm.gender" placeholder="请选择性别">
                  <el-option label="男" value="男"></el-option>
                  <el-option label="女" value="女"></el-option>
                  <el-option label="其他" value="其他"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="年龄" required>
                <el-input-number v-model="characterForm.age" :min="0"></el-input-number>
              </el-form-item>
              <el-form-item label="性格" required>
                <el-input type="textarea" v-model="characterForm.personality" :rows="2"></el-input>
              </el-form-item>
              <el-form-item label="人设描述" required>
                <el-input type="textarea" v-model="characterForm.description" :rows="3"></el-input>
              </el-form-item>
              <el-form-item label="背景故事" required>
                <el-input type="textarea" v-model="characterForm.background" :rows="4"></el-input>
              </el-form-item>
              <el-form-item label="实力" required>
                <el-input v-model="characterForm.strength"></el-input>
              </el-form-item>
              <el-form-item label="存活状态">
                <el-switch v-model="characterForm.isAlive"></el-switch>
              </el-form-item>
            </el-form>

            <!-- 地点表单 -->
            <el-form v-if="showItemFormType === 'location'" :model="locationForm" label-width="80px">
              <el-form-item label="地点名称" required>
                <el-input v-model="locationForm.name" placeholder="请输入地点名称"></el-input>
              </el-form-item>
              <el-form-item label="地点类型" required>
                <el-input v-model="locationForm.type" placeholder="请输入地点类型"></el-input>
              </el-form-item>
              <el-form-item label="地点特色" required>
                <el-input type="textarea" v-model="locationForm.feature" :rows="3"></el-input>
              </el-form-item>
              <el-form-item label="地点位置" required>
                <el-input v-model="locationForm.position"></el-input>
              </el-form-item>
              <el-form-item label="地点描述" required>
                <el-input type="textarea" v-model="locationForm.description" :rows="4"></el-input>
              </el-form-item>
            </el-form>

            <!-- 武器表单 -->
            <el-form v-if="showItemFormType === 'weapon'" :model="weaponForm" label-width="80px">
              <el-form-item label="武器名称" required>
                <el-input v-model="weaponForm.name" placeholder="请输入武器名称"></el-input>
              </el-form-item>
              <el-form-item label="武器类型" required>
                <el-input v-model="weaponForm.type" placeholder="请输入武器类型"></el-input>
              </el-form-item>
              <el-form-item label="武器主人">
                <el-input v-model="weaponForm.owner" placeholder="请输入武器主人"></el-input>
              </el-form-item>
              <el-form-item label="武器威力" required>
                <el-input v-model="weaponForm.power"></el-input>
              </el-form-item>
              <el-form-item label="武器来源" required>
                <el-input v-model="weaponForm.origin"></el-input>
              </el-form-item>
              <el-form-item label="武器描述" required>
                <el-input type="textarea" v-model="weaponForm.description" :rows="4"></el-input>
              </el-form-item>
            </el-form>

            <!-- 装备表单 -->
            <el-form v-if="showItemFormType === 'equipment'" :model="equipmentForm" label-width="80px">
              <el-form-item label="装备名称" required>
                <el-input v-model="equipmentForm.name" placeholder="请输入装备名称"></el-input>
              </el-form-item>
              <el-form-item label="装备类型" required>
                <el-input v-model="equipmentForm.type" placeholder="请输入装备类型"></el-input>
              </el-form-item>
              <el-form-item label="装备主人">
                <el-input v-model="equipmentForm.owner" placeholder="请输入装备主人"></el-input>
              </el-form-item>
              <el-form-item label="装备效果" required>
                <el-input v-model="equipmentForm.effect"></el-input>
              </el-form-item>
              <el-form-item label="装备来源" required>
                <el-input v-model="equipmentForm.origin"></el-input>
              </el-form-item>
              <el-form-item label="装备描述" required>
                <el-input type="textarea" v-model="equipmentForm.description" :rows="4"></el-input>
              </el-form-item>
            </el-form>

            <!-- 丹药表单 -->
            <el-form v-if="showItemFormType === 'elixir'" :model="elixirForm" label-width="80px">
              <el-form-item label="丹药名称" required>
                <el-input v-model="elixirForm.name" placeholder="请输入丹药名称"></el-input>
              </el-form-item>
              <el-form-item label="丹药类型" required>
                <el-input v-model="elixirForm.type" placeholder="请输入丹药类型"></el-input>
              </el-form-item>
              <el-form-item label="丹药效果" required>
                <el-input type="textarea" v-model="elixirForm.effect" :rows="3"></el-input>
              </el-form-item>
              <el-form-item label="丹药来源" required>
                <el-input v-model="elixirForm.origin"></el-input>
              </el-form-item>
              <el-form-item label="重要原料" required>
                <el-input v-model="elixirForm.keyIngredient"></el-input>
              </el-form-item>
              <el-form-item label="丹药描述" required>
                <el-input type="textarea" v-model="elixirForm.description" :rows="4"></el-input>
              </el-form-item>
            </el-form>

            <!-- 功法表单 -->
            <el-form v-if="showItemFormType === 'gongfa'" :model="gongfaForm" label-width="80px">
              <el-form-item label="功法名称" required>
                <el-input v-model="gongfaForm.name" placeholder="请输入功法名称"></el-input>
              </el-form-item>
              <el-form-item label="功法类型" required>
                <el-input v-model="gongfaForm.type" placeholder="请输入功法类型"></el-input>
              </el-form-item>
              <el-form-item label="功法主人">
                <el-input v-model="gongfaForm.owner" placeholder="请输入功法主人"></el-input>
              </el-form-item>
              <el-form-item label="功法效果" required>
                <el-input v-model="gongfaForm.effect"></el-input>
              </el-form-item>
              <el-form-item label="功法来源" required>
                <el-input v-model="gongfaForm.origin"></el-input>
              </el-form-item>
              <el-form-item label="功法描述" required>
                <el-input type="textarea" v-model="gongfaForm.description" :rows="4"></el-input>
              </el-form-item>
            </el-form>

            <div class="form-actions">
              <el-button @click="cancelEdit">取消</el-button>
              <el-button type="primary" @click="saveItem">保存</el-button>
            </div>
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
            </div>
            
            <!-- 右侧统计信息 -->
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
              <!-- 添加保存按钮 -->
              <el-button 
                type="primary" 
                @click="saveContent"
                :loading="isSaving"
                class="save-content-btn"
              >
                保存内容
              </el-button>
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
          <div 
            class="tab"
            :class="{ 'active': activeTab === 'book' }"
            @click="switchTab('book')"
          >
            本书
          </div>
          <div 
            class="tab"
            :class="{ 'active': activeTab === 'library' }"
            @click="switchTab('library')"
          >
            书库
          </div>
          <div 
            class="tab"
            :class="{ 'active': activeTab === 'newBook' }"
            @click="switchTab('newBook')"
          >
            创建新书
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

        <!-- 修改本书信息区域的结构 -->
        <div v-show="activeTab === 'book'" class="tab-content book-info">
          <!-- 书名部分 -->
          <div class="book-section">
            <div class="section-title book-title">书名</div>
            <div class="book-titles">
              <div 
                v-for="(title, index) in bookTitleList" 
                :key="index"
                class="title-item"
              >
                <el-input
                  v-model="title.value"
                  placeholder="请输入书名"
                  maxlength="20"
                  :style="{ width: '240px' }"
                ></el-input>
                <i 
                  v-if="bookTitleList.length > 1"
                  class="el-icon-delete"
                  @click="removeBookTitle(index)"
                ></i>
              </div>
            </div>
            <el-button 
              type="text" 
              icon="el-icon-plus"
              class="add-title-btn"
              @click="addBookTitle"
              :disabled="bookTitleList.length >= 5"
            >
              添加书名
            </el-button>
          </div>

          <!-- 简介部分 -->
          <div class="book-section">
            <div class="section-title">简介</div>
            <div class="input-wrapper">
              <el-input
                type="textarea"
                v-model="bookIntro"
                :rows="4"
                show-word-limit
                maxlength="500"
                placeholder="请输入书籍简介"
              ></el-input>
            </div>
          </div>

          <!-- 故事大纲部分 -->
          <div class="book-section">
            <div class="section-title required">故事大纲</div>
            <div class="input-wrapper">
              <el-input
                type="textarea"
                v-model="bookStory"
                :rows="6"
                placeholder="请输入故事大纲"
              ></el-input>
              <span class="word-count">大纲字数：{{ bookStory.length }}</span>
            </div>
          </div>

          <!-- 封面部分 -->
          <div class="book-section">
            <div class="section-title">封面</div>
            <el-upload
              action="#"
              list-type="picture-card"
              :auto-upload="false"
              :file-list="fileList"
              @remove="handleRemove"
              @preview="handlePictureCardPreview"
            >
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img
                  class="el-upload-list__item-thumbnail"
                  :src="file.url" 
                  alt=""
                >
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleDownload(file)"
                  >
                    <i class="el-icon-download"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)"
                  >
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </div>

          <!-- 添加保存按钮 -->
          <div class="form-actions">
            <el-button type="primary" @click="saveBookInfo">保存</el-button>
          </div>
        </div>

        <!-- 修改书库区域 -->
        <div v-show="activeTab === 'library'" class="tab-content library-content">
          <!-- 搜索框 -->
          <div class="library-search">
            <el-input
              v-model="librarySearchQuery"
              placeholder="搜索书名或简介..."
              prefix-icon="el-icon-search"
              clearable
              @input="handleLibrarySearch"
            ></el-input>
          </div>

          <!-- 书籍列表 -->
          <div class="library-list">
            <div 
              v-for="book in filteredLibraryBooks" 
              :key="book.id"
              class="library-book-item"
              @click="switchToBook(book)"
            >
              <div class="book-item-header">
                <span class="book-name">{{ book.title }}</span>
                <span class="chapter-count">共 {{ book.chapterCount }} 章</span>
              </div>
              <div class="book-intro">{{ book.intro }}</div>
            </div>
          </div>
        </div>

        <!-- 修改创建新书区域 -->
        <div v-show="activeTab === 'newBook'" class="tab-content book-info">
          <!-- 书名部分 -->
          <div class="book-section">
            <div class="section-title book-title">书名</div>
            <div class="book-titles">
              <div 
                v-for="(title, index) in newBookTitles" 
                :key="index"
                class="title-item"
              >
                <el-input
                  v-model="title.value"
                  placeholder="请输入书名"
                  maxlength="20"
                  :style="{ width: '240px' }"
                ></el-input>
                <i 
                  v-if="newBookTitles.length > 1"
                  class="el-icon-delete"
                  @click="removeNewBookTitle(index)"
                ></i>
              </div>
            </div>
            <el-button 
              type="text" 
              icon="el-icon-plus"
              class="add-title-btn"
              @click="addNewBookTitle"
              :disabled="newBookTitles.length >= 5"
            >
              添加书名
            </el-button>
          </div>

          <!-- 简介部分 -->
          <div class="book-section">
            <div class="section-title">简介</div>
            <div class="input-wrapper">
              <el-input
                type="textarea"
                v-model="newBookIntro"
                :rows="4"
                show-word-limit
                maxlength="500"
                placeholder="请输入书籍简介"
              ></el-input>
            </div>
          </div>

          <!-- 故事大纲部分 -->
          <div class="book-section">
            <div class="section-title required">故事大纲</div>
            <div class="input-wrapper">
              <el-input
                type="textarea"
                v-model="newBookStory"
                :rows="6"
                placeholder="请输入故事大纲"
              ></el-input>
              <span class="word-count">大纲字数：{{ newBookStory.length }}</span>
            </div>
          </div>

          <!-- 封面部分 -->
          <div class="book-section">
            <div class="section-title">封面</div>
            <el-upload
              action="#"
              list-type="picture-card"
              :auto-upload="false"
              :file-list="newBookFileList"
              @remove="handleNewBookRemove"
              @preview="handlePictureCardPreview"
            >
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img
                  class="el-upload-list__item-thumbnail"
                  :src="file.url" 
                  alt=""
                >
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleDownload(file)"
                  >
                    <i class="el-icon-download"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleNewBookRemove(file)"
                  >
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
          </div>

          <!-- 操作按钮 -->
          <div class="form-actions">
            <el-button @click="cancelNewBook">取消</el-button>
            <el-button type="primary" @click="saveNewBook">保存</el-button>
          </div>
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
        { type: 'weapon', label: '武器' },
        { type: 'equipment', label: '装备' },
        { type: 'elixir', label: '丹药' },
        { type: 'gongfa', label: '功法' },
      ],
      showItemFormType: '',
      characterForm: {
        name: '',
        gender: '',
        age: 0,
        personality: '',
        description: '',
        background: '',
        strength: '',
        isAlive: true
      },
      locationForm: {
        name: '',
        type: '',
        feature: '',
        position: '',
        description: ''
      },
      weaponForm: {
        name: '',
        type: '',
        owner: '',
        power: '',
        origin: '',
        description: ''
      },
      equipmentForm: {
        name: '',
        type: '',
        owner: '',
        effect: '',
        origin: '',
        description: ''
      },
      elixirForm: {
        name: '',
        type: '',
        effect: '',
        origin: '',
        keyIngredient: '',
        description: ''
      },
      gongfaForm: {
        name: '',
        type: '',
        owner: '',
        effect: '',
        origin: '',
        description: ''
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
      outlineContent: '',
      bookTitleList: [
        { value: '稿费起飞从搭建工具开始' }
      ],
      bookCover: '',
      bookIntro: '',
      bookStory: '',
      libraryBooks: [
        {
          id: 1,
          title: '稿费起飞从搭建工具开始',
          chapterCount: 23,
          intro: '这是一个关于程序员如何通过开发工具实现稿费收入突飞猛进的故事，主人公通过自己的努力和天赋，开发出了一款深受用户喜爱的写作工具...'
        },
        {
          id: 2,
          title: '重生之科技巨头',
          chapterCount: 45,
          intro: '一位互联网公司的普通程序员意外重生到了20年前，凭借对未来科技发展的了解，开启了一段传奇之路，从零开始打造属于自己的科技帝国。'
        },
        {
          id: 3,
          title: '修仙从写代码开始',
          chapterCount: 67,
          intro: '程序员王小明发现自己写的代码竟然能产生法力，一行行代码编织出神奇的法术，用程序员的方式在修仙界掀起一场革命。'
        },
        {
          id: 4,
          title: '全球编程之王',
          chapterCount: 89,
          intro: '在一个编程就是一切的世界里，主角凭借着对代码的极致理解和创新思维，在全球编程竞赛中不断创造奇迹，最终成为传说中的编程之王。'
        },
        {
          id: 5,
          title: 'AI时代的旅行者',
          chapterCount: 34,
          intro: '在人工智能全面普及的未来世界，一位极客少年意外获得了一段神秘代码，由此揭开了AI背后不为人知的秘密。'
        }
      ],
      newBookData: {
        title: '',
        subTitles: ['', '']
      },
      dialogVisible: false,
      dialogImageUrl: '',
      disabled: false,
      fileList: [],
      newBookTitles: [
        { value: '' }
      ],
      newBookIntro: '',
      newBookStory: '',
      newBookFileList: [],
      librarySearchQuery: '',
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
    },
    filteredLibraryBooks() {
      if (!this.librarySearchQuery) {
        return this.libraryBooks
      }
      const query = this.librarySearchQuery.toLowerCase()
      return this.libraryBooks.filter(book => 
        book.title.toLowerCase().includes(query) ||
        book.intro.toLowerCase().includes(query)
      )
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
      this.characterForm = {
        name: '',
        gender: '',
        age: 0,
        personality: '',
        description: '',
        background: '',
        strength: '',
        isAlive: true
      }
      this.locationForm = {
        name: '',
        type: '',
        feature: '',
        position: '',
        description: ''
      }
      this.weaponForm = {
        name: '',
        type: '',
        owner: '',
        power: '',
        origin: '',
        description: ''
      }
      this.equipmentForm = {
        name: '',
        type: '',
        owner: '',
        effect: '',
        origin: '',
        description: ''
      }
      this.elixirForm = {
        name: '',
        type: '',
        effect: '',
        origin: '',
        keyIngredient: '',
        description: ''
      }
      this.gongfaForm = {
        name: '',
        type: '',
        owner: '',
        effect: '',
        origin: '',
        description: ''
      }
    },
    saveItem() {
      console.log('保存条目:', {
        type: this.showItemFormType,
        ...this[`${this.showItemFormType}Form`]
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
    },
    removeBookTitle(index) {
      if (this.bookTitleList.length > 1) {  // 确保至少保留一个书名
        this.bookTitleList.splice(index, 1)
      }
    },
    addBookTitle() {
      if (this.bookTitleList.length < 5) {
        this.bookTitleList.push({ value: '' })
      }
    },
    beforeCoverUpload(file) {
      // 处理封面上传逻辑
    },
    cancelNewBook() {
      // 取消新书创建逻辑
    },
    saveNewBook() {
      // 保存新书创建逻辑
    },
    handleRemove(file) {
      // 处理图片移除逻辑
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleDownload(file) {
      // 处理下载逻辑
    },
    removeNewBookTitle(index) {
      if (this.newBookTitles.length > 1) {  // 确保至少保留一个书名
        this.newBookTitles.splice(index, 1)
      }
    },
    addNewBookTitle() {
      if (this.newBookTitles.length < 5) {
        this.newBookTitles.push({ value: '' })
      }
    },
    handleNewBookRemove(file) {
      // 处理新书图片移除逻辑
    },
    saveBookInfo() {
      // 验证必填项
      if (!this.bookTitleList[0].value.trim()) {
        this.$message.error('请输入主书名')
        return
      }
      if (!this.bookStory.trim()) {
        this.$message.error('请输入故事大纲')
        return
      }

      // TODO: 调用保存接口
      this.$message.success('保存成功')
    },
    handleLibrarySearch() {
      // 实现搜索逻辑
    },
    switchToBook(book) {
      // 实现切换到指定书籍的逻辑
    },
    saveContent() {
      // 实现保存内容逻辑
    },
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

.form-actions {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
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

/* 修改统计信息显示样式 */
.content-stats {
  width: 140px;
  flex-shrink: 0;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 16px;
  background: #fff;
  display: flex;
  flex-direction: column;
  height: 100%;  /* 确保占满整个高度 */
}

.stat-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

/* 添加保存按钮样式 */
.save-content-btn {
  margin-top: auto;  /* 将按钮推到底部 */
  width: 100%;      /* 按钮宽度占满 */
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

.book-titles {
  margin-bottom: 6px;  /* 书名输入框列表的底部间距 */
}

.title-item {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 16px;  /* 每个书名输入框的底部间距 */
}

.title-item:last-child {
  margin-bottom: 0;  /* 最后一个输入框不需要底部间距 */
}

.book-section .el-textarea {
  margin-bottom: 16px;  /* 文本域的底部间距 */
}

.cover-uploader {
  margin-bottom: 16px;  /* 封面上传控件的底部间距 */
}

/* 最后一个区块的最后一个元素不需要底部间距 */
.book-section:last-child > *:last-child {
  margin-bottom: 0;
}

.book-section {
  margin-bottom: 20px;
}

.section-title {
  font-size: 16px;
  font-weight: 500;
  font-family: 'PingFang SC', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif;
  color: #606266;
  margin-bottom: 8px;  /* 标题和控件的间距 */
  user-select: none;   /* 防止文本选择 */
  cursor: default;     /* 使用默认光标 */
}
.section-title.book-title {
  margin-bottom: 12px;
}

.section-title.required::after {
  content: '*';
  color: #f56c6c;
  margin-left: 4px;
}

.book-intro {
  margin-top: 5px;
  font-size: 12px;
  color: #606266;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;  /* 限制显示两行 */
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.5;  /* 设置行高 */
  max-height: 36px;  /* 2行文字的最大高度：12px * 1.5 * 2 = 36px */
}

.book-story {
  margin-bottom: 20px;
}

.book-info {
  padding: 20px;
}

.library-content {
  padding: 20px;
}

.library-book-item {
  margin-bottom: 10px;
}

.book-main-title {
  font-size: 16px;
  font-weight: 500;
  color: #303133;
}

.book-sub-titles {
  margin-top: 5px;
  font-size: 12px;
  color: #909399;
}

.new-book-content {
  padding: 20px;
}

.form-actions {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

/* 修改输入框和文本域的样式 */
.title-item .el-input {
  user-select: text;  /* 允许文本选择 */
}

.title-item .el-input__inner {
  user-select: text;  /* 允许文本选择 */
  cursor: text;       /* 显示文本光标 */
}

.title-item .el-input__wrapper {
  user-select: none;  /* 防止外层被选中 */
  cursor: default;    /* 使用默认光标 */
}

.input-wrapper {
  position: relative;
  user-select: none;  /* 防止外层被选中 */
  cursor: default;    /* 使用默认光标 */
}

.input-wrapper .el-textarea {
  user-select: text;  /* 允许文本选择 */
}

.input-wrapper .el-textarea__inner {
  user-select: text;  /* 允许文本选择 */
  cursor: text;       /* 显示文本光标 */
}

/* 确保文字计数器不可选 */
.word-count {
  user-select: none;
  cursor: default;
}

/* 确保标题不可选 */
.section-title {
  user-select: none;
  cursor: default;
}

/* 修改表单操作区样式 */
.form-actions {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

/* 确保最后一个 book-section 和 form-actions 之间有正确的间距 */
.book-section:last-of-type {
  margin-bottom: 0;
}

/* 修改书库区域样式 */
.library-search {
  padding: 16px;
  border-bottom: 1px solid #e4e7ed;
}

.library-list {
  padding: 16px;
}

.library-book-item {
  padding: 12px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  margin-bottom: 8px;
  background: #fff;
  cursor: pointer;
  transition: all 0.2s ease;  /* 添加过渡效果 */
}

.library-book-item:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.book-item-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.book-name {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
}

.chapter-count {
  font-size: 12px;
  color: #909399;
}

.book-intro {
  margin-top: 5px;
  font-size: 12px;
  color: #606266;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;  /* 限制显示两行 */
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.5;  /* 设置行高 */
  max-height: 36px;  /* 2行文字的最大高度：12px * 1.5 * 2 = 36px */
}
</style> 