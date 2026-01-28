<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="footer-section">
          <h3>影视飓风</h3>
          <p>专业影视素材平台</p>
          <p>为创作者提供高质量素材</p>
        </div>

        <div class="footer-section">
          <h4>快速链接</h4>
          <ul>
            <li><router-link to="/">首页</router-link></li>
            <li><router-link to="/material-library">素材库</router-link></li>
            <li><router-link to="/about">关于我们</router-link></li>
          </ul>
        </div>

        <div class="footer-section">
          <h4>联系方式</h4>
          <ul>
            <li>邮箱: contact@ysjf.com</li>
            <li>微信: ysjf_official</li>
          </ul>
        </div>
      </div>

      <div class="footer-bottom">
        <p>&copy; 2016-2026 杭州星奥传媒有限公司 版权所有</p>
        <p>浙ICP备19028999号-2 | 浙B2-20201416</p>
      </div>
    </div>
  </footer>
</template>

<style lang="scss" scoped>
.footer {
  background: var(--bg-card);
  border-top: 1px solid var(--border-color);
  padding: 48px 0 24px;
  margin-top: 80px;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
  margin-bottom: 40px;
}

.footer-section {
  h3 {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 12px;
    color: var(--text-primary);
  }

  h4 {
    font-size: 16px;
    font-weight: 500;
    margin-bottom: 16px;
    color: var(--text-primary);
  }

  p {
    color: var(--text-secondary);
    font-size: 14px;
    line-height: 1.8;
  }

  ul {
    list-style: none;

    li {
      margin-bottom: 8px;

      a {
        color: var(--text-secondary);
        font-size: 14px;
        transition: color 0.3s ease;

        &:hover {
          color: var(--primary-color);
        }
      }
    }
  }
}

.footer-bottom {
  padding-top: 24px;
  border-top: 1px solid var(--border-color);
  text-align: center;

  p {
    color: var(--text-secondary);
    font-size: 13px;
    margin-bottom: 4px;
  }
}

@media (max-width: 768px) {
  .footer {
    padding: 32px 0 20px;
    margin-top: 40px;
  }

  .footer-content {
    grid-template-columns: 1fr;
    gap: 32px;
  }
}
</style>
