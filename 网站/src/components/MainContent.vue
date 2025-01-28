<template>
  <div class="main-content">
    <div class="header">
      <div class="group-info">
        <div class="group-name">Java Tutorial Group</div>
        <div class="member-count">457,738 members</div>
      </div>
      <div class="actions">
        <i class="icon">🔍</i>
        <i class="icon">⚙️</i>
      </div>
    </div>
    <div class="content">
      <div class="grid">
        <div v-for="n in 6" :key="n" class="grid-item" @click="handleImageClick">
          <div class="image-placeholder" :class="{ 'alt-gradient': n % 2 === 0 }"></div>
          <div class="overlay">
            <span class="close-icon">×</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MainContent',
  methods: {
    handleImageClick() {
      alert('Downloading image...')
    }
  }
}
</script>

<style lang="scss" scoped>
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;

  .header {
    padding: 16px;
    border-bottom: 1px solid #e0e0e0;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .group-info {
      .group-name {
        font-weight: 500;
      }

      .member-count {
        font-size: 14px;
        color: #666;
      }
    }

    .actions {
      display: flex;
      gap: 12px;

      .icon {
        font-size: 20px;
        color: #666;
        cursor: pointer;
      }
    }
  }

  .content {
    flex: 1;
    padding: 16px;
    background: #f0f2f5;
    overflow: auto;

    .grid {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 16px;

      .grid-item {
        position: relative;
        aspect-ratio: 1;
        cursor: pointer;
        border-radius: 8px;
        overflow: hidden;

        .image-placeholder {
          width: 100%;
          height: 100%;
          background: linear-gradient(135deg, #9b6bff 0%, #4c8fff 100%);

          &.alt-gradient {
            background: linear-gradient(135deg, #4c8fff 0%, #9b6bff 100%);
          }
        }

        .overlay {
          position: absolute;
          inset: 0;
          background: rgba(0, 0, 0, 0.5);
          display: flex;
          align-items: center;
          justify-content: center;
          opacity: 0;
          transition: opacity 0.3s;

          .close-icon {
            color: white;
            font-size: 32px;
          }
        }

        &:hover .overlay {
          opacity: 1;
        }
      }
    }
  }
}
</style> 