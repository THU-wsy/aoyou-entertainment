import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/views/Layout/index.vue'
import Login from '@/views/Login/index.vue'
import Home from '@/views/Home/index.vue'
import Game from '@/views/Game/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      component: Layout,
      children: [
        {
          path: '',
          component: Home
        },
        {
          path: 'game',
          component: Game
        }
      ]      
    },
    {
      path: '/login',
      component: Login
    }
  ]
})

export default router
