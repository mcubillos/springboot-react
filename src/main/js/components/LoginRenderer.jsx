import React from 'react'
import PropTypes from 'prop-types'

const LoginRenderer = (props) =>
<div>
	 <h1>Login page</h1>
        <p>Example user: user / password</p>
        <p class="error">Wrong user or password</p>
        <form method="post">
            <label for="username">Username</label>:
            <input type="text" id="username" name="username" autofocus="autofocus" /> <br />
            <label for="password">Password</label>:
            <input type="password" id="password" name="password" /> <br />
            <input type="submit" value="Log in" />
        </form>
        <p><a href="/index">Back to home page</a></p>
</div>

export default LoginRenderer