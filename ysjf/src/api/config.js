/**
 * API 配置文件
 * 后续接入真实后端时，修改这里的配置即可
 */

export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api'
export const API_TIMEOUT = 10000

// API 端点配置
export const API_ENDPOINTS = {
  // 素材相关
  MATERIALS: '/materials',
  MATERIAL_DETAIL: (id) => `/materials/${id}`,
  MATERIAL_SEARCH: '/materials/search',

  // 分类相关
  CATEGORIES: '/categories',

  // 用户相关
  LOGIN: '/auth/login',
  REGISTER: '/auth/register',
  USER_INFO: '/user/info',

  // 收藏相关
  COLLECT_LIST: '/user/collections',
  COLLECT_ADD: (id) => `/user/collections/${id}`,
  COLLECT_REMOVE: (id) => `/user/collections/${id}`,

  // 下载相关
  DOWNLOAD: (id) => `/materials/${id}/download`
}

// HTTP 状态码
export const HTTP_STATUS = {
  OK: 200,
  CREATED: 201,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  INTERNAL_SERVER_ERROR: 500
}
