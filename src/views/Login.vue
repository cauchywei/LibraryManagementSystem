/**
* Created by cauchywei on 16/10/11.
*/
<template>
  <div id="login-panel">
    <form v-on:submit.prevent="login" id="login-form">
      <input v-model="user.username" placeholder="username"/>
      <input v-model="user.password" placeholder="password" type="password"/>
      <button type="submit" class="action-button"> login</button>
    </form>
  </div>
</template>

<script>
  import * as service from '../service'
  export default {
    el: '#login-panel',
    data () {
      return {
        user: {
          username: '',
          password: ''
        },
        data: {name: 'test'}
      }
    },
    methods: {
      login () {
        const length = this.user.username.length
        if (length < 4 || length > 18) {
          alert("username's length must between 4~18")
          return
        }
        let self = this
        service.login(this.username, this.password).then(function (response) {
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
  #login-panel {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  #login-form {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
</style>
