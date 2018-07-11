import fetch from 'isomorphic-fetch'

export const REQUEST_LOGIN = 'REQUEST_LOGIN'
export const REQUEST_USER = 'REQUEST_USER'
	
function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
      return response
  } else {
      const error = new Error(response.statusText)
      error.response = response
      throw error
  }
}

export function requestLogin() {
  return {
    type: REQUEST_LOGIN,
  }
}

export function fetchLogin() {
  return (dispatch) => {
      dispatch(requestLogin())

      return fetch('/login', {
          method: 'GET',
          credentials: 'same-origin',
      }).then(response => checkStatus(response))
        .then(response => response.json())
        .catch(error => {
          dispatch()
        })
  }
}

export function requestUser() {
  return {
    type: REQUEST_USER,
  }
}

export function fetchUser() {
  return (dispatch) => {
      dispatch(requestUser())

      return fetch('/user', {
          method: 'GET',
          credentials: 'same-origin',
      }).then(response => checkStatus(response))
        .then(response => response.json())
        .catch(error => {
          dispatch()
        })
  }
}