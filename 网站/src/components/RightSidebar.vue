<template>
  <div class="right-sidebar">
    <div class="profile">
      <div class="avatar"></div>
      <div class="info">
        <div class="name">Java Tutorial Group</div>
        <div class="members">457,738 members</div>
      </div>
    </div>

    <div class="actions">
      <div class="action-item">
        <div class="action-left">
          <i class="icon">🔗</i>
          <span>t.me/xrw7715</span>
        </div>
        <button class="link-btn">链接</button>
      </div>

      <div class="action-item">
        <div class="action-left">
          <i class="icon">🔔</i>
          <span>通知</span>
        </div>
        <label class="switch">
          <input type="checkbox">
          <span class="slider"></span>
        </label>
      </div>

      <div v-for="(stat, index) in stats" :key="index" class="stat-item">
        <div class="stat-left">
          <i class="icon">{{ stat.icon }}</i>
          <span>{{ stat.count }} {{ stat.label }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RightSidebar',
  data() {
    return {
      stats: [
        { icon: '🔖', count: 7, label: '条收藏消息' },
        { icon: '📷', count: 1855, label: '张图片' },
        { icon: '🎬', count: 12058, label: '个视频' },
        { icon: '💬', count: 813, label: '条链接' },
        { icon: '🎁', count: 2, label: '条留言' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.right-sidebar {
  width: 320px;
  border-left: 1px solid #e0e0e0;
  padding: 16px;

  .profile {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 16px;
    margin-bottom: 24px;

    .avatar {
      width: 80px;
      height: 80px;
      border-radius: 50%;
      background: #f0f0f0;
    }

    .info {
      text-align: center;

      .name {
        font-weight: 500;
      }

      .members {
        font-size: 14px;
        color: #666;
      }
    }
  }

  .actions {
    display: flex;
    flex-direction: column;
    gap: 16px;

    .action-item, .stat-item {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .action-left, .stat-left {
        display: flex;
        align-items: center;
        gap: 8px;
      }
    }

    .link-btn {
      padding: 4px 12px;
      border: none;
      background: none;
      color: #666;
      cursor: pointer;

      &:hover {
        background: #f0f0f0;
        border-radius: 4px;
      }
    }

    .switch {
      position: relative;
      display: inline-block;
      width: 44px;
      height: 24px;

      input {
        opacity: 0;
        width: 0;
        height: 0;

        &:checked + .slider {
          background-color: #4a90e2;
        }

        &:checked + .slider:before {
          transform: translateX(20px);
        }
      }

      .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        transition: .4s;
        border-radius: 24px;

        &:before {
          position: absolute;
          content: "";
          height: 20px;
          width: 20px;
          left: 2px;
          bottom: 2px;
          background-color: white;
          transition: .4s;
          border-radius: 50%;
        }
      }
    }
  }
}
</style> 