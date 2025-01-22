import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Content from '../views/Content.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/content',
    name: 'Content',
    component: Content
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 添加全局路由守卫用于调试
router.beforeEach((to, from, next) => {
  console.log('路由变化:', {
    from: from.path,
    to: to.path
  })
  next()
})

export default router 