<template>
  <div class="material-library">
    <div class="container">
      <!-- 页面头部 -->
      <div class="page-header">
        <h1 class="page-title">素材库</h1>
        <p class="page-subtitle">发现高质量影视素材，激发无限创意</p>
      </div>

      <!-- 筛选栏 -->
      <div class="filters-bar">
        <!-- 分类筛选 -->
        <div class="filter-group">
          <label class="filter-label">分类:</label>
          <select v-model="filters.category" class="filter-select" @change="handleFilterChange">
            <option value="">全部</option>
            <option v-for="cat in categories" :key="cat.id" :value="cat.id">
              {{ cat.name }}
            </option>
          </select>
        </div>

        <!-- 分辨率筛选 -->
        <div class="filter-group">
          <label class="filter-label">分辨率:</label>
          <select v-model="filters.resolution" class="filter-select" @change="handleFilterChange">
            <option value="">全部</option>
            <option value="4K">4K</option>
            <option value="1080P">1080P</option>
            <option value="720P">720P</option>
          </select>
        </div>

        <!-- 价格筛选 -->
        <div class="filter-group">
          <label class="filter-label">价格:</label>
          <select v-model="filters.price" class="filter-select" @change="handleFilterChange">
            <option value="">全部</option>
            <option value="free">免费</option>
            <option value="paid">付费</option>
          </select>
        </div>

        <!-- 排序 -->
        <div class="filter-group">
          <label class="filter-label">排序:</label>
          <select v-model="filters.sort" class="filter-select" @change="handleFilterChange">
            <option value="latest">最新</option>
            <option value="popular">最热</option>
            <option value="duration">时长</option>
          </select>
        </div>

        <!-- 搜索结果显示 -->
        <div v-if="searchQuery" class="search-result">
          搜索 "<span>{{ searchQuery }}</span>" 的结果
          <button class="clear-search" @click="clearSearch">×</button>
        </div>
      </div>

      <!-- 素材列表 -->
      <div v-if="loading" class="loading-state">
        <div class="spinner"></div>
        <p>加载中...</p>
      </div>

      <div v-else-if="materials.length === 0" class="empty-state">
        <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
          <circle cx="11" cy="11" r="8"></circle>
          <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
        </svg>
        <p>暂无素材</p>
        <button class="reset-btn" @click="resetFilters">重置筛选</button>
      </div>

      <div v-else class="materials-grid">
        <MaterialCard
          v-for="material in materials"
          :key="material.id"
          :material="material"
        />
      </div>

      <!-- 分页 -->
      <div v-if="totalPages > 1" class="pagination">
        <button
          class="page-btn"
          :disabled="currentPage === 1"
          @click="handlePageChange(currentPage - 1)"
        >
          上一页
        </button>
        <div class="page-numbers">
          <button
            v-for="page in displayedPages"
            :key="page"
            class="page-number"
            :class="{ active: page === currentPage }"
            @click="handlePageChange(page)"
          >
            {{ page }}
          </button>
        </div>
        <button
          class="page-btn"
          :disabled="currentPage === totalPages"
          @click="handlePageChange(currentPage + 1)"
        >
          下一页
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import MaterialCard from '../components/MaterialCard.vue'

const route = useRoute()
const router = useRouter()

// 状态
const loading = ref(false)
const currentPage = ref(1)
const totalPages = ref(5)
const searchQuery = ref('')

// 筛选条件
const filters = ref({
  category: '',
  resolution: '',
  price: '',
  sort: 'latest'
})

// 分类数据（可从API获取）
const categories = ref([
  { id: 1, name: '自然风景' },
  { id: 2, name: '城市建筑' },
  { id: 3, name: '人物肖像' },
  { id: 4, name: '科技数码' }
])

