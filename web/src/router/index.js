import { createRouter, createWebHashHistory } from 'vue-router'
import Layout from '@/views/layout'

export const constantRouterMap = [
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    meta: {title: '首页', icon: 'home'},
    children: [{
      path: 'home',
      name: 'home',
      component: () => import('@/views/home'),
      meta: {title: '仪表盘', icon: 'dashboard'}
    }]
  },
  {path: '/login', component: () => import('@/views/login') },
  {path: '/404', component: () => import('@/views/404') },
]


export const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRouterMap,
})
