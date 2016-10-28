<template>
  <div id="manage-book-panel">
    <div id="add-book-panel">
      <div class="operation-title">Add a book</div>
      <form v-on:submit.prevent="addNewBook">
        <input placeholder="book name" v-model="newBook.name" type="text">
        <input placeholder="ISBN" v-model="newBook.isbn" type="text">
        <input placeholder="desc" v-model="newBook.desc" type="text">
        <button type="submit" class="action-button">Add</button>
      </form>
      <div v-if="!!currentSelectBook">
        <div class="operation-title">Add a trace for 《{{currentSelectBook.name}}》</div>
        <form v-on:submit.prevent="addTrace">
          <input placeholder="location" v-model="newTrace.location" type="text">
          <button type="submit" class="action-button">Add</button>
        </form>
      </div>
    </div>
    <div id="book-list">
      <input class="input-search" autofocus autocomplete="off" placeholder="Search books here"
             v-model="searchInput" @keyup.enter="search"/>
      <ul>
        <li v-for="book in filterBooks"
            :key="book.isbn">
          <div class="book-item" @click="selectBook(book)"
               v-bind:class="{ 'selected-item': currentSelectBook === book, book: true}"
          >

            <h4 id="title">《{{book.name}}》
              <small>{{book.margin}} / {{book.total}}</small>
            </h4>

            <div id="bottom">
              <h6 id="isbn">ISBN: {{book.isbn}}</h6>

              <a v-if="currentSelectBook === book" style="{color: #ffffff;}">hide</a>
              <a v-else>show detail</a>

            </div>
          </div>
          <div v-if="currentSelectBook == book">
            <ul v-if="!loadingTrace" id="trace-list">
              <li v-for="trace in currentTrace" class="trace-item">
                <span class="trace-item-info">id: {{trace.id}}</span>
                <span class="trace-item-info">     status: {{trace.status}}</span>
                <span class="trace-item-info">     location: {{trace.location}}</span>
                <div class="space"></div>
                <div class="trace-item-operation">
                  <button class="operation delete" v-if="trace.status !== 'DELETED'" @click="deleteTrace(trace)">delete
                  </button>
                  <!--<button class="operation borrow" v-if="trace.status === 'LOCKED'" @click="borrowTrace(trace)">borrow-->
                  <!--</button>-->
                </div>
              </li>
              <div v-if=" currentTrace && currentTrace.length=== 0">
                No traces.
              </div>
            </ul>
            <div v-else>
                Loading...
            </div>
          </div>

        </li>
      </ul>
    </div>
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
        filterBooks: [],
        searchInput: '',
        currentSelectBook: null,
        currentTrace: []
      }
    },
    methods: {
      addNewBook () {
        let self = this
        service.addBookByAdmin(this.newBook).then(function (response) {
          if (response.data.success) {
            self.$store.dispatch('ADD_NEW_BOOK', self.newBook)
            if (self.newBook.name.indexOf(self.searchInput) !== -1) {
              self.filterBooks.unshift(self.newBook)
            }
            self.newBook = {}
          } else {
            alert('add fail!')
          }
        }).catch(function (e) {
          alert('add fail!')
        })
        return false
      },
      deleteBook (book) {
        let self = this
        service.deleteBookByAdmin(book.isbn).then(function (response) {
          if (response.data.success) {
            self.$store.dispatch('FETCH_ALL_BOOKS_BY_ADMIN')
          } else {
            alert('delete book fail!')
          }
        }).catch(function (e) {
          alert(e)
          alert('delete book fail!')
        })
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
        this.currentSelectBook = null
        this.filterBooks = this.$store.state.books.filter(function (book) {
          return book.name.toLowerCase().indexOf(newInput.toLowerCase().trim()) !== -1
        })
//        alert(JSON.stringify(this.filterBooks))
      }
    },
    computed: {
      allBooks: function () {
        return this.$store.state.books
      }
    },
    beforeMount () {
      this.$store.dispatch('FETCH_ALL_BOOKS_BY_ADMIN')
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

</style>
