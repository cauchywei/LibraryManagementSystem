<template>
  <div id="app">
    <div id="header">

      <router-link id="header-title" to="/">Purjoy Library Management System</router-link>

      <div v-if="isLogin" class="user-operation-panel" >
        <span>Welcome, <router-link id="username" to="/user">{{account.name}}</router-link></span>

        <div v-if="isAdmin" class="action-bar">
          <h5 class="admin"> ( admin ) </h5>

          <span class="button-divider">|</span>
          <router-link to="/admin/books" class="header-button">Manage Books</router-link>

          <span class="button-divider">|</span>
          <router-link to="/admin/borrows" class="header-button">Manage Borrows</router-link>

          <span class="button-divider">|</span>
          <router-link to="/admin/users" class="header-button">Manage Users</router-link>
        </div>

        <span class="action-bar" v-else>
          <span class="button-divider">|</span>
          <router-link to="/user/borrows" class="header-button">My Borrows</router-link>
        </span>

        <div class="button-divider">|</div>
        <a href="#" class="header-button" @click="logout">Logout</a>
      </div>

      <div class="user-operation-panel" v-else>
        <router-link to="/user/login" class="header-button">Login</router-link>
        <div class="button-divider">|</div>
        <router-link to="/user/register" class="header-button">Register</router-link>
      </div>

    </div>

    <transition name="fade" mode="out-in">
      <router-view class="view"></router-view>
    </transition>

    <div class="container clearfix">
      <hr/>
      <footer class="text-muted">
        SPM 2016.
      </footer>
      <br/>
    </div>
  </div>
</template>

<script>
  import Home from './views/Home';

  export default {
    components: {
      Home
    },
    data() {
      return {
      }
    },
    methods: {
      logout() {
        this.$store.dispatch('LOGOUT');
        this.$router.push('/index');
      }
    },
    computed: {
      isLogin() {
        return this.$store.getters.isLogin;
      },
      account() {
        return this.$store.getters.getAccount;
      },
      isAdmin() {
        return this.$store.getters.isAdmin;
      }
    }
  };
</script>

<style>

  html {
    height: 100%;
  }

  body {
    display: flex;
    /*align-items: center;*/
    /*justify-content: center;*/
    height: 100%;
  }

  #app {
    color: #2c3e50;
    /*font-family: Source Sans Pro, Helvetica, sans-serif;*/
    /*text-align: center;*/
    width: 100%;
  }

  #app a {
    color: #42b983;
    text-decoration: none;
  }

  #header-title {
    color: #2c3e50;
    font-family: Source Sans Pro, Helvetica, sans-serif;
    font-size: 1.4em;
    font-style: normal;
  }

  #header {
    padding: 10px 20px 10px 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-direction: row;
  }

  .user-operation-panel {
    display: flex;
    flex-direction: row;
    align-self: flex-end;
    align-items: center;
    align-content: flex-end
  }

  .action-bar {
    display: flex;
    margin-left: 10px;
    padding-right: 4px;
    flex-direction: row;
    align-self: flex-end;
    align-items: baseline;
  }

  .button-divider {
    margin-left: 5px;
    margin-right: 5px;
    color: #cccccc;
  }

  #user-operation-panel #admin {
    margin-left: 10px;
    padding-right: 4px;
  }

  .fade-enter-active, .fade-leave-active {
    transition: opacity .3s
  }
  .fade-enter, .fade-leave-active {
    opacity: 0
  }

  * {
    border-radius: 0 !important;
  }

</style>
