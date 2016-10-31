<template>
  <div class="book-page">
    <input class="input-search" autofocus autocomplete="off" placeholder="Search books here"
           v-model="searchInput" @keyup.enter="search"/>
    <section class="main" v-show="searchBooks.length" v-cloak>
      <ul class="book-list">
        <li v-for="book in searchBooks" class="book" :key="book.isbn">
          <div class="view" @click="selectBook(book)">
            <div>
              <h4>《{{ book.name }}》
                <small>ISBN: {{ book.isbn }}</small>
              </h4>
              <div>
                <h8>
                  {{ book.desc }}
                </h8>
              </div>
              <a class="desc" v-if="currentSelectBook === book">⬇hide⬇️</a>
              <a v-else>⬆️️ click to ️show detail️ ️️️️⬆️️</a>
            </div>

          </div>
          <div v-if="currentSelectBook === book">
            <ul v-if="currentBookTrace && currentBookTrace.length">
              <li v-for="trace in currentBookTrace" v-if="showTrace(trace)">
                <span class="item-info">
                  location: {{trace.location}}
                </span>
                <button v-if="traceBorrowable(trace)" @click="borrow(trace)">
                  Borrow
                </button>
                <button v-if="traceReservable(trace)" @click="reservation(trace)">
                  Reserve
                </button>
                <a v-if="trace.status === 'RESERVATION'">
                  has reserved
                </a>
              </li>
            </ul>
            <div v-if="currentBookTrace && currentBookTrace.length === 0">
              no tracks
            </div>
          </div>

        </li>
      </ul>
    </section>
  </div>
</template>

<script>
  import * as service from '../service'

  function removeByValue(arr, val) {
    for (var i = 0; i < arr.length; i++) {
      if (arr[i] === val) {
        arr.splice(i, 1);
        break;
      }
    }
  }

  export default {
    data() {
      return {
        searchBooks: [],
        searchInput: '',
        currentSelectBook: null,
        currentBookTrace: []
      }
    },
    watch: {},
    computed: {},
    methods: {
      traceBorrowable: function (trace) {
        return trace.status === 'NORMAL'
      },
      traceReservable: function (trace) {
        return trace.status === 'BORROWED'
      },
      showTrace: function (trace) {
        return trace.status === 'NORMAL' || trace.status === 'BORROWED' || trace.status === 'RESERVATION'
      },
      isBookSelected: function (book) {
        return this.currentSelectBook === book
      },
      selectBook: function (book) {
        if (this.currentSelectBook === book) {
          this.currentSelectBook = null
        } else {
          this.currentBookTrace = null
          this.currentSelectBook = book
          let self = this
          service.getBookTraces(book.isbn).then(function (response) {
            if (response.data.success) {
              self.currentBookTrace = response.data.entities
            } else {
              alert('fetch error')
            }
          }).catch(function (e) {
            alert(e)
          })
        }
      },
      search: function () {
        let self = this;
        const value = self.searchInput && self.searchInput.trim();
        if (!value) {
          self.$store.dispatch('ON_SEARCH_BOOKS', []);
          return;
        }
        const params = {
          'ISBN': value,
          'name': value
        };
        service.searchBook(params).then(function (response) {
          console.log(response.data.entities);
          self.searchBooks = response.data.entities;
          self.$store.dispatch('ON_SEARCH_BOOKS', self.searchBooks);
        }).catch(function (error) {
          console.error(error);
        });
      },
      borrow: function (trace) {
        let self = this
        service.lendBookTrace(trace).then(function (response) {
          const success = response.data.success;
          if (success) {
            alert('lend success！');
            removeByValue(self.currentBookTrace, trace)
          } else {
            alert('lend fail！');
          }
        }).catch(function (error) {
          alert('lend fail！' + error);
        });
      },
      reservation: function (trace) {
        let self = this
        service.reserveBookTrace(trace).then(function (response) {
          const success = response.data.success;
          if (success) {
            alert('reserve success！');
//            removeByValue(self.currentBookTrace, trace)
            trace.status = 'RESERVATION'
          } else {
            alert('reserve fail！');
          }
        }).catch(function (error) {
          alert('reserve success！' + error);
        });
      }
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

  .book-page {
    background: #fff;
    margin: 0;
    position: relative;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2),
    0 25px 50px 0 rgba(0, 0, 0, 0.1);
  }

  .book-page input::-webkit-input-placeholder {
    font-style: italic;
    font-weight: 300;
    color: #e6e6e6;
  }

  .book-page input::-moz-placeholder {
    font-style: italic;
    font-weight: 300;
    color: #e6e6e6;
  }

  .book-page input::input-placeholder {
    font-style: italic;
    font-weight: 300;
    color: #e6e6e6;
  }

  .book-page h1 {
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

  .view {
    width: 100%;
    height: 100%;
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
    z-index: 1;
    border-top: 1px solid #e6e6e6;
  }

  .book-list {
    margin: 0;
    padding: 0;
    list-style: none;
  }

  .book-list li {
    position: relative;
    font-size: 18px;
    border-bottom: 1px solid #ededed;
    padding: 20px 0px 20px 0px;
  }

  .book-list li:last-child {
    border-bottom: none;
  }

  .book-list li label {
    word-break: break-all;
    display: block;
    line-height: 1.2;
    transition: color 0.4s;
  }

  .book-list li span.small {
    font-size: small;
  }

  .item-info {
    min-width: 100px;
    margin-left: 10px;
    margin-right: 10px;
  }

</style>
