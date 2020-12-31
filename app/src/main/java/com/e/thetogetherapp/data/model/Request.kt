package com.e.thetogetherapp.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Request(
        val requestId: String? = "",
        val needy_id: String? = "",
        val volunteer_id: String? = "",
        val title: String? = "",
        val description: String? = "",
        val status: String? = "",
        val messages: String? = "",
        val feedback: String? = ""
    )
