<template>
  <div class="material-detail">
    <div class="container">
      <div v-if="loading" class="loading-state">
        <div class="spinner"></div>
        <p>加载中...</p>
      </div>

      <div v-else-if="material" class="detail-content">
        <!-- 返回按钮 -->
        <button class="back-btn" @click="goBack">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="19" y1="12" x2="5" y2="12"></line>
            <polyline points="12 19 5 12 12 5"></polyline>
          </svg>
          返回
        </button>

        <!-- 视频预览区域 -->
        <div class="preview-section">
          <div class="video-wrapper">
            <img :src="material.thumbnail" :alt="material.title" />
            <div class="play-overlay">
              <button class="play-btn">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor" stroke="none">
                  <polygon points="5 3 19 12 5 21 5 3"></polygon>
                </svg>
              </button>
            </div>
          </div>
        </div>

        <!-- 素材信息 -->
        <div class="info-section">
          <div class="info-header">
            <h1 class="material-title">{{ material.title }}</h1>
            <div class="material-badges">
              <span v-if="material.free" class="badge free">免费</span>
              <span class="badge resolution">{{ material.resolution }}</span>
            </div>
          </div>

          <!-- 素材详情 -->
          <div class="material-details">
            <div class="detail-item">
              <span class="detail-label">分类</span>
              <span class="detail-value">{{ material.category }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">分辨率</span>
              <span class="detail-value">{{ material.resolution }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">帧率</span>
              <span class="detail-value">{{ material.fps }} fps</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">格式</span>
              <span class="detail-value">{{ material.format }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">时长</span>
              <span class="detail-value">{{ material.duration }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">上传时间</span>
              <span class="detail-value">{{ material.createdAt }}</span>
            </div>
          </div>

          <!-- 标签 -->
          <div class="tags-section">
            <h3 class="tags-title">标签</h3>
            <div class="tags-list">
              <span v-for="tag in material.tags" :key="tag" class="tag">{{ tag }}</span>
            </div>
          </div>

          <!-- 操作按钮 -->
          <div class="action-buttons">
            <button class="action-btn primary">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                <polyline points="7 10 12 15 17 10"></polyline>
                <line x1="12" y1="15" x2="12" y2="3"></line>
              </svg>
              下载素材
            </button>
            <button class="action-btn secondary" @click="handleCollect">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
              </svg>
              收藏
            </button>
            <button class="action-btn secondary">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="18" cy="5" r="3"></circle>
                <circle cx="6" cy="12" r="3"></circle>
                <circle cx="18" cy="19" r="3"></circle>
                <line x1="8.59" y1="13.51" x2="15.42" y2="17.49"></line>
                <line x1="15.41" y1="6.51" x2="8.59" y2="10.49"></line>
              </svg>
              分享
            </button>
          </div>

          <!-- 使用说明 -->
          <div class="usage-info">
            <h3>使用说明</h3>
            <ul>
              <li>个人练习：免费用于个人学习作品</li>
              <li>个人商用：适用于个人商业项目</li>
              <li>企业商用：适用于企业商业项目</li>
            </ul>
          </div>
        </div>
      </div>

      <div v-else class="empty-state">
        <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
          <circle cx="12" cy="12" r="10"></circle>
          <line x1="12" y1="8" x2="12" y2="12"></line>
          <line x1="12" y1="16" x2="12.01" y2="16"></line>
        </svg>
        <p>素材不存在</p>
        <button class="back-btn" @click="goBack">返回素材库</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const material = ref(null)

// 模拟素材数据（后期替换为API调用）
const mockMaterial = {
  id: 1,
  title: '加州圣莫尼卡海滩',
  thumbnail: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=1200&h=800&fit=crop',
  category: '自然风景',
  tags: ['海滩', '广角', '4K', '日落', '自然'],
  resolution: '4K',
  fps: '29.97',
  format: 'MP4',
  duration: '10s',
  free: true,
  createdAt: '2025-01-06',
  description: '美丽的圣莫尼卡海滩日落景色，适合用于各类影视作品。'
}

const loadMaterial = async () => {
  loading.value = true

  // TODO: 替换为实际的API调用
  // const response = await api.getMaterial(route.params.id)
  // material.value = response.data

  // 模拟API延迟
  await new Promise(resolve => setTimeout(resolve, 500))
  material.value = mockMaterial

  loading.value = false
}

const goBack = () => {
  router.push('/material-library')
}

const handleCollect = () => {
  // TODO: 调用收藏API
  console.log('收藏素材:', material.value.id)
}

onMounted(() => {
  loadMaterial()
})
</script>

<style lang="scss" scoped>
.material-detail {
  padding: 32px 0 64px;
}

.loading-state,
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 120px 0;
  color: var(--text-secondary);
}

.spinner {
  width: 48px;
  height: 48px;
  border: 4px solid var(--border-color);
  border-top-color: var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 8px;
  color: var(--text-primary);
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 24px;
  transition: all 0.3s ease;

  &:hover {
    border-color: var(--primary-color);
    color: var(--primary-color);
  }
}

.detail-content {
  max-width: 1200px;
  margin: 0 auto;
}

.preview-section {
  margin-bottom: 32px;
}

.video-wrapper {
  position: relative;
  aspect-ratio: 16 / 9;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 12px;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.play-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.3s ease;

  &:hover {
    background: rgba(0, 0, 0, 0.5);
  }
}

.play-btn {
  width: 80px;
  height: 80px;
  background: var(--primary-color);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  transition: all 0.3s ease;

  &:hover {
    background: var(--primary-hover);
    transform: scale(1.1);
  }
}

.info-section {
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 12px;
  padding: 32px;
}

.info-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 24px;
  gap: 16px;
}

.material-title {
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
}

.material-badges {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
}

.badge {
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 13px;
  font-weight: 600;

  &.free {
    background: #10b981;
    color: white;
  }

  &.resolution {
    background: var(--bg-hover);
    color: var(--text-secondary);
  }
}

.material-details {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 32px;
  padding-bottom: 32px;
  border-bottom: 1px solid var(--border-color);
}

.detail-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.detail-label {
  font-size: 13px;
  color: var(--text-secondary);
}

.detail-value {
  font-size: 15px;
  color: var(--text-primary);
  font-weight: 500;
}

.tags-section {
  margin-bottom: 32px;
}

.tags-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 12px;
  color: var(--text-primary);
}

.tags-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tag {
  padding: 6px 14px;
  background: var(--bg-hover);
  border: 1px solid var(--border-color);
  border-radius: 6px;
  color: var(--text-secondary);
  font-size: 13px;
  transition: all 0.3s ease;

  &:hover {
    border-color: var(--primary-color);
    color: var(--primary-color);
  }
}

.action-buttons {
  display: flex;
  gap: 12px;
  margin-bottom: 32px;
  flex-wrap: wrap;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 600;
  transition: all 0.3s ease;

  &.primary {
    background: var(--primary-color);
    color: white;
    border: none;

    &:hover {
      background: var(--primary-hover);
      transform: translateY(-2px);
    }
  }

  &.secondary {
    background: transparent;
    color: var(--text-primary);
    border: 1px solid var(--border-color);

    &:hover {
      border-color: var(--primary-color);
      color: var(--primary-color);
    }
  }
}

.usage-info {
  padding: 20px;
  background: var(--bg-hover);
  border-radius: 8px;

  h3 {
    font-size: 16px;
    font-weight: 600;
    margin-bottom: 12px;
    color: var(--text-primary);
  }

  ul {
    list-style: none;
  }

  li {
    padding: 8px 0;
    color: var(--text-secondary);
    font-size: 14px;
    border-bottom: 1px solid var(--border-color);

    &:last-child {
      border-bottom: none;
    }
  }
}

// 响应式
@media (max-width: 768px) {
  .material-detail {
    padding: 24px 0 48px;
  }

  .info-header {
    flex-direction: column;
  }

  .material-title {
    font-size: 24px;
  }

  .info-section {
    padding: 24px 20px;
  }

  .material-details {
    grid-template-columns: 1fr 1fr;
    gap: 16px;
  }

  .action-buttons {
    flex-direction: column;

    .action-btn {
      width: 100%;
      justify-content: center;
    }
  }
}
</style>
