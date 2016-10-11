import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

var instance = axios.create()
const mock = new MockAdapter(instance)

mock.onPost('/users/login').reply(200, {
  success: true,
  entity: {
    role: 'READER',
    id: 1,
    username: 'cauchywei',
    name: 'Wei Qin',
    avatarUrl: '233',
    age: '20',
    major: 'CS',
    phone: '23333',
    email: 'cauchywei@gmail.com',
    remarks: 'hi'
  }
})

mock.onGet('/users/self/').reply(200, {
  role: 'READER',
  id: 1,
  username: 'cauchywei',
  name: 'Wei Qin',
  avatarUrl: '233',
  age: '20',
  major: 'CS',
  phone: '23333',
  email: 'cauchywei@gmail.com',
  remarks: 'hi'
})

export default instance
