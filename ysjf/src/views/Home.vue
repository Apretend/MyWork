<template>
  <div class="home">
    <!-- Hero 区域 -->
    <section class="hero">
      <div class="container">
        <div class="hero-content">
          <h1 class="hero-title">探索无限创意</h1>
          <p class="hero-subtitle">高质量影视素材，为你的作品注入生命力</p>

          <!-- 搜索框 -->
          <div class="hero-search">
            <input
              v-model="searchQuery"
              type="text"
              placeholder="搜索你想要的素材..."
              @keyup.enter="handleSearch"
            />
            <button class="search-btn" @click="handleSearch">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="11" cy="11" r="8"></circle>
                <path d="m21 21-4.35-4.35"></path>
              </svg>
              搜索
            </button>
          </div>

          <!-- 热门标签 -->
          <div class="hero-tags">
            <span class="tag-label">热门搜索:</span>
            <span
              v-for="tag in hotTags"
              :key="tag"
              class="tag"
              @click="handleTagClick(tag)"
            >
              {{ tag }}
            </span>
          </div>
        </div>

        <!-- 背景装饰 -->
        <div class="hero-bg">
          <div class="bg-circle circle-1"></div>
          <div class="bg-circle circle-2"></div>
          <div class="bg-circle circle-3"></div>
        </div>
      </div>
    </section>

    <!-- 精选素材 -->
    <section class="featured-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">精选素材</h2>
          <router-link to="/material-library" class="view-all">
            查看全部
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M5 12h14M12 5l7 7-7 7"></path>
            </svg>
          </router-link>
        </div>

        <div class="materials-grid">
          <MaterialCard
            v-for="material in featuredMaterials"
            :key="material.id"
            :material="material"
          />
        </div>
      </div>
    </section>

    <!-- 分类展示 -->
    <section class="categories-section">
      <div class="container">
        <h2 class="section-title">素材分类</h2>
        <div class="categories-grid">
          <div
            v-for="category in categories"
            :key="category.id"
            class="category-card"
            @click="handleCategoryClick(category)"
          >
            <div class="category-icon">{{ category.icon }}</div>
            <h3 class="category-name">{{ category.name }}</h3>
            <p class="category-desc">{{ category.description }}</p>
            <span class="category-count">{{ category.count }} 个素材</span>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import MaterialCard from '../components/MaterialCard.vue'

const router = useRouter()
const searchQuery = ref('')

// 热门标签
const hotTags = ref([
  '4K素材',
  '城市夜景',
  '自然风光',
  '延时摄影',
  '慢动作',
  '航拍'
])

// 精选素材数据（后期可通过API替换）
const featuredMaterials = ref([
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
  }
])

// 分类数据
const categories = ref([
  {
    id: 1,
    name: '自然风景',
    icon: '🏔️',
    description: '壮丽的自然风光素材',
    count: 156
  },
  {
    id: 2,
    name: '城市建筑',
    icon: '🏙️',
    description: '现代城市与建筑影像',
    count: 124
  },
  {
    id: 3,
    name: '人物肖像',
    icon: '👤',
    description: '专业人物拍摄素材',
    count: 89
  },
  {
    id: 4,
    name: '科技数码',
    icon: '💻',
    description: '科技感十足的画面',
    count: 67
  },
  {
    id: 5,
    name: '美食特写',
    icon: '🍜',
    description: '诱人的美食拍摄素材',
    count: 45
  },
  {
    id: 6,
    name: '动物世界',
    icon: '🦁',
    description: '野生动物精彩瞬间',
    count: 78
  }
])

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({
      path: '/material-library',
      query: { search: searchQuery.value }
    })
  }
}

const handleTagClick = (tag) => {
  router.push({
    path: '/material-library',
    query: { search: tag }
  })
}

const handleCategoryClick = (category) => {
  router.push({
    path: '/material-library',
    query: { category: category.id }
  })
}
</script>

<style lang="scss" scoped>
.home {
  padding-top: 64px;
}

