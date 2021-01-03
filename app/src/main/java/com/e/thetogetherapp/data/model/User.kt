package com.e.thetogetherapp.data.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User(
    var email: String? = "",
    var name: String? = "",
    var nickname: String? ="",
    var age: String? = "",
    var type: String? = "",
    var location: Map<String, String>? = null){

    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "email" to email,
            "name" to name,
            "nickname" to nickname,
            "age" to "age",
            "location" to mapOf(
                "city" to location!!["city"],
                "country" to location!!["country"],
                "address" to location!!["address"]
            ),
            "type" to type
        )
    }
}