// 素材数据（占位数据，后期替换为API调用）
const materials = ref([
  {
    id: 1,
    title: '加州圣莫尼卡海滩',
    thumbnail: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&h=600&fit=crop',
    category: '自然风景',
    tags: ['海滩', '广角', '4K'],
    resolution: '4K',
    fps: '29.97',
    format: 'MP4',
    duration: '10s',
    free: true,
    createdAt: '2025-01-06'
  },
  {
    id: 2,
    title: '城市夜景延时',
    thumbnail: 'https://images.unsplash.com/photo-1519501025264-65ba15a82390?w=800&h=600&fit=crop',
    category: '城市',
    tags: ['夜景', '延时', '城市'],
    resolution: '4K',
    fps: '25',
    format: 'MP4',
    duration: '15s',
    free: true,
    createdAt: '2025-01-05'
  },
  {
    id: 3,
    title: '阿拉斯加雪山',
    thumbnail: 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800&h=600&fit=crop',
    category: '自然风景',
    tags: ['雪山', '全景', '航拍'],
    resolution: '4K',
    fps: '30',
    format: 'MP4',
    duration: '8s',
    free: false,
    createdAt: '2025-01-04'
  },
  {
    id: 4,
    title: '森林晨雾',
    thumbnail: 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&h=600&fit=crop',
    category: '自然风景',
    tags: ['森林', '晨雾', '特写'],
    resolution: '4K',
    fps: '24',
    format: 'MP4',
    duration: '12s',
    free: true,
    createdAt: '2025-01-03'
  },
  {
    id: 5,
    title: '莫斯科红场',
    thumbnail: 'https://images.unsplash.com/photo-1513326738677-b964603b136d?w=800&h=600&fit=crop',
    category: '城市',
    tags: ['城市', '人文', '近景'],
    resolution: '4K',
    fps: '25',
    format: 'MP4',
    duration: '10s',
    free: true,
    createdAt: '2025-01-02'
  },
  {
    id: 6,
    title: '科技办公室',
    thumbnail: 'https://images.unsplash.com/photo-1497366216548-37526070297c?w=800&h=600&fit=crop',
    category: '科技数码',
    tags: ['科技', '办公室', '广角'],
    resolution: '4K',
    fps: '30',
    format: 'MP4',
    duration: '8s',
    free: false,
    createdAt: '2025-01-01'
  },
  {
    id: 7,
    title: '日落海滩',
    thumbnail: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&h=600&fit=crop&sat=-100',
    category: '自然风景',
    tags: ['日落', '海滩', '广角'],
    resolution: '4K',
    fps: '24',
    format: 'MP4',
    duration: '12s',
    free: true,
    createdAt: '2024-12-31'
  },
  {
    id: 8,
    title: '城市天际线',
    thumbnail: 'https://images.unsplash.com/photo-1480714378408-67cf0d13bc1b?w=800&h=600&fit=crop',
    category: '城市',
    tags: ['城市', '天际线', '全景'],
    resolution: '4K',
    fps: '25',
    format: 'MP4',
    duration: '15s',
    free: true,
    createdAt: '2024-12-30'
  }
])

// 计算显示的页码
const displayedPages = computed(() => {
  const pages = []
  const maxVisible = 5
  let start = Math.max(1, currentPage.value - Math.floor(maxVisible / 2))
  let end = Math.min(totalPages.value, start + maxVisible - 1)

  if (end - start < maxVisible - 1) {
    start = Math.max(1, end - maxVisible + 1)
  }

  for (let i = start; i <= end; i++) {
    pages.push(i)
  }

  return pages
})

// 监听路由参数变化
watch(() => route.query, (newQuery) => {
  if (newQuery.search) {
    searchQuery.value = newQuery.search
  }
  if (newQuery.category) {
    filters.value.category = newQuery.category
  }
  // 重新加载数据
  loadMaterials()
}, { immediate: true })

// 加载素材数据
const loadMaterials = async () => {
  loading.value = true

  // TODO: 替换为实际的API调用
  // const response = await api.getMaterials({
  //   page: currentPage.value,
  //   ...filters.value,
  //   search: searchQuery.value
  // })

  // 模拟API延迟
  await new Promise(resolve => setTimeout(resolve, 500))

  loading.value = false
}

