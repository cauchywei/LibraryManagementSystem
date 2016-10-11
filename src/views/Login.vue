<template>
  <div id="login-panel">
    <h1>Login</h1>
    <form v-on:submit.prevent="login" id="login-form">
      <input v-model="account.username" placeholder="username" type="text"/>
      <input v-model="account.password" placeholder="password" type="password"/>
      <button type="submit" class="action-button">login</button>
    </form>
  </div>
</template>

<script>
  import * as service from '../service'
  export default {
    el: '#login-panel',
    data () {
      return {
        account: {
          username: '',
          password: ''
        },
        data: {name: 'test'}
      }
    },
    methods: {
      login () {
        const length = this.account.username.length
        if (length < 2 || length > 18) {
          alert("username's length must between 2~18")
          return
        }
        let self = this
        service.login(this.account.username, this.account.password).then(function (response) {
          if (response.data.success) {
            self.$store.dispatch('ON_LOGIN', response.data.entity)
            self.$router.push('/index')
          } else {
            alert('username or password error!')
          }
        }).catch(function (error) {
          alert(error)
        })
        return false
      }
    },
    watch: {}
  }
</script>

<style scoped>
  #login-panel {
    height: 100%;
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  #login-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 30px;
  }
</style>
