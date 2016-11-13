<template>
  <div class="trace-list">
    <div v-if="traces && traces.length">
      <table class="table table-bordered table-condensed table-hover">
        <thead>
        <tr>
          <th>trace</th>
          <th>status</th>
          <th>operation</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="trace in traces" v-show="isTraceVisible(trace)">
          <td>No.{{ trace.id }} on "{{ trace.location }}"</td>
          <td>{{ trace.status }}</td>
          <td>
            <template v-if="isLogin">
              <button v-show="isTraceBorrowable(trace)" @click="borrow(trace)" class="">
                borrow
              </button>
              <template v-if="trace.status === 'BORROWED'">
                <button @click="reserve(trace)" v-show="isTraceReservable(trace)">
                  reserve
                </button>
              </template>
            </template>
            <template v-else>
              Please login first.
            </template>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <template v-else>
      no book trace yet.
    </template>
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

  export default {
    props: [
      'traces'
    ],
    watch: {},
    computed: {

      isLogin() {
        return this.$store.getters.isLogin;
      },

      isReachLimit() {
        return this.$store.getters.getAccount.loanLimit === 0;
      },

      DUMP() {
      }

    },
    methods: {
      isTraceVisible: function(e) {
        return e && inArray(['NORMAL', 'LOCKED', 'BORROWED'], e.status);
      },
      isTraceBorrowable: function(trace) {
        return trace.status === 'NORMAL';
      },
      isTraceReservable: function(trace) {
        return !!trace.isReservable && this.$store.account.id !== trace.loan.userId;
      },
      borrow: function(trace) {
        service.borrowBookTrace(trace).then(response => {
          if (response.data.success) {
            trace.status = 'LOCKED';
            alert('book trace is locked successfully, please ask the administrator to check in 2 hours.');
          } else {
            alert(response.data.error);
          }
        }).catch(e => {
          alert(e);
        });
      },
      reserve: function(trace) {
        service.reserveBookTrace(trace).then(response => {
          if (response.data.success) {
            trace.isReservable = false;
            alert('book trace is reserved successfully, you\'ll receive our notice mail after it is returned.');
          } else {
            alert(response.data.error);
          }
        }).catch(e => {
          alert(e);
        });
      }
    }
  }
</script>
