package com.e.thetogetherapp.data.model

class Request {
    var requestId: String? = null
    var needy_id: String? = null
    var volunteer_id: String? = null
    var title: String? = null
    var description: String? = null
    var status: String? = null
    var messages: String? = null
    var feedback: String? = null

    constructor(
        requestId: String?,
        needy_id: String?,
        volunteer_id: String?,
        title: String?,
        description: String?,
        status: String?,
        messages: String?,
        feedback: String?
    ) {
        this.requestId = requestId
        this.needy_id = needy_id
        this.volunteer_id = volunteer_id
        this.title = title
        this.description = description
        this.status = status
        this.messages = messages
        this.feedback = feedback
    }
}