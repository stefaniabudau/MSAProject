package com.e.thetogetherapp.model

class Rating {
    var ratingId: String? = null
    var toId: String? = null
    var fromId: String? = null
    var honesty: Float? = null
    var attitude: Float? = null
    var punctuality: Float? = null

    constructor(
        ratingId: String?,
        toId: String?,
        fromId: String?,
        honesty: Float?,
        attitude: Float?,
        punctuality: Float?
    ) {
        this.ratingId = ratingId
        this.toId = toId
        this.fromId = fromId
        this.honesty = honesty
        this.attitude = attitude
        this.punctuality = punctuality
    }
}