// Hero 区域
.hero {
  position: relative;
  min-height: 600px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 80px 0;
  overflow: hidden;
  background: linear-gradient(135deg, #f5f7fa 0%, #e8ecf1 100%);
}

.hero-content {
  position: relative;
  z-index: 2;
  text-align: center;
  max-width: 800px;
  width: 100%;
}

.hero-title {
  font-size: 56px;
  font-weight: 700;
  margin-bottom: 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  animation: fadeInUp 0.8s ease;
}

.hero-subtitle {
  font-size: 20px;
  color: var(--text-secondary);
  margin-bottom: 40px;
  animation: fadeInUp 0.8s ease 0.2s both;
}

.hero-search {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
  animation: fadeInUp 0.8s ease 0.4s both;

  input {
    flex: 1;
    padding: 16px 24px;
    background: var(--bg-card);
    border: 1px solid var(--border-color);
    border-radius: 12px;
    color: var(--text-primary);
    font-size: 16px;
    outline: none;
    transition: all 0.3s ease;

    &:focus {
      border-color: var(--primary-color);
      box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
    }

    &::placeholder {
      color: var(--text-secondary);
    }
  }
}

.search-btn {
  padding: 16px 32px;
  background: var(--primary-color);
  color: white;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s ease;

  &:hover {
    background: var(--primary-hover);
    transform: translateY(-2px);
  }
}

.hero-tags {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  flex-wrap: wrap;
  animation: fadeInUp 0.8s ease 0.6s both;
}

.tag-label {
  color: var(--text-secondary);
  font-size: 14px;
}

.tag {
  padding: 6px 16px;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 20px;
  color: var(--text-secondary);
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    border-color: var(--primary-color);
    color: var(--primary-color);
    transform: translateY(-2px);
  }
}

// 背景装饰
.hero-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 1;
  overflow: hidden;
}

.bg-circle {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.3;
  animation: float 20s infinite ease-in-out;

  &.circle-1 {
    width: 400px;
    height: 400px;
    background: linear-gradient(135deg, #e0e7ff 0%, #c7d2fe 100%);
    top: -100px;
    right: -100px;
    animation-delay: 0s;
  }

  &.circle-2 {
    width: 300px;
    height: 300px;
    background: linear-gradient(135deg, #fce7f3 0%, #fbcfe8 100%);
    bottom: -50px;
    left: -50px;
    animation-delay: 5s;
  }

  &.circle-3 {
    width: 200px;
    height: 200px;
    background: linear-gradient(135deg, #ddd6fe 0%, #c4b5fd 100%);
    top: 50%;
    left: 50%;
    animation-delay: 10s;
  }
}

// 动画
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes float {
  0%, 100% {
    transform: translate(0, 0);
  }
  50% {
    transform: translate(30px, -30px);
  }
}

// 精选素材区域
.featured-section {
  padding: 80px 0;
  background: #ffffff;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 32px;
}

.section-title {
  font-size: 32px;
  font-weight: 700;
  color: var(--text-primary);
}

.view-all {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--primary-color);
  font-size: 15px;
  font-weight: 500;
  transition: gap 0.3s ease;

  &:hover {
    gap: 12px;
  }
}

.materials-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

// 分类区域
.categories-section {
  padding: 80px 0;
  background: #f9fafb;
}

.categories-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 24px;
}

.category-card {
  background: var(--bg-dark);
  border: 1px solid var(--border-color);
  border-radius: 16px;
  padding: 32px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    border-color: var(--primary-color);
    transform: translateY(-4px);
    box-shadow: var(--shadow-lg);
  }
}

.category-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.category-name {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.category-desc {
  color: var(--text-secondary);
  font-size: 14px;
  margin-bottom: 16px;
}

.category-count {
  display: inline-block;
  padding: 4px 12px;
  background: var(--bg-hover);
  border-radius: 12px;
  color: var(--text-secondary);
  font-size: 12px;
}

// 响应式
@media (max-width: 768px) {
  .hero {
    min-height: 500px;
    padding: 60px 0;
  }

  .hero-title {
    font-size: 36px;
  }

  .hero-subtitle {
    font-size: 16px;
    margin-bottom: 32px;
  }

  .hero-search {
    flex-direction: column;

    input {
      width: 100%;
    }

    .search-btn {
      width: 100%;
      justify-content: center;
    }
  }

  .section-title {
    font-size: 24px;
  }

  .materials-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .categories-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }

  .category-card {
    padding: 24px 16px;
  }

  .featured-section,
  .categories-section {
    padding: 48px 0;
  }
}
</style>
