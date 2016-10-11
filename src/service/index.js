/**
 * Created by cauchywei on 16/10/11.
 */
/* eslint-env browser */
import mockAxios from './mock'
import prodAxios from './prod'

const env = 'prod'
const axios = env === 'mock' ? mockAxios : prodAxios

export function login (username, password) {
  const params = {username, password}
  const form = new FormData()

  form.append('method', 'post')
  form.append('format', 'json')
  form.append('params', params)
  // form.append('sign', sign(params))

  return axios({
    method: 'post',
    url: '/users/login',
    data: form
  })
}

export function register (data) {
  const form = new FormData()
  form.append('method', 'post')
  form.append('format', 'json')
  form.append('params', data)
  // form.append('sign', sign(data))

  return axios({
    method: 'post',
    url: '/users/register',
    data: form
  })
}

export function searchBook(params) {
  return axios.get('/books/search', {
    params: params
  });
}

export function getProfile() {
  return axios.get('/users/self/');
}

export function setProfile(form) {
  return axios.post('/books/self/', {
    data: form
  });
}
