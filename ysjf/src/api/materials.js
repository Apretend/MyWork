/**
 * 素材相关 API
 * 所有接口已预留，接入后端时取消注释并实现即可
 */

import request from './request'
import { API_ENDPOINTS } from './config'

/**
 * 获取素材列表
 * @param {Object} params - 查询参数 { page, category, resolution, price, sort, search }
 * @returns {Promise}
 */
export const getMaterials = (params) => {
  // TODO: 接入真实API
  // return request.get(API_ENDPOINTS.MATERIALS, { params })

  // 模拟数据返回
  return Promise.resolve({
    code: 200,
    data: {
      list: [],
      total: 0,
      page: params.page || 1,
      pageSize: 20
    }
  })
}

/**
 * 获取素材详情
 * @param {string|number} id - 素材ID
 * @returns {Promise}
 */
export const getMaterialDetail = (id) => {
  // TODO: 接入真实API
  // return request.get(API_ENDPOINTS.MATERIAL_DETAIL(id))

  return Promise.resolve({
    code: 200,
    data: {}
  })
}

/**
 * 搜索素材
 * @param {Object} params - 搜索参数 { keyword, page }
 * @returns {Promise}
 */
export const searchMaterials = (params) => {
  // TODO: 接入真实API
  // return request.get(API_ENDPOINTS.MATERIAL_SEARCH, { params })

  return Promise.resolve({
    code: 200,
    data: {
      list: [],
      total: 0
    }
  })
}

/**
 * 下载素材
 * @param {string|number} id - 素材ID
 * @returns {Promise}
 */
export const downloadMaterial = (id) => {
  // TODO: 接入真实API
  // return request.get(API_ENDPOINTS.DOWNLOAD(id), { responseType: 'blob' })

  return Promise.resolve({
    code: 200,
    data: {
      downloadUrl: ''
    }
  })
}

/**
 * 收藏素材
 * @param {string|number} id - 素材ID
 * @returns {Promise}
 */
export const collectMaterial = (id) => {
  // TODO: 接入真实API
  // return request.post(API_ENDPOINTS.COLLECT_ADD(id))

  return Promise.resolve({
    code: 200,
    message: '收藏成功'
  })
}

/**
 * 取消收藏
 * @param {string|number} id - 素材ID
 * @returns {Promise}
 */
export const uncollectMaterial = (id) => {
  // TODO: 接入真实API
  // return request.delete(API_ENDPOINTS.COLLECT_REMOVE(id))

  return Promise.resolve({
    code: 200,
    message: '取消收藏成功'
  })
}
