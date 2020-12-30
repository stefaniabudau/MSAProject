package com.e.thetogetherapp.register

data class RegisterFormState( val emailError: Int? = null,
                             val nameError: Int? = null,
                             val nicknameError: Int? = null,
                             val ageError: Int? = null,
                             val countryError: Int? = null,
                             val cityError: Int? = null,
                             val addressError: Int? = null,
                             val passwordError: Int? = null,
                             val differentPasswords : Int? = null,
                             val isDataValid: Boolean = false)