import React from 'react'
import PropTypes from 'prop-types'

const UserRenderer = (props) =>
<div>
 	<div th:substituteby="index::logout"></div>
    <h1>This is a secured page!</h1>
    <p><a href="/index" th:href="@{/index}">Back to home page</a></p>
</div>

export default UserRenderer