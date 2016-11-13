<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div class="container">
    <div class="col-sm-12">
      <input class="input-search" autofocus autocomplete="off" placeholder="Search by username here"
             v-model="searchInput"/>
      <table class="table table-bordered table-condensed table-hover">
        <thead>
        <tr>
          <th>No.</th>
          <th>username</th>
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
  import * as service from '../../service'

  export default {
    data () {
      return {
        newBook: {
          name: '', isbn: '', total: null, margin: null
        },
        loadingTrace: false,
        newTrace: {},
        filterBooks: [],
        searchInput: '',
        currentSelectBook: null,
        currentTrace: []
      }
    }, methods: {
      addNewBook () {
        let self = this;
        service.addBookByAdmin(this.newBook).then(function(response) {
          if (response.data.success) {
            self.$store.dispatch('ADD_NEW_BOOK', self.newBook)
            //            if (self.newBook.name.indexOf(self.searchInput) !== -1) {
            //              self.$store.state.books.unshift(self.newBook)
            //            }
            self.newBook = {}
          } else {
            alert('add fail!')
          }
        }).catch(function(e) {
          alert('add fail!')
        });
        return false
      }, deleteBook (book) {
        let self = this
        service.deleteBookByAdmin(book.isbn).then(function(response) {
          if (response.data.success) {
            self.$store.dispatch('FETCH_ALL_BOOKS_BY_ADMIN')
          } else {
            alert('delete book fail!')
          }
        }).catch(function(e) {
          alert(e)
          alert('delete book fail!')
        })
      }, selectBook (book) {
        if (this.loadingTrace) {
          return
        }

        if (this.currentSelectBook === book) {
          this.currentTrace = null
          this.currentSelectBook = null
        } else {
          this.currentSelectBook = book
          this.loadingTrace = true
          service.getBookTraceByAdmin(book.isbn).then(response => {
            this.loadingTrace = false
            if (response.data.success) {
              this.currentTrace = response.data.entities
            } else {
              alert('fetch failed!')
            }
          }).catch(error => {
            this.loadingTrace = false
            alert('fetch failed!' + error)
          })
        }
      }, addTrace () {
        if (this.loadingTrace) {
          return
        }

        service.addBookTraceByAdmin(this.currentSelectBook, this.newTrace).then((response) => {
          this.newTrace = {}
          if (!response.data.success) {
            alert('add trace failed!')
          } else {
            this.currentTrace.push(response.data.entity)
          }
        }).catch((error) => {
          this.newTrace = {}
          alert('add trace failed!' + error)
        })
      }, deleteTrace (trace) {
        service.deleteBookTraceByAdmin(trace).then(function(response) {
          if (response.data.success) {
            trace.status = "DELETED"
          } else {
            alert('delete failed!')
          }
        }).catch(function(error) {
          alert('delete failed!' + error)
        })
      }, borrowTrace (trace) {
        service.borrowBookTraceByAdmin(trace).then(function(response) {
          if (response.data.success) {
            trace.status = "BORROWED"
          } else {
            alert('borrow failed!')
          }
        }).catch(function(error) {
          alert('borrow failed!' + error)
        })
      }
    }, watch: {
      searchInput: function(newInput) {
        this.currentSelectBook = null;
        this.filterBooks = this.$store.state.books.filter(function(book) {
          return book.name && book.name.toLowerCase().indexOf(newInput.toLowerCase().trim()) !== -1
        })
      }
    }, computed: {
      allBooks: function() {
        return this.$store.state.books
      }
    },
    beforeMount () {
      this.$store.dispatch('FETCH_ALL_BOOKS_BY_ADMIN').then(() => {
        if (!this.searchInput) {
          this.filterBooks = this.$store.state.books
        }
      })
    }
  };
</script>

<style scoped>

  ul {
    list-style: none;
  }

  #manage-book-panel {
    display: flex;
    flex-direction: row;
    align-self: stretch;
    height: 100%;
    padding: 20px;
  }

  #add-book-panel {
    height: 100%;
    padding: 16px;
    padding-left: 50px;
    flex-grow: 1;
  }

  #book-list {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    align-self: stretch;
    flex-grow: 14;
  }

  #trace-list {
    width: 100%;
  }

  .operation {
    align-self: flex-end;
    margin: 4px;
  }

  .delete {
    color: #af5b5e;
  }

  .book {
    box-shadow: #2c3e50;
    display: flex;
    flex-direction: column;
    margin: 20px;
    height: auto;
    min-width: 300px;
    width: 100%;
    padding: 20px;
    align-items: flex-start;
    box-shadow: inset 0 -2px 2px rgba(0, 0, 0, 0.03);
  }

  .trace-item {
    display: flex;
    padding-top: 8px;
    padding-bottom: 8px;
    width: 100%;
  }

  .trace-item-info {
    min-width: 80px;
    text-align: start;
    margin-left: 10px;
    margin-right: 10px;
  }

  .book-item {
    display: flex;
    flex-direction: column;
    margin: 20px;
    height: auto;
    min-width: 300px;
    width: 100%;
    align-items: flex-start;
  }

  .book #title {
    align-self: flex-start;
  }

  #header {
    display: flex;
    align-items: flex-end;
  }

  #bottom {
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    align-self: stretch;
    justify-content: space-between;
  }

  #delete {
    height: 20px;
    align-self: flex-end;
    color: #af5b5e;
    margin-left: 100px;
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
    border: 1px solid #999999;
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

  #add-book-panel form {
    display: flex;
    flex-direction: column;
  }

  .trace-item-operation {
    width: 100%;
    display: flex;
    align-items: flex-end;
  }

  .selected-item {
    border: dashed #42b983;
  }

  .selected-item-info {
    color: #ffffff;
  }

  .operation-title {
    margin-top: 50px;
  }

</style>
