package com.e.thetogetherapp.notifications

data class PushNotification(
    val data: NotificationData,
    val to: String
)