<template xmlns:v-bind="http://www.w3.org/1999/xhtml" xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="container">
    <div class="col-sm-3">
      <h3>Quick Borrow</h3>
      <form v-on:submit.prevent="quickBorrow">
        <!--<label for="borrow_trace">barcode</label>-->
        <input v-model="quickBorrowForm.trace" type="text" id="borrow_trace" class="form-control" placeholder="barcode" required/>
        <!--<label for="borrow_username">username</label>-->
        <input v-model="quickBorrowForm.username" type="text" id="borrow_username" class="form-control" placeholder="username" required/>
        <button type="submit" class="btn btn-default btn-block">submit</button>
      </form>
      <h3>Quick Return</h3>
      <form v-on:submit.prevent="quickReturn">
        <!--<label for="return_trace">barcode</label>-->
        <input v-model="quickReturnForm.trace" type="text" id="return_trace" class="form-control" placeholder="barcode" required/>
        <button type="submit" class="btn btn-default btn-block">submit</button>
      </form>
    </div>
    <div class="col-sm-9">
      <input class="input-search" autofocus autocomplete="off" placeholder="Search by username here"
             v-model="searchInput"/>
      <div class="btn-toolbar">
        <div class="btn-group">
          <button type="button" class="btn btn-primary" v-bind:class="{active: activeTab === 0}" @click="tab(0)">
            APPLYING &amp; RESERVING
          </button>
          <button type="button" class="btn btn-success" v-bind:class="{active: activeTab === 1}" @click="tab(1)">ACTIVE
            BORROWS
          </button>
          <button type="button" class="btn btn-default" v-bind:class="{active: activeTab === 3}" @click="tab(3)">
            RETURNED RECORDS
          </button>
          <button type="button" class="btn btn-default" v-bind:class="{active: activeTab === 2}" @click="tab(2)">
            OTHERS
          </button>
        </div>
      </div>
      <br/>
      <table class="table table-bordered table-condensed table-hover">
        <thead>
        <tr>
          <th>book trace</th>
          <th>user</th>
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
                {{ borrow.trace.book.name }}, {{ borrow.trace.book.publisher }}
                <br/>
                by {{ borrow.trace.book.authors.join(', ') }}
              </li>
              <li>No.{{ borrow.trace.id }} on {{ borrow.trace.location }}</li>
            </ul>
          </td>
          <td>
            {{ borrow.user.username }}
          </td>
          <td>
            {{ borrow.status }}
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
              <button class="btn btn-success btn-block btn-xs" @click="acceptApplication(borrow)">accept</button>
              <button class="btn btn-danger btn-block btn-xs" @click="rejectApplication(borrow)">reject</button>
            </template>
            <template v-if="borrow.status === 'ACTIVE'">
              <button class="btn btn-success btn-block btn-xs" @click="confirmReturned(borrow)">confirm returned</button>
              <!--<button class="btn btn-danger btn-block btn-xs" @click="confirmDisabled(borrow)">confirm disabled</button>-->
            </template>
          </td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="5" style="text-align: center">empty.</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
  import * as service from '../../service';

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

  export default {
    data () {
      return {
        quickBorrowForm: {},
        quickReturnForm: {},
        searchInput: '',
        activeTab: 0,

        DUMP: {}
      }
    },
    beforeMount() {
      this.$store.dispatch('ADMIN_FETCH_BORROWS');
    },
    watch: {
    },
    computed: {
      filteredBorrows() {
        let self = this;
        return this.$store.state.admin_borrows.filter(e => self.isBorrowVisible(e));
      }
    },
    methods: {
      datetime(prefix, timestamp) {
        if (timestamp) {
          let date = new Date(timestamp);
          let year = date.getFullYear();
          let month = date.getMonth() + 1;
          let day = date.getDate();
          let hour = date.getHours();
          let minute = date.getMinutes();
          let second = date.getSeconds();
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
        let pattern = this.searchInput.toLowerCase().trim();
        return e && inArray(board[this.activeTab], e.status) && pattern.length && e.user.username.toLowerCase().indexOf(pattern) !== -1;
      },
      quickBorrow() {
        if (this.quickBorrowForm.trace) {
          let pos = this.quickBorrowForm.trace.indexOf(':');
          if (pos !== -1) {
            this.$store.dispatch('ADMIN_QUICK_BORROW', {
              isbn: this.quickBorrowForm.trace.substring(0, pos),
              traceId: this.quickBorrowForm.trace.substring(pos),
              username: this.quickBorrowForm.username
            });
          }
        }
      },
      quickReturn() {
        if (this.quickReturnForm.trace) {
          let pos = this.quickReturnForm.trace.indexOf(':');
          if (pos !== -1) {
            this.$store.dispatch('ADMIN_QUICK_RETURN', {
              isbn: this.quickReturnForm.trace.substring(0, pos),
              traceId: this.quickReturnForm.trace.substring(pos)
            });
          }
        }
      },
      acceptApplication(borrow) {
        this.$store.dispatch('ADMIN_ACCEPT_APPLICATION', borrow);
      },
      rejectApplication(borrow) {
        this.$store.dispatch('ADMIN_REJECT_APPLICATION', borrow);
      },
      confirmReturned(borrow) {
        this.$store.dispatch('ADMIN_CONFIRM_RETURNED', borrow);
      },
      confirmDisabled(borrow) {
        this.$store.dispatch('ADMIN_CONFIRM_DISABLED', borrow);
      },

      DUMP() {
      }
    }
  };
</script>

<style scoped>

  form label {
    margin-top: 5px;
  }

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
