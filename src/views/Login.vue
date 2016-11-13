<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div id="login-panel">
    <h1>Login</h1>
    <form v-on:submit.prevent="login" id="login-form">
      <input v-model="account.username" class="form-control" placeholder="username" type="text"/>
      <input v-model="account.password" class="form-control" placeholder="password" type="password"/>
      <button type="submit" class="action-button btn btn-success btn-block">login</button>
    </form>
  </div>
</template>

<script scoped>
  import * as service from '../service';
  export default {
    data () {
      return {
        account: {
          username: '',
          password: ''
        },
        data: {name: ''}
      }
    },
    methods: {
      login () {
        const length = this.account.username.length;
        if (length < 2 || length > 18) {
          alert("username's length must between 2 and 18.");
          return;
        }
        let self = this;
        service.login(this.account).then(function (response) {
          if (response.data.success) {
            self.$store.dispatch('LOGIN', response.data.entity);
            self.$router.push('/index');
          } else {
            alert('username or password error!');
          }
        }).catch(function (error) {
          alert(error);
        });
        return false;
      }
    },
    watch: {}
  }
</script>

<style scoped>

  #login-panel {
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  #login-form {
    margin-top: 30px;
    width: 300px;
  }

  form * {
    margin-bottom: 5px;
  }

  form .btn {
    margin-top: 10px;
  }

</style>
