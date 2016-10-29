import Vue from 'vue';
import Vuex from 'vuex';
import * as service from '../service'

Vue.use(Vuex);

var userData;
try {
  userData = JSON.parse(localStorage.getItem('account'));
} catch (e) {
  userData = null;
}
const store = new Vuex.Store({
  state: {
    account: userData,
    searchBooks: null,
    records: null,
    books: [],
    users: null
  },

  actions: {

    ON_LOGIN: ({commit, dispatch, state}, account) => {
      commit('SET_LOGIN', account);
      return Promise.resolve();
    },

    LOGOUT: ({commit, dispatch, state}) => {
      commit('SET_LOGOUT');
      return Promise.resolve();
    },

    ON_SEARCH_BOOKS: ({commit, dispatch, state}, books) => {
      commit('SET_SEARCH_BOOKS', books);
      return Promise.resolve();
    },

    ADD_NEW_BOOK: ({commit, dispatch, state}, book) => {
      commit('ADD_BOOK', book);
      return Promise.resolve();
    },

    FETCH_ALL_BOOKS_BY_ADMIN: ({commit, dispatch, state}) => {
      return service.getBooksByAdmin().then(function (response) {
        if (response.data.success) {
          commit('SET_NEW_BOOKS', response.data.entities)
        }
      })
    },
    FETCH_ALL_LENDS_BY_ADMIN: ({commit, dispatch, state}) => {
      return service.getLendsByAdmin().then(function (response) {
        if (response.data.success) {
          commit('SET_NEW_BOOKS', response.data.entities)
        }
      })
    },
    ON_LIST_BORROW_RECORDS: ({commit, dispatch, state}, borrowRecords) => {
      commit('SET_BORROW_RECORDS', borrowRecords);
      return Promise.resolve();
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

    SET_LOGOUT: (state) => {
      state.account = null;
      localStorage.setItem('account', null);
    },

    SET_LOGIN: (state, account) => {
      state.account = account;
      localStorage.setItem('account', JSON.stringify(account));
    },

    SET_SEARCH_BOOKS: (state, books) => {
      state.searchBooks = books;
    },
    ADD_BOOK: (state, book) => {
      state.books.unshift(book);
    },
    SET_NEW_BOOKS: (state, books) => {
      state.books = books;
    },
    SET_BORROW_RECORDS: (state, borrowRecords) => {
      state.borrowRecords = borrowRecords;
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

    isLogin (state) {
      return !!state.account
    }

    // // ids of the items that should be currently displayed based on
    // // current list type and current pagination
    // activeIds (state) {
    //   const { activeType, itemsPerPage, lists } = state
    //   const page = Number(state.route.params.page) || 1
    //   if (activeType) {
    //     const start = (page - 1) * itemsPerPage
    //     const end = page * itemsPerPage
    //     return lists[activeType].slice(start, end)
    //   } else {
    //     return []
    //   }
    // },
    //
    // // items that should be currently displayed.
    // // this Array may not be fully fetched.
    // activeItems (state, getters) {
    //   return getters.activeIds.map(id => state.items[id]).filter(_ => _)
    // }
  }
})

export default store;
