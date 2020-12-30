package com.e.thetogetherapp.register

import com.e.thetogetherapp.data.model.RegisteredUser


data class RegisterResult(
    val success: RegisteredUserView? = null,
    val error: Int? = null
)