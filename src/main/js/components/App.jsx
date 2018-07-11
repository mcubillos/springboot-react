import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import { fetchLogin } from '../actions/index'

class App extends Component {

  const { dispatch } = this.props
  render() {
    return <div>
      <button onClick={() => dispatch(fetchLogin())}>Login</button>
    </div>
  }
}

export default connect()(App)