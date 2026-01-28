<template>
  <div class="about-page">
    <div class="container">
      <!-- 页面头部 -->
      <div class="page-header">
        <h1 class="page-title">关于我们</h1>
        <p class="page-subtitle">专业的影视素材平台，为创作者赋能</p>
      </div>

      <!-- 介绍内容 -->
      <div class="about-content">
        <section class="about-section">
          <h2 class="section-title">关于影视飓风</h2>
          <div class="section-content">
            <p>
              影视飓风是一个专注于为创作者提供高质量影视素材的平台。我们致力于打造国内领先的影视素材库，
              帮助视频创作者、设计师、广告公司等快速获取优质的素材资源。
            </p>
            <p>
              自2016年成立以来，我们已积累了数万个高质量素材，涵盖自然风光、城市建筑、人物肖像、
              科技数码等多个分类。无论是个人练习还是商业项目，都能在这里找到合适的素材。
            </p>
          </div>
        </section>

        <section class="about-section">
          <h2 class="section-title">我们的优势</h2>
          <div class="features-grid">
            <div class="feature-card">
              <div class="feature-icon">🎬</div>
              <h3 class="feature-title">高质量素材</h3>
              <p class="feature-desc">所有素材均经过专业筛选，确保质量</p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">⚡</div>
              <h3 class="feature-title">快速下载</h3>
              <p class="feature-desc">高速服务器，秒级下载体验</p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">🔍</div>
              <h3 class="feature-title">精准搜索</h3>
              <p class="feature-desc">智能分类和标签系统，快速找到所需素材</p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">💰</div>
              <h3 class="feature-title">灵活授权</h3>
              <p class="feature-desc">支持个人练习、个人商用、企业商用等多种授权</p>
            </div>
          </div>
        </section>

        <section class="about-section">
          <h2 class="section-title">联系我们</h2>
          <div class="contact-info">
            <div class="contact-item">
              <h4>商务合作</h4>
              <p>business@ysjf.com</p>
            </div>
            <div class="contact-item">
              <h4>用户支持</h4>
              <p>support@ysjf.com</p>
            </div>
            <div class="contact-item">
              <h4>微信公众号</h4>
              <p>影视飓风</p>
            </div>
            <div class="contact-item">
              <h4>公司地址</h4>
              <p>浙江省杭州市</p>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.about-page {
  padding: 32px 0 64px;
}

.page-header {
  text-align: center;
  margin-bottom: 64px;
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

.about-content {
  max-width: 900px;
  margin: 0 auto;
}

.about-section {
  margin-bottom: 64px;
}

.section-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 24px;
  color: var(--text-primary);
}

.section-content {
  p {
    font-size: 16px;
    line-height: 1.8;
    color: var(--text-secondary);
    margin-bottom: 16px;

    &:last-child {
      margin-bottom: 0;
    }
  }
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 24px;
}

.feature-card {
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 12px;
  padding: 32px 24px;
  text-align: center;
  transition: all 0.3s ease;

  &:hover {
    border-color: var(--primary-color);
    transform: translateY(-4px);
    box-shadow: var(--shadow-lg);
  }
}

.feature-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.feature-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 8px;
  color: var(--text-primary);
}

.feature-desc {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
}

.contact-info {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 24px;
}

.contact-item {
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 12px;
  padding: 24px;

  h4 {
    font-size: 16px;
    font-weight: 600;
    margin-bottom: 8px;
    color: var(--text-primary);
  }

  p {
    font-size: 14px;
    color: var(--text-secondary);
  }
}

// 响应式
@media (max-width: 768px) {
  .about-page {
    padding: 24px 0 48px;
  }

  .page-title {
    font-size: 28px;
  }

  .section-title {
    font-size: 24px;
  }

  .features-grid,
  .contact-info {
    grid-template-columns: 1fr;
  }
}
</style>
