package com.e.thetogetherapp.data.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Event (
    var title: String? = "",
    var location: String? = "",
    var date: String? = "",
    var time: String? = "",
    var description: String? = "",
    var category: String? = ""){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "location" to location,
            "date" to date,
            "time" to time,
            "description" to description,
            "category" to category)
    }
}