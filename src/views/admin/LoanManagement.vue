<template>
  <div id="loan-book-panel">
      <input class="input-search" autofocus autocomplete="off" placeholder="Search books or users here"
             v-model="searchInput" @keyup.enter="search"/>
      <ul>
        <li v-for="lend in filterLends"
            :key="lend.id">
          <div class="book-item" @click="selectBook(book)"
          >

            <h4 id="title">《{{lend.trace.book.name}}》
              <small>ISBN: {{lend.trace.isbn}}</small>
            </h4>

            <div id="bottom">
              <h6 id="isbn" class="info-item">User: {{lend.user.name}}({{lend.user.username}})</h6>
              <h6 class="info-item">Apply Time: {{new Date(lend.applyingTime)}}</h6>
              <span class="info-item"> Status: {{lend.status}}</span>

              <div class="operation">
                <button v-if="lend.status === 'APPLYING'" @click="acceptLendApplying(lend)">accept</button>
                <button v-if="lend.status === 'APPLYING'" @click="rejectLendApplying(lend)">reject</button>
                <button v-if="lend.status === 'ACTIVE' || lend.status === 'LATE'" @click="confirmReturn(lend)">return</button>
              </div>
            </div>
          </div>

        </li>
      </ul>
    </div>
</template>

<script>
  import * as service from '../../service'

  export default {
    data () {
      return {
        newBook: {
          name: '',
          isbn: '',
          total: null,
          margin: null
        },
        loadingTrace: false,
        newTrace: {},
        allLends: [],
        searchInput: '',
        currentSelectBook: null,
        currentTrace: []
      }
    },
    methods: {
      acceptLendApplying (lend) {
        service.acceptLendByAdmin(lend).then(response => {
          if (response.data.success) {
            lend.status = "ACTIVE"
          } else {
            alert('accept fail!')
          }
        }).catch(function (e) {
          alert('accept fail!')
        })
        return false
      },
      rejectLendApplying (lend) {
        service.rejectLendByAdmin(lend).then(response => {
          if (response.data.success) {
          lend.status = "REJECTED"
        } else {
          alert('reject fail!')
        }
      }).catch(function (e) {
          alert('reject fail!')
        })
        return false
      },
      confirmReturn (lend) {
        service.confirmReturnLendByAdmin(lend).then(response => {
          if (response.data.success) {
          lend.status = "RETURNED"
        } else {
          alert('return fail!')
        }
      }).catch(function (e) {
          alert('return fail!')
        })
        return false
      },
      selectBook (book) {
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
      },
      addTrace () {
        if (this.loadingTrace) {
          return
        }
        service.addBookTraceByAdmin(this.currentSelectBook, this.newTrace).then((response) => {
          this.newTrace = {}
          if (!response.data.success) {
            alert('add trace failed!')
          } else {
            self.currentTrace.add(response.data.entity)
          }
        }).catch(function (error) {
          this.newTrace = {}
          alert('add trace failed!' + error)
        })
      },
      deleteTrace (trace) {
        service.deleteBookTraceByAdmin(trace).then(function (response) {
          if (response.data.success) {
            trace.status = "DELETED"
          } else {
            alert('delete failed!')
          }
        }).catch(function (error) {
          alert('delete failed!' + error)
        })
      },
      borrowTrace (trace) {
        service.borrowBookTraceByAdmin(trace).then(function (response) {
          if (response.data.success) {
            trace.status = "BORROWED"
          } else {
            alert('borrow failed!')
          }
        }).catch(function (error) {
          alert('borrow failed!' + error)
        })
      }
    },
    watch: {
      searchInput: function (newInput) {
//        this.currentSelectBook = null
//        this.filterBooks = this.$store.state.books.filter(function (book) {
//          return book.name.toLowerCase().indexOf(newInput.toLowerCase().trim()) !== -1
//        })
//        alert(JSON.stringify(this.filterBooks))
      }
    },
    computed: {
      filterLends: function () {
        return this.allLends.filter(lend => (lend.trace.book.name.toLowerCase().indexOf(this.searchInput.toLowerCase().trim()) !== -1 ||
          lend.user.name.toLowerCase().indexOf(this.searchInput.toLowerCase().trim()) !== -1 ||
          lend.user.username.toLowerCase().indexOf(this.searchInput.toLowerCase().trim()) !== -1))
      }
    },
    beforeMount () {
      service.getLendsByAdmin().then(response => {
        if (response.data.success) {
          this.allLends = response.data.entities
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

  .info-item {
    margin-left: 10px;
    margin-right: 10px;
  }

</style>
