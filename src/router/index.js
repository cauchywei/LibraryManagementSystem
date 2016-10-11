import Vue from 'vue'
import Router from 'vue-router'

import LoginView from '../views/Login.vue'
import HomeView from '../views/Home.vue'
import RegisterView from '../views/Register.vue'
import UserView from '../views/User.vue'
import BorrowRecordsView from '../views/BorrowRecords.vue'

Vue.use(Router)

var router = new Router({
  mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes: [
    { path: '/index', component: HomeView },
    { path: '/login', component: LoginView },
    { path: '/register', component: RegisterView },
    { path: '/my', component: UserView },
    { path: '/my/records', component: BorrowRecordsView },
    { path: '*', redirect: '/index' }
  ]
})
export default router
