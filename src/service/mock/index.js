/**
 * Created by cauchywei on 16/10/11.
 */
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

var instance = axios.create()
const mock = new MockAdapter(instance)

mock.onPost('/users/login').reply(200, {
  role: 'reader',
  id: 1,
  username: 'cauchywei',
  name: 'Wei Qin',
  avatarUrl: '233',
  age: '20',
  major: 'CS',
  phone: '23333',
  email: 'cauchywei@gmail.com',
  remarks: []
})

mock.onGet('/test').reply(200, {
  role: 'reader',
  id: 1,
  username: 'cauchywei',
  name: 'Wei Qin',
  avatarUrl: '233',
  age: '20',
  major: 'CS',
  phone: '23333',
  email: 'cauchywei@gmail.com',
  remarks: []
})

export default instance
