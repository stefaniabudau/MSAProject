package com.e.thetogetherapp.model

class Donation {
    var donationId: String? = null
    var volunteerId: String? = null
    var needyId: String? = null
    var title: String? = null
    var description: String? = null
    var status: String? = null

    constructor(
        donationId: String,
        volunteerId: String,
        needyId: String,
        title: String,
        description: String,
        status: String
    )
    {
        this.donationId = donationId
        this.volunteerId = volunteerId
        this.needyId = needyId
        this.title = title
        this.description = description
        this.status = status
    }
}