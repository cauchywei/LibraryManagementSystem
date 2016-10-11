/**
 * Created by cauchywei on 16/10/11.
 */
var user = {}
var login = false

export function isLogin () {
  return !!login
}

export function getUser () {
  return user
}

export function onLogin (_user) {
  user = _user
  localStorage.setItem('user', user)
  login = true
}

export function logout () {
  user = null
  localStorage.setItem('user', user)
  login = false
}

