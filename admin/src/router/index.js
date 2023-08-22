import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    component: () => import('../views/login.vue')
  },
  {
    path: "/",
    component: () => import('../views/main.vue'),
    meta:{
      loginRequire: true
    },
    children:[{
      path: 'welcome',
      component: () => import('../views/main/welcome.vue')
    },{
      path: 'about',
      component: () => import('../views/main/about.vue')
    }, {
      path: 'station',
      component: () => import('../views/main/station.vue')
    }, {
      path: 'train',
      component: () => import('../views/main/train.vue')
    }, {
      path: 'train-station',
      component: () => import('../views/main/train-station.vue')
    }, {
      path: 'train-carriage',
      component: () => import('../views/main/train-carriage.vue')
    }, {
      path: 'train-seat',
      component: () => import('../views/main/train-seat.vue')
    }]
  },
  {
    path:'',
    redirect: '/welcome'
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
