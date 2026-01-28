# 影视素材展示官网

基于 Vue 3 + Vite 构建的影视素材展示平台官网

## 功能特性

- ✨ 响应式设计，支持桌面端和移动端
- 🎨 现代化 UI 设计，暗色主题
- 🔍 强大的搜索功能
- 📂 素材分类展示
- 🎯 素材详情预览
- 🔽 素材下载功能（预留接口）
- ⭐ 收藏功能（预留接口）
- 📄 分页浏览

## 技术栈

- **框架**: Vue 3 (Composition API)
- **构建工具**: Vite
- **路由**: Vue Router 4
- **HTTP 客户端**: Axios
- **样式**: SCSS
- **状态管理**: 预留（可用 Pinia）

## 项目结构

```
ysjf/
├── public/                 # 静态资源
├── src/
│   ├── api/               # API 接口层
│   │   ├── config.js      # API 配置
│   │   ├── request.js     # Axios 封装
│   │   ├── materials.js   # 素材相关接口
│   │   └── index.js       # 统一导出
│   ├── assets/            # 资源文件
│   ├── components/        # 公共组件
│   │   ├── Navigation.vue # 导航栏
│   │   ├── Footer.vue     # 页脚
│   │   └── MaterialCard.vue # 素材卡片
│   ├── router/            # 路由配置
│   │   └── index.js
│   ├── styles/            # 全局样式
│   │   └── global.scss
│   ├── views/             # 页面组件
│   │   ├── Home.vue           # 首页
│   │   ├── MaterialLibrary.vue # 素材库
│   │   ├── MaterialDetail.vue  # 素材详情
│   │   └── About.vue          # 关于我们
│   ├── App.vue            # 根组件
│   └── main.js            # 入口文件
├── index.html
├── vite.config.js
├── package.json
└── README.md
```

## 快速开始

### 安装依赖

```bash
npm install
```

### 本地开发

```bash
npm run dev
```

访问 http://localhost:3000

### 构建生产

```bash
npm run build
```

### 预览生产构建

```bash
npm run preview
```

## 环境配置

复制 `.env.example` 为 `.env.local` 并修改配置：

```bash
cp .env.example .env.local
```

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

## API 接入说明

所有 API 接口已在 `src/api/` 目录下预留，接入步骤：

1. **修改 API 配置**
   ```bash
   # src/api/config.js
   export const API_BASE_URL = 'https://your-api.com/api'
   ```

2. **取消注释接口代码**
   ```javascript
   // src/api/materials.js
   export const getMaterials = (params) => {
     return request.get(API_ENDPOINTS.MATERIALS, { params })
   }
   ```

3. **在组件中调用**
   ```javascript
   import { materialsAPI } from '@/api'

   const loadMaterials = async () => {
     const result = await materialsAPI.getMaterials({ page: 1 })
     // 处理返回数据
   }
   ```

## 数据替换

### 方式一：修改组件内的 mock 数据

直接在组件的 `setup()` 函数中修改 `materials` 等数据：

```javascript
// src/views/Home.vue
const featuredMaterials = ref([
  {
    id: 1,
    title: '你的素材标题',
    thumbnail: 'https://your-image-url.com/image.jpg',
    // ... 其他字段
  }
])
```

### 方式二：接入管理系统（推荐）

1. 开发后台管理系统，提供素材管理接口
2. 在组件中调用 API 接口获取数据
3. 通过管理系统上传和管理素材

## 后续功能扩展

- [ ] 用户登录/注册
- [ ] 用户个人中心
- [ ] 收藏夹功能
- [ ] 下载历史
- [ ] 视频预览播放
- [ ] 评论系统
- [ ] 素材上传（用户投稿）
- [ ] 支付系统（付费素材）

## 浏览器支持

- Chrome (推荐)
- Firefox
- Safari
- Edge

## 许可证

Copyright© 2016-2026 杭州星奥传媒有限公司
