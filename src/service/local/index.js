import axios from 'axios';

export function serviceUrl() {
  return 'http://localhost:6001/';
}

const instance = axios.create({
  baseURL: serviceUrl(),
  timeout: 3000,
  withCredentials: true,
  headers: { 'X-Custom-Header': 'foobar' }
});

export default instance;
