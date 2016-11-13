import Vue from 'vue';
import Vuex from 'vuex';
import * as service from '../service';

Vue.use(Vuex);

let account;
try {
  account = JSON.parse(localStorage.getItem('account'));
} catch(e) {
  localStorage.setItem('account', null);
  account = null;
}

let methods = {

  getBook: (books, isbn) => {
    if (books instanceof Array) {
      for (let i in books) {
        let e = books[i];
        if (e && e.isbn === isbn) {
          return {
            index: i,
            value: e
          };
        }
      }
      return {
        index: -1,
        value: undefined
      };
    } else {
      return undefined;
    }
  },

  getBookTrace: (traces, id) => {
    if (traces instanceof Array) {
      for (let i in traces) {
        let e = traces[i];
        if (e && e.id === id) {
          return {
            index: i,
            value: e
          };
        }
      }
      return {
        index: null,
        value: undefined
      };
    } else {
      return undefined;
    }
  },

  getBorrow: (borrows, id) => {
    if (borrows instanceof Array) {
      for (let i in borrows) {
        let e = borrows[i];
        if (e && e.id === id) {
          return {
            index: i,
            value: e
          };
        }
      }
      return {
        index: null,
        value: undefined
      };
    } else {
      return undefined;
    }
  },

  DUMP: () => {}

};

