package com.e.thetogetherapp.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val uid: String,
        val displayName: String
)