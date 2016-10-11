/**
* Created by cauchywei on 16/10/11.
*/
<template>
  <div id="login-panel">
    <form v-on="login" id="login-form">
      <input v-model="user.username" placeholder="username"/>
      <input v-model="user.password" placeholder="password" type="password"/>
      <button type="submit" class="action-button"> login</button>
    </form>
    <div>{{userResponse}}</div>
  </div>
</template>

<script>
  import service from '../service'

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
          return
        }
        service.login(this.username, this.password).then(function (response) {
          this.data = response
        }).catch(function (error) {
          this.data = error
        })
      }
    },
    watch: {
      userResponse () {
        return this.data
      }
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
