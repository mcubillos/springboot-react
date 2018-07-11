import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import LoginRenderer from './LoginRenderer'

class LoginApp extends Component {

  render() {
    return <div>
     <LoginRenderer></LoginRenderer>
    </div>
  }
}

export default connect()(LoginApp)