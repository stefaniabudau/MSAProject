package com.e.thetogetherapp.register

data class RegisterForm( var email: String= "",
                         var name: String = "",
                         var nickname: String = "",
                         var age: String = "",
                         var country: String = "",
                         var city: String = "",
                         var address: String = "",
                         var password1: String = "",
                         var password2: String = "",
                         var type: String)