const store = new Vuex.Store({
  state: {

    account: account,

    books: [],
    borrows: [],

    admin_users: [],
    admin_books: [],
    admin_bookTraces: [],
    admin_borrows: [],

    DUMP: {}

  },

  actions: {

    LOGIN: ({ commit, dispatch, state }, account) => {
      commit('SET_ACCOUNT', account);
      return Promise.resolve();
    },

    LOGOUT: ({ commit, dispatch, state }) => {
      commit('DEL_ACCOUNT');
      return Promise.resolve();
    },

    SEARCH_BOOKS: ({ commit, dispatch, state }, { isbn, name }) => {
      return service.searchBook({
        isbn: isbn,
        name: name
      }).then(response => {
        if (response.data.success) {
          commit('SET_BOOKS', response.data.entities);
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    FETCH_BOOK_TRACES: ({ commit, dispatch, state }, book) => {
      return service.getBookTraces(book.isbn).then(response => {
        if (response.data.success) {
          commit('SET_BOOK_TRACES', {
            isbn: book.isbn,
            traces: response.data.entities
          });
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      })
    },

    FETCH_BORROWS: ({ commit, dispatch, state }) => {
      return service.getMyBorrows().then(response => {
        if (response.data.success) {
          commit('SET_BORROWS', response.data.entities);
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    CANCEL_APPLICATION: ({ commit, dispatch, state }, borrow) => {
      return service.cancelApplication(borrow).then(response => {
        if (response.data.success) {
          commit('SET_BORROW', response.data.entity);
          alert("cancel successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    CANCEL_RESERVATION: ({ commit, dispatch, state }, borrow) => {
      return service.cancelReservation(borrow).then(response => {
        if (response.data.success) {
          commit('SET_BORROW', response.data.entity);
          alert("cancel successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    RENEW_BORROW: ({ commit, dispatch, state }, borrow) => {
      return service.renewBorrow(borrow).then(response => {
        if (response.data.success) {
          commit('SET_BORROW', response.data.entity);
          alert("renew for 15 days, you can renew once only.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    // admin

    ADMIN_ADD_BOOK: ({ commit, dispatch, state, getters }, book) => {
      if (!getters.isAdmin) return false;
      return service.addBookByAdmin(book).then(response => {
        if (response.data.success) {
          commit('ADMIN_ADD_BOOK', response.data.entity);
          alert("add successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(function(e) {
        alert(e);
      });
    },

    ADMIN_DEL_BOOK: ({ commit, dispatch, state, getters }, book) => {
      if (!getters.isAdmin) return false;
      return service.deleteBookByAdmin(book).then(response => {
        if (response.data.success) {
          commit('ADMIN_DEL_BOOK', book);
          alert("delete successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(function(e) {
        alert(e);
      });
    },

    ADMIN_FETCH_USERS: ({ commit, dispatch, state, getters }) => {
      if (!getters.isAdmin) return false;
      return service.getUsersByAdmin().then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_USERS', response.data.entities);
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_FETCH_BOOKS: ({ commit, dispatch, state, getters }) => {
      if (!getters.isAdmin) return false;
      return service.getBooksByAdmin().then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_BOOKS', response.data.entities);
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_ADD_BOOK_TRACE: ({ commit, dispatch, state, getters }, trace) => {
      if (!getters.isAdmin) return false;
      return service.addBookTraceByAdmin(trace).then(response => {
        if (response.data.success) {
          commit('ADMIN_ADD_BOOK_TRACE', response.data.entity);
          alert("add successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_DEL_BOOK_TRACE: ({ commit, dispatch, state, getters }, trace) => {
      if (!getters.isAdmin) return false;
      return service.deleteBookTraceByAdmin(trace).then(response => {
        if (response.data.success) {
          console.log(trace);
          commit('ADMIN_DEL_BOOK_TRACE', trace);
          alert("delete successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_FETCH_BOOK_TRACES: ({ commit, dispatch, state, getters }, book) => {
      if (!getters.isAdmin) return false;
      return service.getBookTracesByAdmin(book).then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_BOOK_TRACES', {
            isbn: book.isbn,
            traces: response.data.entities
          });
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_FETCH_BORROWS: ({ commit, dispatch, state, getters }) => {
      if (!getters.isAdmin) return false;
      return service.getBorrowsByAdmin().then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_BORROWS', response.data.entities);
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_ACCEPT_APPLICATION: ({ commit, dispatch, state, getters }, borrow) => {
      service.acceptLendingByAdmin(borrow).then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_BORROW', response.data.entity);
          alert("accept application successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_REJECT_APPLICATION: ({ commit, dispatch, state, getters }, borrow) => {
      service.rejectLendingByAdmin(borrow).then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_BORROW', response.data.entity);
          alert("reject application successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_CONFIRM_RETURNED: ({ commit, dispatch, state, getters }, borrow) => {
      service.confirmReturnedByAdmin(borrow).then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_BORROW', response.data.entity);
          alert("confirm returned successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_CONFIRM_DISABLED: ({ commit, dispatch, state, getters }, borrow) => {
      service.confirmDisabledByAdmin(borrow).then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_BORROW', response.data.entity);
          alert("confirm disabled successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_QUICK_BORROW: ({ commit, dispatch, state, getters }, info) => {
      service.quickBorrowByAdmin(info).then(response => {
        if (response.data.success) {
          commit('ADMIN_ADD_BORROW', response.data.entity);
          alert("borrow successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    ADMIN_QUICK_RETURN: ({ commit, dispatch, state, getters }, info) => {
      service.quickReturnByAdmin(info).then(response => {
        if (response.data.success) {
          commit('ADMIN_SET_BORROW', response.data.entity);
          alert("return successfully.");
        } else {
          console.log(response.data.error);
          alert(response.data.error);
        }
      }).catch(e => {
        alert(e);
      });
    },

    DUMP: () => {
    }

    // // ensure data for rendering given list type
    // FETCH_LIST_DATA: ({ commit, dispatch, state }, { type }) => {
    //   commit('SET_ACTIVE_TYPE', { type })
    //   return fetchIdsByType(type)
    //     .then(ids => commit('SET_LIST', { type, ids }))
    //     .then(() => dispatch('ENSURE_ACTIVE_ITEMS'))
    // },
    //
    // // ensure all active items are fetched
    // ENSURE_ACTIVE_ITEMS: ({ dispatch, getters }) => {
    //   return dispatch('FETCH_ITEMS', {
    //     ids: getters.activeIds
    //   })
    // },
    //
    // FETCH_ITEMS: ({ commit, state }, { ids }) => {
    //   // only fetch items that we don't already have.
    //   ids = ids.filter(id => !state.items[id])
    //   if (ids.length) {
    //     return fetchItems(ids).then(items => commit('SET_ITEMS', { items }))
    //   } else {
    //     return Promise.resolve()
    //   }
    // },
    //
    // FETCH_USER: ({ commit, state }, { id }) => {
    //   return state.users[id]
    //     ? Promise.resolve(state.users[id])
    //     : fetchUser(id).then(user => commit('SET_USER', { user }))
    // }
  },

  mutations: {

    DEL_ACCOUNT: (state) => {
      state.account = null;
      localStorage.setItem('account', null);
    },

    SET_ACCOUNT: (state, account) => {
      state.account = account;
      localStorage.setItem('account', JSON.stringify(account));
    },

    SET_BOOKS: (state, books) => {
      state.books = books;
    },

    SET_BOOK_TRACES: (state, { isbn, traces }) => {
      let book = methods.getBook(state.books, isbn).value;
      if (book) {
        Vue.set(book, 'traces', traces);
      }
    },

    SET_BORROWS: (state, borrows) => {
      state.borrows = borrows;
    },

    SET_BORROW: (state, borrow) => {
      let i = methods.getBorrow(state.borrows, borrow.id).index;
      if (i) {
        Vue.set(state.borrows, i, borrow);
      }
    },

    // admin

    ADMIN_SET_USERS: (state, users) => {
      state.admin_users = users;
    },

    ADMIN_ADD_BOOK: (state, book) => {
      state.admin_books.unshift(book);
    },

    ADMIN_DEL_BOOK: (state, book) => {
      if (book) {
        book.status = 'DELETED';
      }
    },

    ADMIN_SET_BOOKS: (state, books) => {
      state.admin_books = books;
    },

    ADMIN_ADD_BOOK_TRACE: (state, trace) => {
      let book = methods.getBook(state.admin_books, trace.isbn).value;
      if (book) {
        book.traces.push(trace);
      }
    },

    ADMIN_DEL_BOOK_TRACE: (state, trace) => {
      if (trace) {
        Vue.set(trace, 'status', 'DELETED');
      }
    },

    ADMIN_SET_BOOK_TRACES: (state, { isbn, traces }) => {
      let book = methods.getBook(state.admin_books, isbn).value;
      if (book) {
        Vue.set(book, 'traces', traces);
      }
    },

    ADMIN_SET_BORROWS: (state, borrows) => {
      state.admin_borrows = borrows;
    },

    ADMIN_ADD_BORROW: (state, borrow) => {
      state.admin_borrows.unshift(borrow);
    },

    ADMIN_SET_BORROW: (state, borrow) => {
      let i = methods.getBorrow(state.admin_borrows, borrow.id).index;
      if (i) {
        Vue.set(state.admin_borrows, i, borrow);
      }
    },

    DUMP: (state) => {
    }

    //
    // SET_ITEMS: (state, { items }) => {
    //   items.forEach(item => {
    //     if (item) {
    //       Vue.set(state.items, item.id, item)
    //     }
    //   })
    // },
    //
    // SET_USER: (state, { user }) => {
    //   Vue.set(state.users, user.id, user)
    // }
  },

  getters: {

    isLogin: state => {
      return !!state.account;
    },

    getAccount: state => {
      return state.account;
    },

    isAdmin: state => {
      return state.account.roles.indexOf('ADMIN') !== -1;
    },

    getBooks: state => {
      return state.books;
    },

    DUMP: state => {
      return undefined;
    }

  }
});

export default store;