const handleFilterChange = () => {
  currentPage.value = 1
  loadMaterials()
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadMaterials()
  // 滚动到顶部
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

const clearSearch = () => {
  searchQuery.value = ''
  router.push({ path: '/material-library' })
}

const resetFilters = () => {
  filters.value = {
    category: '',
    resolution: '',
    price: '',
    sort: 'latest'
  }
  searchQuery.value = ''
  currentPage.value = 1
  loadMaterials()
}

onMounted(() => {
  // 初始化加载数据
  if (route.query.search) {
    searchQuery.value = route.query.search
  }
  loadMaterials()
})
</script>

<style lang="scss" scoped>
.material-library {
  padding: 32px 0 64px;
}

.page-header {
  text-align: center;
  margin-bottom: 48px;
}

.page-title {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 8px;
}

.page-subtitle {
  font-size: 16px;
  color: var(--text-secondary);
}

// 筛选栏
.filters-bar {
  display: flex;
  flex-wrap: wrap;
  gap: 24px;
  padding: 24px;
  background: #f9fafb;
  border: 1px solid var(--border-color);
  border-radius: 12px;
  margin-bottom: 32px;
  align-items: center;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 8px;
}

.filter-label {
  font-size: 14px;
  color: var(--text-secondary);
  white-space: nowrap;
}

.filter-select {
  padding: 8px 16px;
  background: var(--bg-dark);
  border: 1px solid var(--border-color);
  border-radius: 6px;
  color: var(--text-primary);
  font-size: 14px;
  outline: none;
  cursor: pointer;
  transition: border-color 0.3s ease;

  &:focus {
    border-color: var(--primary-color);
  }

  option {
    background: var(--bg-card);
  }
}

.search-result {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: var(--primary-color);
  color: white;
  border-radius: 6px;
  font-size: 14px;
  margin-left: auto;

  span {
    font-weight: 600;
  }
}

.clear-search {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  line-height: 1;
  transition: background 0.3s ease;

  &:hover {
    background: rgba(255, 255, 255, 0.3);
  }
}

.materials-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
  margin-bottom: 48px;
}

// 加载和空状态
.loading-state,
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 0;
  color: var(--text-secondary);
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--border-color);
  border-top-color: var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.empty-state svg {
  margin-bottom: 16px;
  opacity: 0.5;
}

.reset-btn {
  margin-top: 16px;
  padding: 10px 24px;
  background: var(--primary-color);
  color: white;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  transition: background 0.3s ease;

  &:hover {
    background: var(--primary-hover);
  }
}

// 分页
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
}

.page-btn {
  padding: 8px 16px;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 6px;
  color: var(--text-primary);
  font-size: 14px;
  transition: all 0.3s ease;

  &:hover:not(:disabled) {
    border-color: var(--primary-color);
    color: var(--primary-color);
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
}

.page-numbers {
  display: flex;
  gap: 8px;
}

.page-number {
  width: 36px;
  height: 36px;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 6px;
  color: var(--text-primary);
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;

  &:hover {
    border-color: var(--primary-color);
    color: var(--primary-color);
  }

  &.active {
    background: var(--primary-color);
    border-color: var(--primary-color);
    color: white;
  }
}

// 响应式
@media (max-width: 768px) {
  .material-library {
    padding: 24px 0 48px;
  }

  .page-title {
    font-size: 28px;
  }

  .page-subtitle {
    font-size: 14px;
  }

  .filters-bar {
    padding: 16px;
    gap: 16px;
  }

  .filter-group {
    flex: 1 1 calc(50% - 8px);
    min-width: 0;
  }

  .filter-select {
    flex: 1;
    min-width: 0;
  }

  .search-result {
    width: 100%;
    margin-left: 0;
    margin-top: 8px;
  }

  .materials-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }
}
</style>
