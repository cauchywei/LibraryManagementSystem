<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div class="container">
    <div class="col-sm-4">
      <h3>Add Book</h3>
      <div class="">
        <form v-on:submit.prevent="fetchDouban(doubanISBN)">
          <input placeholder="ISBN" v-model="doubanISBN" type="text" class="form-control" required>
          <button type="submit" class="btn btn-primary btn-block">fetch from Douban API</button>
        </form>
        <br/>
        <form v-on:submit.prevent>
          <label for="new-isbn">ISBN</label>
          <input v-model="newBook.isbn" type="text" id="new-isbn" class="form-control" required>
          <label for="new-name">name</label>
          <input v-model="newBook.name" type="text" id="new-name" class="form-control" required>
          <label for="new-publisher">publisher</label>
          <input v-model="newBook.publisher" type="text" id="new-publisher" class="form-control">
          <label for="new-authors">authors (each start with #)</label>
          <input v-model="newBook.authors" type="text" id="new-authors" class="form-control">
          <label for="new-imageUrl">image URL</label>
          <input v-model="newBook.imageUrl" type="text" id="new-imageUrl" class="form-control">
          <label for="new-image">image (if no URL above)</label>
          <input v-on:change="setNewBookImage" type="file" id="new-image" class="form-control">
          <label for="new-desc">description</label>
          <textarea v-model="newBook.desc" id="new-desc" class="form-control" rows="5"></textarea>
          <br/>
          <button @click="addBook(newBook)" class="btn btn-success btn-block">add book</button>
        </form>
      </div>
    </div>
    <div class="col-sm-8">
      <input class="input-search" autofocus autocomplete="off" placeholder="Search by ISBN here"
             v-model="searchInput"/>
      <table class="table table-bordered table-condensed table-hover">
        <thead>
        <tr>
          <th>ISBN</th>
          <th>name</th>
          <th>publisher</th>
          <th>operation</th>
        </tr>
        </thead>
        <tbody v-if="filteredBooks && filteredBooks.length">
        <tr v-for="book in filteredBooks">
          <td>{{ book.isbn }}</td>
          <td>{{ book.name }}</td>
          <td>{{ book.publisher }}</td>
          <td>
            <button class="btn btn-default btn-block btn-xs" @click="showBookDetail(book)">detail</button>
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

    <div class="modal fade" id="modal-book-detail">
      <div class="modal-dialog modal-lg">
        <div class="modal-content" v-if="book">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
            <h4 class="modal-title">Detail</h4>
          </div>
          <div class="modal-body">
            <div class="media">
              <div class="media-left" v-if="book.imageUrl">
                <img class="img-book" v-bind:src="book.imageUrl"/>
              </div>
              <div class="media-body">
                <table class="table table-bordered table-condensed table-hover">
                  <tbody>
                  <tr v-if="book.isbn">
                    <td>ISBN</td>
                    <td>{{ book.isbn }}</td>
                  </tr>
                  <tr v-if="book.name">
                    <td>name</td>
                    <td>{{ book.name }}</td>
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
                  <tr>
                    <td>created at</td>
                    <td>{{ new Date(book.createTime).toLocaleDateString() }}</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div class="modal-body">
            <form v-on:submit.prevent="addTrace(newTrace)">
              <label for="new-trace-location">location</label>
              <input v-model="newTrace.location" type="text" class="form-control" id="new-trace-location" required/>
              <button type="submit" class="btn btn-primary btn-block">add book trace</button>
            </form>
            <br/>
            <table class="table table-bordered table-condensed table-hover">
              <thead>
              <tr>
                <th>trace</th>
                <th>barcode</th>
                <th>status</th>
                <th>created at</th>
                <th>operation</th>
              </tr>
              </thead>
              <tbody v-if="filteredTraces && filteredTraces.length">
              <tr v-for="trace in filteredTraces">
                <td>No.{{ trace.id }} on "{{ trace.location }}"</td>
                <td>
                  <img v-bind:src="'http://barcode.tec-it.com/barcode.ashx?data='+trace.isbn+':'+trace.id+'&code=Code128&dpi=96'"/>
                </td>
                <td>{{ trace.status }}</td>
                <td>{{ new Date(trace.createTime).toLocaleDateString() }}</td>
                <td>
                  <button @click="deleteTrace(trace)">delete</button>
                </td>
              </tr>
              </tbody>
              <tbody v-else>
              <tr>
                <td colspan="3" style="text-align: center">empty.</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
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

  let $ = window.$;

  export default {
    data () {
      return {
        doubanISBN: '',
        newBook: {},
        filteredBooks: [],
        searchInput: '',
        book: null,
        newTrace: {},
        DUMP: null
      }
    },
    beforeMount() {
      let self = this;
      this.$store.dispatch('ADMIN_FETCH_BOOKS').then(() => {
        self.filteredBooks = self.$store.state.admin_books.filter(e => self.isBookVisible(e));
      });
    },
    watch: {
      searchInput(value) {
        let self = this;
        this.filteredBooks = this.$store.state.admin_books.filter(e => self.isBookVisible(e));
      }
    },
    computed: {
      filteredTraces() {
        if (this.book.traces) {
          let self = this;
          return this.book.traces.filter(e => self.isTraceVisible(e));
        } else {
          return [];
        }
      }
    },
    methods: {
      fetchDouban(isbn) {
        service.fetchDouban(isbn).then(response => {
          let data = response.data;
          if (data.isbn13 === isbn) {
            let book = {
              isbn: data.isbn13,
              name: data.title,
              publisher: data.publisher,
              authors: data.author.length ? '#' + data.author.join('#') : '',
              imageUrl: data.image,
              desc: data.summary
            };
            this.newBook = book;
          }
        });
      },
      setNewBookImage(e) {
        var target = e.target;
        var files = target.files;
        if (!files || files.length === 0) {
          this.newBook.image = null;
        } else {
          this.newBook.image = files[0];
        }
      },
      addBook(book) {
        this.$store.dispatch('ADMIN_ADD_BOOK', book);
        return false;
      },
      isBookVisible(e) {
        let pattern = this.searchInput.toLowerCase().trim();
        return e && inArray(['NORMAL'], e.status) && (!pattern.length || e.isbn.toLowerCase().indexOf(pattern) !== -1);
      },
      showBookDetail(book) {
        this.book = book;
        this.newTrace.isbn = book.isbn;
        this.$store.dispatch('ADMIN_FETCH_BOOK_TRACES', book).then(() => {
        });
        $('#modal-book-detail').modal('show');
      },
      hideBookDetail() {
        $('#modal-book-detail').modal('hide');
      },
      deleteBook(book) {
        this.$store.dispatch('ADMIN_DEL_BOOK', book);
      },
      addTrace(trace) {
        this.$store.dispatch('ADMIN_ADD_BOOK_TRACE', trace);
        return false;
      },
      isTraceVisible(e) {
        return e && inArray(['NORMAL', 'LOCKED', 'BORROWED'], e.status);
      },
      deleteTrace(trace) {
        this.$store.dispatch('ADMIN_DEL_BOOK_TRACE', trace);
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

</style>
