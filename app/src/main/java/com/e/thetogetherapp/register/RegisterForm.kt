package com.e.thetogetherapp.register

data class RegisterForm( var email: String?= null,
                         var name: String? = null,
                         var age: String? = null,
                         var country: String? = null,
                         var city: String? = null,
                         var address: String? = null,
                         var password1: String? = null,
                         var password2: String? = null,
                         var type: String)