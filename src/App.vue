<template>
  <div id="app">
    <div id="header">

      <router-link id="header-title" to="/">Library Management System</router-link>

      <div class="user-operation-panel" v-if="login">
        <span>Welcome <router-link id="username" to="/my">{{account.name}} </router-link></span>

        <div v-if="isAdmin" class="action-bar">
          <h5 class="admin"> ( admin ) </h5>
          <div class="button-divider">|</div>
          <router-link to="/book_management" class="header-button">Manage Books</router-link>
        </div>
        <div class="action-bar" v-else>
          <div class="button-divider">|</div>
          <router-link to="/my/borrow_records" class="header-button">My Borrow Record</router-link>
        </div>


        <div class="button-divider">|</div>
        <a class="header-button" @click="logout">Logout</a>
      </div>
      <div class="user-operation-panel" v-if="!login">
        <router-link to="/login" class="header-button">Login</router-link>
        <div class="button-divider">|</div>
        <router-link to="/register" class="header-button">Register</router-link>
      </div>

    </div>

    <transition name="fade" mode="out-in">
      <router-view class="view"></router-view>
    </transition>
  </div>
</template>

<script>
  import Hello from './components/Hello'

  export default {
    components: {
      Hello
    },
    data() {
      return {
//        login: this.$store.state.isLogin,
//        user: this.$store.state.user
      }
    },
    methods: {
      logout() {
        this.$store.dispatch('LOGOUT')
        this.$router.push('/index')
      }
    },
    computed: {
      login() {
        return this.$store.getters.isLogin;
      },
      account() {
        return this.$store.state.account;
      },
      isAdmin() {
        return this.$store.state.account.role === 'ADMIN'
      }
    }
  }
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
    font-family: Source Sans Pro, Helvetica, sans-serif;
    text-align: center;
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

  .header-button {
    font-size: 0.9em;
    color: #42b983;
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
    margin-left: 10px;
    margin-right: 10px;
    color: #cccccc;
  }

  .action-button {
    padding: 8px 16px 8px 16px;
    background: #42b983;
    color: #ffffff;
    border-width: 0;
    border-radius: 4px;
  }

  input {
    border: 1px solid #eeeeee;
    padding: 8px;
    margin: 8px 16px 8px 16px;
  }

  textarea {
    border: 1px solid #eeeeee;
    padding: 8px;
    margin: 8px 16px 8px 16px;
  }

  #username {
    text-decoration: underline;
  }

  #user-operation-panel #admin {
    margin-left: 10px;
    padding-right: 4px;
  }


</style>
