package com.e.thetogetherapp.register

data class RegisterFormState( val emailError: Int? = null,
                         val nameError: Int? = null,
                         val ageError: Int? = null,
                         val locationError: Int? = null,
                         val passwordError: Int? = null,
                         val differentPasswords : Int? = null,
                         val isDataValid: Boolean = false)