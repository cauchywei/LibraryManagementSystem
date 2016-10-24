import mockAxios from './mock'
import prodAxios from './prod'

const env = 'prod';
const axios = env === 'mock' ? mockAxios : prodAxios;

function extractMapToForm(map) {
  const form = new FormData();
  for (var key in map) {
    if (!map.hasOwnProperty(key)) continue;
    form.append(key, map[key]);
  }
  return form;
}

export function register(map) {
  return axios.post('/users/register', extractMapToForm(map))
}

export function login({username, password}) {
  const map = {
    'username': username,
    'password': password
  };
  return axios.post('/users/login', extractMapToForm(map));
}

export function logout() {
  return axios.post('/users/logout');
}

export function getProfile() {
  return axios.get('/users/self/');
}

export function setProfile(map) {
  return axios.post('/books/self/', extractMapToForm(map));
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
  return axios.post(`/books/${ISBN}/return`);
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

export function lendBookTrace(trace) {
  return axios.post(`/books/${trace.book.isbn}/traces/${trace.id}/lend`);
}

export function getBookByAdmin(ISBN) {
  return axios.get('/admin/books/' + ISBN + '/');
}

export function getBookTraceByAdmin(ISBN) {
  return axios.get('/admin/books/' + ISBN + '/traces/');
}

export function addBookByAdmin(map) {
  return axios.post('/admin/books/add', extractMapToForm(map));
}

export function updateBookByAdmin(ISBN, map) {
  return axios.post('/admin/books/' + ISBN + '/update', extractMapToForm(map));
}

export function deleteBookTraceByAdmin(trace) {
  return axios.post('admin/books/' + trace.book.isbn + '/traces/' + trace.id + '/delete');
}

export function borrowBookTraceByAdmin(trace) {
  return axios.post('admin/books/' + trace.book.isbn + '/traces/' + trace.id + '/borrow');
}
export function getUsersByAdmin() {
  return axios.get('/admin/users/', {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function getBookTraces(isbn) {
  return axios.get(`/books/${isbn}/traces/`, {
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
