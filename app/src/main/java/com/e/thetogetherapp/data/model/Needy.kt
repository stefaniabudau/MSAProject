package com.e.thetogetherapp.data.model

data class Needy (
    var needyId: String? = null,
    var name: String? = null,
    var nickname: String? = null,
    var age: Int? = null,
    var location: Map<String, String>? = null,
    var description: String? = null,
    var averageRating: Float? = null,
    var friends: List<Volunteer>? = null,
    var ratings: List<Rating>? = null,
    var donations: List<Donation>? = null,
    var requests: List<Request>? = null)

//    constructor(
//        needyId: String?,
//        name: String?,
//        nickname: String?,
//        age: Int?,
//        location: IntArray?,
//        description: String?,
//        averageRating: Float?,
//        friends: List<Volunteer>?,
//        ratings: List<Rating>?,
//        donations: List<Donation>?,
//        requests: List<Request>?
//    ) {
//        this.needyId = needyId
//        this.name = name
//        this.nickname = nickname
//        this.age = age
//        this.location = location
//        this.description = description
//        this.averageRating = averageRating
//        this.friends = friends
//        this.ratings = ratings
//        this.donations = donations
//        this.requests = requests
//    }
//}