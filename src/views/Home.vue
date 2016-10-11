<template>
  <div class="book-page">
    <input class="input-search" autofocus autocomplete="off" placeholder="Search books here"
           v-model="searchInput" @keyup.enter="search"/>
    <section class="main" v-show="searchBooks.length" v-cloak>
      <ul class="book-list">
        <li v-for="book in searchBooks" class="book" :key="book.isbn">
          <div class="view">
            <span>《{{ book.name }}》</span>
            <span class="small">
              [ISBN: {{ book.isbn }}]
              Total: {{book.total}}, Left: {{ book.margin }} .
            </span>
            <button class="btn btn-default" @click="borrow(book)"> Borrow </button>
          </div>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
  import * as service from '../service'

  export default {
    data() {
      return {
        searchBooks: [],
        searchInput: ''
      }
    },
    watch: {
    },
    methods: {
      search: function() {
        let self = this;
        const value = self.searchInput && self.searchInput.trim();
        if (!value) {
          return;
        }
        const params = {
          'ISBN': value,
          'name': value
        };
        service.searchBook(params).then(function (response) {
          self.searchBooks = response.data.entities;
          console.log(self.searchBooks);
          self.$store.dispatch('ON_SEARCH_BOOKS', self.searchBooks);
        }).catch(function (error) {
          console.error(error);
        });
      },
      borrow: function(book) {
        service.borrowBook(book.isbn).then(function (response) {
          const success = response.data.success;
          if (success) {
            book.margin -= 1;
          } else {
            alert('借书失败！');
          }
        }).catch(function (error) {
          console.error(error);
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
    box-shadow: inset 0 -2px 1px rgba(0,0,0,0.03);
  }

  .main {
    position: relative;
    z-index: 2;
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

</style>
