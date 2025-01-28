<template>
  <div class="left-sidebar">
    <div class="search-header">
      <div class="search-container">
        <i class="menu-icon">☰</i>
        <input type="text" placeholder="搜索" class="search-input">
      </div>
    </div>
    <div class="chat-list">
      <div class="chat-item">
        <div class="avatar"></div>
        <div class="chat-info">
          <div class="chat-name">Chat Room 1</div>
          <div class="last-message">Last message...</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LeftSidebar'
}
</script>

<style lang="scss" scoped>
.left-sidebar {
  width: 260px;
  border-right: 1px solid #e0e0e0;
  background: #1a2236;
  color: white;
  display: flex;
  flex-direction: column;

  .search-header {
    padding: 16px;
    border-bottom: 1px solid #2a3246;

    .search-container {
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .menu-icon {
      font-size: 20px;
    }

    .search-input {
      flex: 1;
      padding: 8px;
      border-radius: 4px;
      border: none;
      background: #2a3246;
      color: white;
    }
  }

  .chat-list {
    flex: 1;
    overflow-y: auto;

    .chat-item {
      padding: 16px;
      display: flex;
      align-items: center;
      gap: 12px;
      cursor: pointer;

      &:hover {
        background: #2a3246;
      }

      .avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background: #4a90e2;
      }

      .chat-info {
        .chat-name {
          font-weight: 500;
        }

        .last-message {
          font-size: 14px;
          color: #a0a0a0;
        }
      }
    }
  }
}
</style> 