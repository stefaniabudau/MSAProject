package com.e.thetogetherapp.model

class User {
    var userId: String? = null
    var userame: String? = null
    var password: String? = null

    constructor(userId: String?, userame: String?, password: String?) {
        this.userId = userId
        this.userame = userame
        this.password = password
    }
}