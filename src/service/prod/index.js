import axios from 'axios'

export function serviceUrl () {
  return 'http://xupu.name:6001/'
}

const instance = axios.create({
  baseURL: serviceUrl(),
  timeout: 1000,
  headers: {'X-Custom-Header': 'foobar'}
});

export default instance
