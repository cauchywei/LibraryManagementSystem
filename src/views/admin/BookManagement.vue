<template>
  <div id="manage-book-panel">
    <div id="add-book-panel">
      <div>Add a book</div>
      <form v-on:submit.prevent="addNewBook">
        <input placeholder="book name" v-model="newBook.name" type="text">
        <input placeholder="ISBN" v-model="newBook.isbn" type="text">
        <input placeholder="total" v-model="newBook.total" type="number">
        <input placeholder="margin" v-model="newBook.margin" type="number">
        <button type="submit" class="action-button">Add</button>
      </form>
    </div>
    <div id="book-list">
      <div v-for="book in allBooks"
           class="book"
           :key="book.isbn">
        <h4 id="title">《{{book.name}}》<small>{{book.margin}} / {{book.total}}</small></h4>

        <div id="bottom">
          <h6 id="isbn">ISBN: {{book.isbn}}</h6>

          <button @click="deleteBook(book)" class="btn btn-danger btn-delete">delete</button>
        </div>
      </div>
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
        }
      }
    },
    methods: {
      addNewBook () {
        let self = this
        service.addBookByAdmin(this.newBook).then(function (response) {
          if (response.data.success) {
            self.$store.dispatch('ADD_NEW_BOOK', self.newBook)
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
      }
    },
    watch: {},
    computed: {
      allBooks: function () {
        return this.$store.state.books
      }
    },
    beforeMount () {
      this.$store.dispatch('FETCH_ALL_BOOKS_BY_ADMIN')
    }
  }
</script>

<style scoped>

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
    align-items: center;
    align-self: stretch;
    flex-grow: 14;
  }

  .book {
    box-shadow: #2c3e50;
    display: flex;
    flex-direction: column;
    margin: 20px;
    width: 50%;
    padding: 20px;
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
    width: 20px;
    height: 20px;
    background: #ff0000;
    align-self: flex-end;
  }

  #add-book-panel form {
    display: flex;
    flex-direction: column;
  }

</style>
