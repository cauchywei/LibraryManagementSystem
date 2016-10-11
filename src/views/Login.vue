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
  </div>
</template>

<script>
  export default {
    el: '#login-panel',
    data () {
      return {
        user: {
          username: '',
          password: ''
        }
      }
    },
    methods: {
      login () {
        const length = this.user.username.length
        if (length < 4 || length > 18) {
          return
        }
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
