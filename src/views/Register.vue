<template>
  <div id="register-panel">
    <h1>Register</h1>
    <form v-on:submit.prevent="register" id="register-form">
      <input v-model="account.username" placeholder="username" type="text"/>
      <input v-model="account.password" placeholder="password" type="password"/>
      <button type="submit" class="action-button">register</button>
    </form>
  </div>
</template>

<script>
  import * as service from '../service'
  export default {
    el: '#register-panel',
    data () {
      return {
        account: {
          username: '',
          password: ''
        },
        data: { name: 'test' }
      }
    },
    methods: {
      register () {
        const length = this.account.username.length
        if (length < 2 || length > 18) {
          alert("username's length must between 2~18")
          return
        }
        let self = this
        service.login(this.account.username, this.account.password).then(function (response) {
          self.$store.dispatch('ON_LOGIN', response.data)
          self.$router.go(-1)
        }).catch(function (error) {
          alert(error)
        })
        return false
      }
    },
    watch: {

    }
  }
</script>

<style scoped>
  #register-panel {
    height: 100%;
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  #register-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 30px;
  }
</style>
