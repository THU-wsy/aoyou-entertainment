import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/views/Layout/index.vue'
import Login from '@/views/Login/index.vue'
import Home from '@/views/Home/index.vue'
import Game from '@/views/Game/index.vue'
import GameStoreDetail from '@/views/GameStoreDetail/index.vue'
import User from '@/views/User/index.vue'
import NotFoundView from '@/views/NotFoundView.vue'
import GameAdmin from '@/views/Admin/GameAdmin/index.vue'
import ShoppingAdmin from '@/views/Admin/ShoppingAdmin/index.vue'
import UserInfoAdmin from '@/views/Admin/UserInfoAdmin/index.vue'

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
        },
        {
          path: 'detail/game/:id',
          component: GameStoreDetail
        }
      ]      
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/user',
      component: User,
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: 'info',
          component: UserInfoAdmin
        },
        {
          path: 'game',
          component: GameAdmin
        },
        {
          path: 'shopping',
          component: ShoppingAdmin
        }
      ]
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      component: NotFoundView
    }    
  ]
})

export default router
