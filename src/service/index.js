import mockAxios from './mock';
import localAxios from './local';
import productionAxios from './production';

const env = 'production';
var axios;
switch (env) {
  case 'local':
    axios = localAxios;
    break;
  case 'mock':
    axios = mockAxios;
    break;
  case 'production':
    axios = productionAxios;
    break;
}

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

export function getBook(isbn) {
  return axios.get(`/books/${isbn}/`);
}

export function getBookTraces(isbn) {
  return axios.get(`/books/${isbn}/traces/`, {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function borrowBook(isbn) {
  return axios.post(`/books/${isbn}/borrow`);
}

export function returnBook(isbn) {
  return axios.post(`/books/${isbn}/return`);
}

export function renewBorrow(loan) {
  return axios.post(`/users/self/loen/${loan.id}/renew`);
}

export function cancelApplication(loan) {
  return axios.post(`/users/self/loen/${loan.id}/cancel`);
}

export function cancelReservation(loan) {
  return axios.post(`/users/self/loen/${loan.id}/cancelReservation`);
}

export function getMyBorrows() {
  return axios.get('/users/self/loen/', {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function borrowBookTrace(trace) {
  return axios.post(`/books/${trace.book.isbn}/traces/${trace.id}/lend`);
}

export function reserveBookTrace(trace) {
  return axios.post(`/books/${trace.book.isbn}/traces/${trace.id}/reserve`);
}

// admin

export function getUsersByAdmin() {
  return axios.get('/admin/users/', {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function getUserByAdmin(userId) {
  return axios.get(`/admin/users/${userId}/`);
}

export function freezeUserByAdmin(userId) {
  return axios.post(`/admin/users/${userId}/freeze`);
}

export function unfreezeUserByAdmin(userId) {
  return axios.post(`/admin/users/${userId}/unfreeze`);
}

export function deleteUserByAdmin(userId) {
  return axios.post(`/admin/users/${userId}//delete`);
}

export function addBookByAdmin(book) {
  return axios.post('/admin/books/add', extractMapToForm(book));
}

export function updateBookByAdmin(oldBook, newBook) {
  return axios.post(`/admin/books/${oldBook.isbn}/update`, extractMapToForm(newBook));
}

export function deleteBookByAdmin(book) {
  return axios.post(`/admin/books/${book.isbn}/delete`);
}

export function getBooksByAdmin() {
  return axios.get('/admin/books/', {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function getBookByAdmin(isbn) {
  return axios.get(`/admin/books/${isbn}/`);
}

export function addBookTraceByAdmin(trace) {
  return axios.post(`/admin/books/${trace.isbn}/traces/add`, extractMapToForm(trace));
}

export function updateBookTraceByAdmin(oldTrace, newTrace) {
  return axios.post(`/admin/books/${oldTrace.isbn}/traces/${oldTrace.id}/update`, extractMapToForm(newTrace));
}

export function deleteBookTraceByAdmin(trace) {
  return axios.post(`admin/books/${trace.isbn}/traces/${trace.id}/delete`);
}

export function getBookTracesByAdmin(book) {
  return axios.get(`/admin/books/${book.isbn}/traces/`, {
    params: {
      page: 1,
      limits: 0
    }
  });
}

export function getBookTraceByAdmin(isbn, traceId) {
  return axios.get(`/admin/books/${isbn}/traces/${traceId}/`);
}

export function getBorrowsByAdmin() {
  return axios.get('/admin/loen/');
}

export function acceptLendingByAdmin(loan) {
  return axios.post(`/admin/loen/${loan.id}/accept`);
}

export function rejectLendingByAdmin(loan) {
  return axios.post(`/admin/loen/${loan.id}/reject`);
}

export function confirmReturnedByAdmin(loan) {
  return axios.post(`/admin/loen/${loan.id}/confirmReturned`);
}

export function confirmDisabledByAdmin(loan) {
  return axios.post(`/admin/loen/${loan.id}/confirmDisabled`);
}

export function fetchDouban(isbn) {
  return axios.get(`/douban/${isbn}/`);
}

export function quickBorrowByAdmin(info) {
  return axios.post(`/admin/books/${info.isbn}/traces/${info.traceId}/lending`, extractMapToForm({
    username: info.username
  }));
}

export function quickReturnByAdmin(info) {
  return axios.post(`/admin/books/${info.isbn}/traces/${info.traceId}/returned`);
}
