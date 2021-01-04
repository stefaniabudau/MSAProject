package com.e.thetogetherapp.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChangePassword (
    var current:String? = "",
    var new:String? = "",
    var newRe:String? = ""
)