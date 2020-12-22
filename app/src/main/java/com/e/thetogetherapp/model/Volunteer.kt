package com.e.thetogetherapp.model

class Volunteer {
    var volunteerId: String? = null
    var name: String? = null
    var nickname: String? = null
    var age: Int? = null
    var location: IntArray? = null
    var description: String? = null
    var averageRating: Float? = null
    var friends: Array<Volunteer>? = null
    var ratings: Array<Rating>? = null
    var donations: Array<Donation>? = null
    var requests: Array<Request>? = null

    constructor(
        volunteerId: String?,
        name: String?,
        nickname: String?,
        age: Int?,
        location: IntArray?,
        description: String?,
        averageRating: Float?,
        friends: Array<Volunteer>?,
        ratings: Array<Rating>?,
        donations: Array<Donation>?,
        requests: Array<Request>?
    ) {
        this.volunteerId = volunteerId
        this.name = name
        this.nickname = nickname
        this.age = age
        this.location = location
        this.description = description
        this.averageRating = averageRating
        this.friends = friends
        this.ratings = ratings
        this.donations = donations
        this.requests = requests
    }
}