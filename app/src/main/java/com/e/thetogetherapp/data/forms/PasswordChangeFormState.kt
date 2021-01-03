package com.e.thetogetherapp.data.forms

data class PasswordChangeFormState (
    val passwordLengthError: Int? = null,
    val differentPasswordsError: Int? = null,
    val notNewPassword: Int? = null,
    val isDataValid: Boolean = false
)