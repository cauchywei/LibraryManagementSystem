<template>
  <div id="app">
    <div id="header">

      <router-link id="header-title" to="/">Library Management System</router-link>

      <div class="user-operation-panel" v-show="!login">
        <router-link to="/login" class="header-button">Login</router-link>
        <div class="button-divider">|</div>
        <router-link to="/register" class="header-button">Register</router-link>
      </div>

      <div class="user-operation-panel" v-show="login">
        <a class="header-button" @click="logout">Logout</a>
      </div>
    </div>
    <transition name="fade" mode="out-in">
      <router-view class="view"></router-view>
    </transition>
  </div>
</template>

<script>
  import Hello from './components/Hello'
  import router from './router'

  export default {
    components: {
      Hello
    },
    data () {
      return {
        login: false
      }
    },
    methods: {
      logout () {
        this.$store.dispatch('LOGOUT')
        router.go('/')
      }
    },
    computed: {
      login: function () {
        return !!this.$store.state.user
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
  }

  .button-divider {
    margin-left: 10px;
    margin-right: 10px;
    color: #cccccc;
  }

  .action-button {
    padding: 4px 10px 4px 10px;
    background: #42b983;
    color: #ffffff;
    border-width: 0;
    border-radius: 4px;
  }

  input {
    border: 1px solid #eeeeee;
    padding: 8px;
    margin: 16px;
  }

</style>
