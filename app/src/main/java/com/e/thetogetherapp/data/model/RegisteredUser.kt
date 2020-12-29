package com.e.thetogetherapp.data.model

data class RegisteredUser(val displayName: String? = null,
                          val email: String? = null,
                          val age: String? = null,
                          val location: Map<String, String>? = null)