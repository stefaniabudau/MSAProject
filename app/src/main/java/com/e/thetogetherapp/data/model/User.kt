package com.e.thetogetherapp.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User (val email: String? = "",
             val name: String? = "",
             val nickname: String? ="",
             val age: String? = "",
             val type: String? = "",
             val location: Map<String, String>? = null)

