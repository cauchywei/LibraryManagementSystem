<template>
  <div id="user-panel">
    <!--<img class="avatar" :src="account.avatar">-->
    <!--<a id="upload" class="avatar">upload avatar</a>-->
    <div v-if="!!user">
      <h1 id="title"> My Info </h1>
      <div>
        <div class="info-row">
          <h4>Username:</h4>
          {{user.username}}
        </div>
        <div class="info-row">
          <h4>Name:</h4>
          {{user.name}}
        </div>
        <div class="info-row">
          <h4>Age:</h4>
          {{user.age}}
        </div>
        <div class="info-row">
          <h4>Major:</h4>
          {{user.major}}
        </div>
        <div class="info-row">
          <h4>Email:</h4>
          {{user.email}}
        </div>
        <div class="info-row">
          <h4>Phone:</h4>
          {{user.phone}}
        </div>
        <div class="info-row">
          <h4>Description:</h4>
          {{user.remarks}}
        </div>
      </div>
    </div>
    <h1 v-else> UnLogin</h1>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        user: this.$store.state.account
      }
    }
  }
</script>

<style scoped>

  #user-panel {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .avatar {
    width: 100px;
    height: 100px;
    border-width: 0;
    border-radius: 50px;
  }

  .info-row {
    display: flex;
    flex-direction: row;
    align-items: baseline;
  }

  .info-row h4 {
    width: 120px;
    text-align: start;
    color: #888888;
  }

  #title {
    margin-bottom: 30px;
  }
</style>
