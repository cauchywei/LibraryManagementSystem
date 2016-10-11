/**
 * Created by cauchywei on 16/10/11.
 */
import axios from 'axios'

export function serviceUrl () {
  return 'http://localhsot:8080/api/'
}

const instance = axios.create({
  baseURL: serviceUrl(),
  timeout: 1000,
  headers: {'X-Custom-Header': 'foobar'}
})

export default instance
