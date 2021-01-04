package com.e.thetogetherapp.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Donation (
    var volunteer: String? = "",
    var needy: String? = "",
    var catgory: String? = "",
    var title: String? = "",
    var description: String? = "",
    var status: String? = "",
    var feedback: String? = ""
)