package com.example.waymaker.ui.register

import com.example.waymaker.ui.login.LoggedInUserView

data class RegisterResult(
    val success: RegisteredUserView? = null,
    val error: Int? = null
)