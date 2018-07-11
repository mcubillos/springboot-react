import 'babel-polyfill'
import React from 'react'
import { renderToString } from 'react-dom/server'
import { createStore, applyMiddleware } from 'redux'
import { Provider } from 'react-redux'
import thunk from 'redux-thunk'
import { createLogger } from 'redux-logger'

import reducers from './reducers/index'
import App from './components/App'

export function render(requestPath, model) {
  const store = createStore(reducers,
                            model,
                            applyMiddleware(thunk, createLogger()))

  let html = renderToString(
    <Provider store={store}>
      <App />
    </Provider>
  )

  return {
    html,
    state: model
  }
}