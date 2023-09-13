package com.example.chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val userId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("username")


    }
    private fun openChatActivity(user: User) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("userId", user.userId)
        intent.putExtra("username", user.username)
        startActivity(intent)
    }
}
