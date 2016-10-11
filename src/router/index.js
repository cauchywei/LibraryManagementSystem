import Vue from 'vue'
import Router from 'vue-router'
import Todo from '../components/Todo.vue'
import LoginView from '../views/Login.vue'

Vue.use(Router)

// import { createListView } from '../views/CreateListView'
// import ItemView from '../views/ItemView.vue'
// import UserView from '../views/UserView.vue'

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: [
    { path: '/index', component: Todo },
    { path: '/login', component: LoginView },
    { path: '*', redirect: '/index' }
  ]
})
