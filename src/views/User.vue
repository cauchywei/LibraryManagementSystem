<template>
  <div id="user-panel">
    <!--<img class="avatar" :src="account.avatar">-->
    <!--<a id="upload" class="avatar">upload avatar</a>-->
    <div v-if="!!user">
      <h1> My Info </h1>
      <div>
        <div class="info-row">
          <h3>Username:</h3>
          {{user.username}}
        </div>
        <div class="info-row">
          <h3>Name:</h3>
          {{user.name}}
        </div>
        <div class="info-row">
          <h3>Age:</h3>
          {{user.age}}
        </div>
        <div class="info-row">
          <h3>Major:</h3>
          {{user.major}}
        </div>
        <div class="info-row">
          <h3>Email:</h3>
          {{user.email}}
        </div>
        <div class="info-row">
          <h3>Phone:</h3>
          {{user.phone}}
        </div>
        <div class="info-row">
          <h3>Description:</h3>
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

  .info-row h3 {
    width: 120px;
    text-align: start;
  }
</style>
