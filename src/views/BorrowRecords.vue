<template>
  <div class="record-page">
    <!--<button id="btn-refresh" class="btn btn-default btn-block" @click="refresh()"> Refresh </button>-->
    <section class="main" v-if="borrowRecords && borrowRecords.length" v-cloak>
      <h2 v-if="reservations && reservations.length">My Reservations ({{reservations.length}})</h2>
      <ul class="record-list">
        <li v-for="borrowRecord in reservations" class="trace-item">
          <div>
            <h4>
              《{{borrowRecord.trace.book.name}}》
              <small>ISBN: {{borrowRecord.trace.isbn}}</small>
            </h4>
          </div>
          <div>
            <span class="trace-item-info">id: {{borrowRecord.id}}</span>
            <span class="trace-item-info">     location: {{borrowRecord.location}}</span>
            <span class="trace-item-info">     status: {{borrowRecord.status}}</span>
            <span class="trace-item-info">
              Applying Time: {{new Date(borrowRecord.applyingTime)}}</span>
            <!--<div class="space"></div>-->
            <span >
              <button class="operation delete" v-if="borrowRecord.status === 'WAITING'"
                      @click="cancelReserve(borrowRecord)">CANCEL</button>
              <!--<button class="operation borrow" v-if="trace.status === 'LOCKED'" @click="borrowTrace(trace)">borrow-->
              <!--</button>-->
            </span>
          </div>
        </li>
      </ul>

      <h2 v-if="borrowRecords && borrowRecords.length">My Borrow Record ({{borrowRecords.length}})</h2>
      <ul class="record-list">
        <li v-for="borrowRecord in borrowRecords" class="trace-item">
          <div>
            <h4>
              《{{borrowRecord.trace.book.name}}》
              <small>ISBN: {{borrowRecord.trace.isbn}}</small>
            </h4>
          </div>
          <div>
            <span class="trace-item-info">id: {{borrowRecord.id}}</span>
            <span class="trace-item-info">     location: {{borrowRecord.location}}</span>
            <span class="trace-item-info">     status: {{borrowRecord.status}}</span>
            <span class="trace-item-info" v-if="borrowRecord.status === 'APPLYING'">
              Applying Time: {{new Date(borrowRecord.applyingTime)}}</span>
            <!--<div class="space"></div>-->
            <span >
              <button class="operation" v-if="borrowRecord.status === 'ACTIVE'"
                      @click="renew(borrowRecord)">RENEW</button>
                <button class="operation delete" v-if="borrowRecord.status === 'APPLYING'"
                        @click="cancelApplying(borrowRecord)">CANCEL</button>
              <!--<button class="operation borrow" v-if="trace.status === 'LOCKED'" @click="borrowTrace(trace)">borrow-->
              <!--</button>-->
            </span>
          </div>
        </li>
      </ul>
    </section>
    <section class="main" v-if="borrowRecords && !borrowRecords.length">
      You have no records.
    </section>
  </div>
</template>

<script>
  import * as service from '../service'

  export default {
    data() {
      return {
        borrowRecords: [],
        reservations: []
      }
    },
    watch: {},
    methods: {
      cancelApplying: function (lend) {
        service.cancelApplying(lend).then((response) => {
          lend.status = 'CANCELED'
        }).catch(function (error) {
          console.error(error);
        });
      },
      renew: function (lend) {
        service.renewBook(lend).then((response) => {
          lend.status = 'CANCELED'
      }).catch(function (error) {
          console.error(error);
        });
      },
      cancelReserve: function (lend) {
        service.cancelReserve(lend).then((response) => {
          lend.status = 'CANCELED'
      }).catch(function (error) {
          console.error(error);
        });
      },
      refresh: function () {
        let self = this;
        service.getBorrowRecords().then(function (response) {
          console.log(response.data.entities);
          self.borrowRecords = response.data.entities;
          self.$store.dispatch('ON_LIST_BORROW_RECORDS', self.borrowRecords);
        }).catch(function (error) {
          console.error(error);
        });
      },
      revert: function (record) {
        service.returnBook(record.book.isbn).then(function (response) {
          const success = response.data.success;
          if (success) {
            record.status = 'RETURNED';
            record.returnTime = new Date();
          } else {
            alert('还书失败！');
          }
        }).catch(function (error) {
          alert('还书失败！');
          console.error(error);
        });
      }
    },
    created: function () {
      service.getBorrowRecords().then((response) => {
        this.borrowRecords = response.data.entities;
      this.$store.dispatch('ON_LIST_BORROW_RECORDS', self.borrowRecords);
    }).catch(function (error) {
        console.error(error);
      });

      service.getReservation().then((response) => {
        this.reservations = response.data.entities;
    }).catch(function (error) {
        console.error(error);
      });
    }
  }
