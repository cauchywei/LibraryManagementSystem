<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div class="container" v-cloak>
    <h2>My Borrows</h2>
    <div class="btn-toolbar">
      <div class="btn-group">
        <button type="button" class="btn btn-primary" v-bind:class="{active: activeTab === 0}" @click="tab(0)">APPLYING &amp; RESERVING</button>
        <button type="button" class="btn btn-success" v-bind:class="{active: activeTab === 1}" @click="tab(1)">ACTIVE BORROWS</button>
        <button type="button" class="btn btn-default" v-bind:class="{active: activeTab === 3}" @click="tab(3)">RETURNED RECORDS</button>
        <button type="button" class="btn btn-default"  v-bind:class="{active: activeTab === 2}" @click="tab(2)">OTHERS</button>
      </div>
    </div>
    <br/>
    <table class="table table-bordered table-condensed table-hover">
      <thead>
      <tr>
        <th>book trace</th>
        <th>status</th>
        <th>info</th>
        <th>operation</th>
      </tr>
      </thead>
      <tbody v-if="filteredBorrows && filteredBorrows.length">
      <tr v-for="borrow in filteredBorrows">
        <td>
          <ul>
            <li>
              {{ borrow.trace.book.name }} (ISBN: {{ borrow.trace.book.isbn }})
            </li>
            <li>
              No.{{ borrow.trace.id }} from {{ borrow.trace.location}}
            </li>
            <li>
              <img v-bind:src="'http://barcode.tec-it.com/barcode.ashx?data='+borrow.trace.isbn+':'+borrow.traceId+'&code=Code128&dpi=96'"/>
            </li>
          </ul>
        </td>
        <td>
          <ul>
            <li>Borrow No.{{ borrow.id }}</li>
            <li>Status: {{ borrow.status }}</li>
          </ul>
        </td>
        <td>
          <ul>
            <li v-if="borrow.applyingTime">{{ datetime('applied at', borrow.applyingTime) }}</li>
            <li v-if="borrow.expiredTime">{{ datetime('expire at', borrow.expiredTime) }}</li>
            <li v-if="borrow.activeTime">{{ datetime('active at', borrow.activeTime) }}</li>
            <li v-if="borrow.appointedTime">{{ datetime('appointed until', borrow.appointedTime) }}</li>
          </ul>
        </td>
        <td>
          <template v-if="borrow.status === 'APPLYING'">
            <button class="btn btn-danger btn-block btn-xs" @click="cancelApplication(borrow)">cancel</button>
          </template>
          <template v-if="borrow.status === 'RESERVING'">
            <button class="btn btn-danger btn-block btn-xs" @click="cancelReservation(borrow)">cancel</button>
          </template>
          <template v-if="borrow.status === 'ACTIVE' && borrow.renewCount === 0">
            <button class="btn btn-success btn-block btn-xs" @click="renewBorrow(borrow)">renew</button>
          </template>
        </td>
      </tr>
      </tbody>
      <tbody v-else>
      <tr>
        <td colspan="4" style="text-align: center">empty.</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import * as service from '../service';

  let inArray = (arr, element) => {
    if (arr instanceof Array) {
      for (let i in arr) {
        let e = arr[i];
        if (e === element) {
          return true;
        }
      }
      return false;
    }
    return undefined;
  };

  let prefixZero = (input, len) => {
    return (new Array(len).join('0') + input.toString()).slice(-len);
  };

  export default {
    data() {
      return {
        activeTab: 0,

        DUMP: null
      }
    },
    beforeMount() {
      this.$store.dispatch('FETCH_BORROWS');
    },
    watch: {
    },
    computed: {
      filteredBorrows() {
        let self = this;
        return this.$store.state.borrows.filter(e => self.isBorrowVisible(e));
      }
    },
    methods: {
      datetime(prefix, timestamp) {
        if (timestamp) {
          let date = new Date(timestamp);
          let year = date.getFullYear();
          let month = prefixZero(date.getMonth() + 1, 2);
          let day = prefixZero(date.getDate(), 2);
          let hour = prefixZero(date.getHours(), 2);
          let minute = prefixZero(date.getMinutes(), 2);
          let second = prefixZero(date.getSeconds(), 2);
          return prefix + " " + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        }
      },
      tab(index) {
        this.activeTab = index;
      },
      isBorrowVisible(e) {
        let board = {
          0: ['APPLYING', 'RESERVING'],
          1: ['ACTIVE'],
          3: ['RETURNED'],
          2: ['EXPIRED', 'CANCELED', 'REJECTED']
        };
        return e && inArray(board[this.activeTab], e.status);
      },
      cancelApplication(borrow) {
        this.$store.dispatch('CANCEL_APPLICATION', borrow);
      },
      cancelReservation(borrow) {
        this.$store.dispatch('CANCEL_RESERVATION', borrow);
      },
      renewBorrow(borrow) {
        this.$store.dispatch('RENEW_BORROW', borrow);
      },

      DUMP() {}

    }
  }
</script>

<style scoped>

  .input-search {
    padding: 8px 30px 10px 0px;
    font-size: 16px;
    border: none;
    background: rgba(0, 0, 0, 0.003);
    outline: none;
    width: 100%;
  }

  ul {
    -moz-padding-start: 20px;
    -webkit-padding-start: 20px;
  }

</style>
