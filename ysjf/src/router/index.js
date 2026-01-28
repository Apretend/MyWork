import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/material-library',
    name: 'MaterialLibrary',
    component: () => import('../views/MaterialLibrary.vue')
  },
  {
    path: '/material/:id',
    name: 'MaterialDetail',
    component: () => import('../views/MaterialDetail.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
