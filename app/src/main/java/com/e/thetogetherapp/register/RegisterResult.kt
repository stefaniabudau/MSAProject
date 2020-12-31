package com.e.thetogetherapp.register

import com.e.thetogetherapp.data.model.RegisteredUser


data class RegisterResult(
    val success: RegisteredUser? = null,
    val error: Int? = null
)