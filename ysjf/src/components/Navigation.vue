<template>
  <nav class="navigation">
    <div class="container">
      <div class="nav-content">
        <!-- Logo -->
        <router-link to="/" class="logo">
          <img src="/logo.svg" alt="影视飓风" />
          <span>影视飓风</span>
        </router-link>

        <!-- 导航链接 -->
        <div class="nav-links">
          <router-link to="/" class="nav-link">首页</router-link>
          <router-link to="/material-library" class="nav-link">素材库</router-link>
          <router-link to="/about" class="nav-link">关于我们</router-link>
        </div>

        <!-- 右侧操作区 -->
        <div class="nav-actions">
          <!-- 搜索按钮 -->
          <button class="icon-btn" @click="showSearch = true">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="11" cy="11" r="8"></circle>
              <path d="m21 21-4.35-4.35"></path>
            </svg>
          </button>

          <!-- 登录按钮 -->
          <button class="login-btn">登录</button>
        </div>
      </div>
    </div>

    <!-- 搜索弹窗 -->
    <div v-if="showSearch" class="search-modal" @click.self="showSearch = false">
      <div class="search-content">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="搜索素材、标签、分类..."
          @keyup.enter="handleSearch"
          ref="searchInput"
        />
        <button class="search-btn" @click="handleSearch">搜索</button>
        <button class="close-btn" @click="showSearch = false">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, watch, nextTick } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const showSearch = ref(false)
const searchQuery = ref('')
const searchInput = ref(null)

// 监听搜索弹窗显示，自动聚焦输入框
watch(showSearch, (newVal) => {
  if (newVal) {
    nextTick(() => {
      searchInput.value?.focus()
    })
  }
})

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    // 跳转到素材库页面，并传递搜索参数
    router.push({
      path: '/material-library',
      query: { search: searchQuery.value }
    })
    showSearch.value = false
  }
}
</script>

<style lang="scss" scoped>
.navigation {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid var(--border-color);

  .nav-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 64px;
  }
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);

  img {
    width: 32px;
    height: 32px;
  }

  &:hover {
    color: var(--primary-color);
  }
}

.nav-links {
  display: flex;
  gap: 32px;

  .nav-link {
    color: var(--text-secondary);
    font-size: 15px;
    transition: color 0.3s ease;
    position: relative;

    &:hover,
    &.router-link-active {
      color: var(--text-primary);
    }

    &.router-link-active::after {
      content: '';
      position: absolute;
      bottom: -22px;
      left: 0;
      right: 0;
      height: 2px;
      background: var(--primary-color);
    }
  }
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.icon-btn {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  background: transparent;
  color: var(--text-secondary);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;

  &:hover {
    background: var(--bg-hover);
    color: var(--text-primary);
  }
}

.login-btn {
  padding: 8px 24px;
  border-radius: 8px;
  background: var(--primary-color);
  color: white;
  font-size: 14px;
  font-weight: 500;
  transition: background 0.3s ease;

  &:hover {
    background: var(--primary-hover);
  }
}

// 搜索弹窗
.search-modal {
  position: fixed;
  top: 64px;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: flex-start;
  justify-content: center;
  padding-top: 120px;
  z-index: 999;
}

.search-content {
  position: relative;
  width: 90%;
  max-width: 600px;
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 12px;
  padding: 16px;
  box-shadow: var(--shadow-lg);

  input {
    width: 100%;
    padding: 16px 60px 16px 20px;
    background: var(--bg-dark);
    border: 1px solid var(--border-color);
    border-radius: 8px;
    color: var(--text-primary);
    font-size: 16px;
    outline: none;
    transition: border-color 0.3s ease;

    &:focus {
      border-color: var(--primary-color);
    }

    &::placeholder {
      color: var(--text-secondary);
    }
  }
}

.search-btn {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  padding: 8px 20px;
  background: var(--primary-color);
  color: white;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  transition: background 0.3s ease;

  &:hover {
    background: var(--primary-hover);
  }
}

.close-btn {
  position: absolute;
  right: 16px;
  top: -48px;
  width: 36px;
  height: 36px;
  border-radius: 8px;
  background: var(--bg-card);
  color: var(--text-secondary);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;

  &:hover {
    background: var(--bg-hover);
    color: var(--text-primary);
  }
}

// 响应式
@media (max-width: 768px) {
  .nav-links {
    display: none;
  }

  .search-content {
    width: 95%;
    padding: 12px;

    input {
      padding: 14px 50px 14px 16px;
      font-size: 15px;
    }
  }

  .search-btn {
    padding: 6px 16px;
    font-size: 13px;
  }
}
</style>
