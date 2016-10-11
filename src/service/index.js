import mockAxios from './mock'
import prodAxios from './prod'

const env = 'prod';
const axios = env === 'mock' ? mockAxios : prodAxios;

export function register(form) {
  return axios.post('/users/register', {
    data: data
  })
}

export function login(username, password) {
  const form = {
    'username': username,
    'password': password
  };
  return axios.post('/users/login', {
    data: form
  });
}

export function logout() {
  return axios.post('/users/logout');
}

export function getProfile() {
  return axios.get('/users/self/');
}

export function setProfile(form) {
  return axios.post('/books/self/', {
    data: form
  });
}

export function searchBook(params) {
  return axios.get('/books/search', {
    params: params
  });
}

export function getBook(ISBN) {
  return axios.get('/books/' + ISBN + '/');
}

export function borrowBook(ISBN) {
  return axios.post('/books/' + ISBN + '/borrow');
}

export function returnBook(ISBN) {
  return axios.post('/books/' + ISBN + '/return');
}

export function getBorrowRecords() {
  return axios.get('/users/self/records/', {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function getBooksByAdmin() {
  return axios.get('/admin/books/', {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function getBookByAdmin(ISBN) {
  return axios.get('/admin/books/' + ISBN + '/');
}

export function addBookByAdmin(form) {
  return axios.post('/admin/books/add', {
    data: form
  });
}

export function updateBookByAdmin(ISBN, form) {
  return axios.post('/admin/books/' + ISBN + '/update', {
    data: form
  });
}

export function deleteBookByAdmin(ISBN) {
  return axios.post('/users/' + ISBN + '/');
}

export function getUsersByAdmin() {
  return axios.get('/admin/users/', {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function getUserByAdmin(userId) {
  return axios.get('/admin/users/' + userId + '/');
}

export function freezeUserByAdmin(userId) {
  return axios.post('/admin/users/' + userId + '/freeze');
}

export function unfreezeUserByAdmin(userId) {
  return axios.post('/admin/users/' + userId + '/unfreeze');
}

export function deleteUserByAdmin(userId) {
  return axios.post('/admin/users/' + userId + '/delete');
}
