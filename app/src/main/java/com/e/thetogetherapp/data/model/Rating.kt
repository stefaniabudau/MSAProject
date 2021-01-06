package com.e.thetogetherapp.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Rating (
    var id: String? = "",
    var to: String? = "",
    var from: String? = "",
    var honesty: String? = "",
    var attitude: String? = "",
    var punctuality: String? = ""
)