<template>
  <div id="register-panel">
    <h1>Register</h1>
    <form v-on:submit.prevent="register" id="register-form">
      <div style="{ display: flex; flex-direction: row}">
        <input v-model="account.username" placeholder="username" type="text"/>
        <input v-model="account.password" placeholder="password" type="password"/>
      </div>

      <div style="{ display: flex; flex-direction: row}">
        <input v-model="account.name" placeholder="name" type="text"/>
        <input v-model="account.age" placeholder="age" v-model.number="age" type="number"/>
      </div>

      <input v-model="account.major" class="full-input" placeholder="major" type="text"/>
      <input v-model="account.phone" class="full-input" placeholder="phone" type="tel"/>
      <input v-model="account.email" class="full-input" placeholder="email" type="email"/>
      <textarea v-model="account.remarks" class="full-input" placeholder="self description"></textarea>
      <button type="submit"  class="action-button">register</button>
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
        data: {name: 'test'}
      }
    },
    methods: {
      register () {
        if (!this.account.username || !this.account.password || !this.account.age || !this.account.name || !this.account.major || !this.account.phone || !this.account.email) {
          alert('please fill all info!')
          return false
        }

        let self = this
        service.register(this.account).then(function (response) {
          if (response.data.success) {
            alert('register success!')
            self.$router.push('/login')
          } else {
            alert('register fail!')
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

  .full-input {
    align-self: stretch;
  }

  #register-form .action-button {
    margin-top: 20px;
  }

</style>
