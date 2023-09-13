package com.example.chat

data class Message(
    val senderId: String,
    val receiverId: String,
    val messageText: String,
    val timestamp: String
)
