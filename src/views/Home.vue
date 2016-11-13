<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div class="book-page">
    <input class="input-search" autofocus autocomplete="off" placeholder="Search books here"
           v-model.trim="searchInput" @keyup.enter="search" />
    <hr/>
    <div class="container" v-cloak>
      <div class="panel panel-default">
        <div class="panel-heading">
          Search Results
        </div>
        <div class="panel-body">
          <div v-if="filteredBooks && filteredBooks.length" class="list-group">
            <div v-for="book in filteredBooks" v-bind:key="book.isbn"
                 class="list-group-item">
              <div class="media">
                <div class="media-left">
                  <img class="img-book" v-bind:src="book.imageUrl" />
                </div>
                <div class="media-body">
                  <h4 class="media-heading list-group-item-heading">
                    {{ book.name }}
                  </h4>
                  <table class="table table-bordered table-condensed table-hover">
                    <tbody>
                      <tr v-if="book.isbn">
                        <td>ISBN</td>
                        <td>{{ book.isbn }}</td>
                      </tr>
                      <tr v-if="book.publisher">
                        <td>Publisher</td>
                        <td>{{ book.publisher }}</td>
                      </tr>
                      <tr v-if="book.authors && book.authors.length">
                        <td>Authors</td>
                        <td>{{ book.authors.join(', ') }}</td>
                      </tr>
                      <tr v-if="book.desc">
                        <td>Description</td>
                        <td>{{ book.desc }}</td>
                      </tr>
                    </tbody>
                  </table>
                  <div>
                    <button class="btn btn-danger" v-if="isActiveBook(book)" @click="toggleBook(book)">Hide traces</button>
                    <button class="btn btn-primary" v-else @click="toggleBook(book)">Check traces</button>
                  </div>
                  <div v-show="isActiveBook(book)">
                    <br/>
                    <trace-list v-bind:traces="book.traces"></trace-list>
                  </div>
                </div>
              </div>

            </div>
          </div>
          <div v-else>
            no matched books.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue';
  import * as service from '../service';

  import BookTraceList from '../components/BookTraceList';

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
    data() {
      return {
        searchInput: '',
        current: null
      }
    },
    watch: {},
    computed: {

      filteredBooks: function() {
        let self = this;
        return this.$store.state.books.filter(e => self.isBookVisible(e));
      },

      DUMP: () => {}

    },
    methods: {
      search: function() {
        const value = this.searchInput;
        if (value) {
          this.$store.dispatch('SEARCH_BOOKS', {
            isbn: value,
            name: value
          });
        }
      },
      isBookVisible: function(e) {
        return e && inArray(['NORMAL'], e.status);
      },
      isActiveBook: function(book) {
        return book.active;
      },
      toggleBook: function(book) {
        Vue.set(book, 'active', !book.active);
        if (book.active) {
          this.$store.dispatch('FETCH_BOOK_TRACES', book);
        }
      },

      DUMP: function() {}
    },
    components: {

      "trace-list": BookTraceList

    }
  }
</script>

<style scoped>

  .input-search {
    padding: 8px 30px 0 40px;
    font-size: 16px;
    border: none;
    background: rgba(0, 0, 0, 0.003);
    outline: none;
    width: 100%;
  }

  .img-book {
    width: 100px;
    height: 150px;
    border: 1px solid #eee;
  }

  tr > td:first-child {
    width: 100px;
  }

</style>
