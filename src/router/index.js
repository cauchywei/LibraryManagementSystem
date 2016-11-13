import Vue from 'vue';
import Router from 'vue-router';

import HomeView from '../views/Home';
import LoginView from '../views/Login';
import RegisterView from '../views/Register';
import UserView from '../views/User';
import BorrowRecordsView from '../views/Borrows';
import BookManagementView from '../views/admin/BookManagement';
import BorrowManagementView from '../views/admin/BorrowManagement';
import UserManagementView from '../views/admin/UserManagement';

Vue.use(Router);

var router = new Router({
  mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes: [
    { path: '/index', component: HomeView },
    { path: '/user/login', component: LoginView },
    { path: '/user/register', component: RegisterView },
    { path: '/user', component: UserView },
    { path: '/user/borrows', component: BorrowRecordsView },
    { path: '/admin/books', component: BookManagementView },
    { path: '/admin/borrows', component: BorrowManagementView },
    { path: '/admin/users', component: UserManagementView },
    { path: '*', redirect: '/index' }
  ]
});

export default router;