</script>

<style scoped>

  h1 {
    color: #42b983;
  }

  html,
  body {
    margin: 0;
    padding: 0;
  }

  body {
    font: 14px 'Helvetica Neue', Helvetica, Arial, sans-serif;
    line-height: 1.4em;
    background: #f5f5f5;
    color: #4d4d4d;
    min-width: 230px;
    max-width: 550px;
    margin: 0 auto;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    font-weight: 300;
  }

  :focus {
    outline: 0;
  }

  .hidden {
    display: none;
  }

  .record-page {
    background: #fff;
    margin: 0;
    position: relative;
    /*box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2),*/
    /*0 25px 50px 0 rgba(0, 0, 0, 0.1);*/
  }

  .record-page input::-webkit-input-placeholder {
    font-style: italic;
    font-weight: 300;
    color: #e6e6e6;
  }

  .record-page input::-moz-placeholder {
    font-style: italic;
    font-weight: 300;
    color: #e6e6e6;
  }

  .record-page input::input-placeholder {
    font-style: italic;
    font-weight: 300;
    color: #e6e6e6;
  }

  .record-page h1 {
    position: absolute;
    top: -155px;
    width: 100%;
    font-size: 100px;
    font-weight: 100;
    text-align: center;
    color: rgba(175, 47, 47, 0.15);
    -webkit-text-rendering: optimizeLegibility;
    -moz-text-rendering: optimizeLegibility;
    text-rendering: optimizeLegibility;
  }

  .input-search,
  .edit {
    position: relative;
    margin: 0;
    width: 100%;
    font-size: 24px;
    font-family: inherit;
    font-weight: inherit;
    line-height: 1.4em;
    color: inherit;
    padding: 6px;
    border: 1px solid #999;
    box-shadow: inset 0 -1px 5px 0 rgba(0, 0, 0, 0.2);
    box-sizing: border-box;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  .input-search {
    padding: 16px 16px 16px 60px;
    border: none;
    background: rgba(0, 0, 0, 0.003);
    box-shadow: inset 0 -2px 1px rgba(0, 0, 0, 0.03);
  }

  .main {
    position: relative;
    height: 100%;
    z-index: 2;
    border-top: 1px solid #e6e6e6;
  }

  .record-list {
    margin: 0;
    padding: 0;
    list-style: none;
  }

  .record-list li {
    position: relative;
    font-size: 18px;
    border-bottom: 1px solid #ededed;
    padding: 20px 0px 20px 0px;
  }

  .record-list li:last-child {
    border-bottom: none;
  }

  .record-list li label {
    word-break: break-all;
    display: block;
    line-height: 1.2;
    transition: color 0.4s;
  }

  .record-list li span.small {
    font-size: small;
  }

  .trace-item {
    display: flex;
    padding-top: 8px;
    padding-bottom: 8px;
    flex-direction: column;
    width: 100%;
  }

  .trace-item-operation {
    width: 100%;
    display: flex;
    align-items: flex-end;
  }

  .trace-item-info {
    min-width: 80px;
    text-align: start;
    margin-left: 10px;
    margin-right: 10px;
  }

</style>
