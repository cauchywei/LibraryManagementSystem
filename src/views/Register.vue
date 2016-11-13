<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div id="register-panel">
    <h1>Register</h1>
    <form v-on:submit.prevent="register" id="register-form" class="form-horizontal">
      <div class="form-group">
        <label for="username">username</label>
        <input v-model="account.username" id="username" class="form-control" placeholder="input your student ID" type="text"/>

        <label for="password">password</label>
        <input v-model="account.password" id="password" class="form-control" placeholder="password" type="password"/>

        <label for="name">name <small class="text-muted">optional</small></label>
        <input v-model.trim="account.name" id="name" class="form-control" placeholder="your name" type="text"/>

        <label for="age">age <small class="text-muted">optional</small></label>
        <input v-model.number="account.age" id="age" class="form-control" placeholder="your age" type="number"/>

        <label for="avatar">avatar <small class="text-muted">optional</small></label>
        <input v-on:change="setAvatar" id="avatar" class="form-control" placeholder="your avatar" type="file"/>

        <label for="major">major <small class="text-muted">optional</small></label>
        <input v-model.trim="account.major" id="major" class="form-control" placeholder="your major" type="text"/>

        <label for="phone">phone <small class="text-muted">optional</small></label>
        <input v-model.tel="account.phone" id="phone" class="form-control" placeholder="your phone number" type="tel"/>

        <label for="email">email <small class="text-muted">optional</small></label>
        <input v-model.email="account.email" id="email" class="form-control" placeholder="your email to receive notices" type="email"/>

        <label for="remarks">remarks <small class="text-muted">optional</small></label>
        <textarea v-model.trim="account.remarks" id="remarks" class="form-control" rows="3" placeholder="anything you want!"></textarea>

        <button type="submit" class="btn btn-success btn-block">register</button>
      </div>

    </form>
  </div>
</template>

<script>
  import * as service from '../service'
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
      setAvatar(e) {
        var target = e.target;
        var files = target.files;
        if (!files || files.length === 0) {
          this.account.avatar = null;
        } else {
          this.account.avatar = files[0];
        }
      },
      register() {
        if (!this.account.username
            || !this.account.password
            || !this.account.age
            || !this.account.name
            || !this.account.major
            || !this.account.phone
            || !this.account.email) {
          alert('please fill all fields!');
          return false;
        }

        let self = this;
        service.register(this.account).then(function (response) {
          if (response.data.success) {
            alert('register success!');
            self.$router.push('/login');
          } else {
            alert('register fail!');
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

  #register-panel {
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  #register-form {
    margin-top: 30px;
    width: 400px;
  }

  #register-form input,
  #register-form textarea,
  #register-form div {
    align-self: stretch;
  }

  form * {
    margin-bottom: 5px;
  }

  form .btn {
    margin-top: 10px;
  }

</style>
