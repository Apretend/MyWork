<template>
  <div class="material-card" @click="handleClick">
    <!-- 缩略图 -->
    <div class="card-thumbnail">
      <img :src="material.thumbnail" :alt="material.title" />
      <div class="thumbnail-overlay">
        <button class="preview-btn">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polygon points="5 3 19 12 5 21 5 3"></polygon>
          </svg>
          预览
        </button>
      </div>

      <!-- 免费标识 -->
      <div v-if="material.free" class="free-badge">免费</div>

      <!-- 分辨率标识 -->
      <div class="resolution-badge">{{ material.resolution }}</div>
    </div>

    <!-- 卡片内容 -->
    <div class="card-content">
      <h3 class="material-title">{{ material.title }}</h3>

      <div class="material-meta">
        <span class="meta-item">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="2" y="7" width="20" height="15" rx="2" ry="2"></rect>
            <polyline points="17 2 12 7 7 2"></polyline>
          </svg>
          {{ material.category }}
        </span>
        <span class="meta-item">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="10"></circle>
            <polyline points="12 6 12 12 16 14"></polyline>
          </svg>
          {{ material.duration }}
        </span>
      </div>

      <!-- 标签 -->
      <div class="material-tags">
        <span
          v-for="tag in material.tags.slice(0, 3)"
          :key="tag"
          class="tag"
        >
          {{ tag }}
        </span>
      </div>

      <!-- 底部信息 -->
      <div class="card-footer">
        <div class="format-info">
          <span>{{ material.format }}</span>
          <span>{{ material.fps }}fps</span>
        </div>
        <button class="collect-btn" @click.stop="handleCollect">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const props = defineProps({
  material: {
    type: Object,
    required: true
  }
})

const router = useRouter()

const handleClick = () => {
  router.push(`/material/${props.material.id}`)
}

const handleCollect = () => {
  // 预留收藏功能
  console.log('收藏素材:', props.material.id)
  // TODO: 调用收藏API
}
</script>

<style lang="scss" scoped>
.material-card {
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    border-color: var(--primary-color);
    transform: translateY(-4px);
    box-shadow: var(--shadow-lg);

    .thumbnail-overlay {
      opacity: 1;
    }
  }
}

.card-thumbnail {
  position: relative;
  aspect-ratio: 16 / 10;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
  }

  &:hover img {
    transform: scale(1.05);
  }
}

.thumbnail-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.preview-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 24px;
  background: var(--primary-color);
  color: white;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;

  &:hover {
    background: var(--primary-hover);
    transform: scale(1.05);
  }
}

.free-badge {
  position: absolute;
  top: 12px;
  left: 12px;
  padding: 4px 12px;
  background: #10b981;
  color: white;
  font-size: 12px;
  font-weight: 600;
  border-radius: 4px;
}

.resolution-badge {
  position: absolute;
  bottom: 12px;
  right: 12px;
  padding: 4px 8px;
  background: rgba(0, 0, 0, 0.8);
  color: white;
  font-size: 11px;
  font-weight: 500;
  border-radius: 4px;
}

.card-content {
  padding: 16px;
}

.material-title {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.material-meta {
  display: flex;
  gap: 16px;
  margin-bottom: 12px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 4px;
  color: var(--text-secondary);
  font-size: 12px;

  svg {
    flex-shrink: 0;
  }
}

.material-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 12px;
}

.tag {
  padding: 4px 10px;
  background: var(--bg-hover);
  border-radius: 4px;
  color: var(--text-secondary);
  font-size: 12px;
  transition: all 0.3s ease;

  &:hover {
    background: var(--primary-color);
    color: white;
  }
}

.card-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 12px;
  border-top: 1px solid var(--border-color);
}

.format-info {
  display: flex;
  gap: 8px;
  font-size: 12px;
  color: var(--text-secondary);
}

.collect-btn {
  width: 32px;
  height: 32px;
  border-radius: 6px;
  background: transparent;
  color: var(--text-secondary);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;

  &:hover {
    background: var(--bg-hover);
    color: var(--primary-color);
  }
}

// 响应式
@media (max-width: 768px) {
  .card-content {
    padding: 12px;
  }

  .material-title {
    font-size: 15px;
  }
}
</style>
