package com.e.thetogetherapp.login

import com.e.thetogetherapp.data.model.LoggedInUser

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: LoggedInUser? = null,
        val error: Int? = null
)