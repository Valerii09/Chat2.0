package com.example.chat

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ArrayAdapter<String> // Адаптер для сообщений
    private lateinit var messages: MutableList<String> // Список сообщений

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val userId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("username")

        val listView = findViewById<ListView>(R.id.listView)
        messages = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messages)
        listView.adapter = adapter

        if (userId != null) {
            loadMessages(userId)
        }

        val sendButton = findViewById<Button>(R.id.sendButton)
        sendButton.setOnClickListener {
            val messageText = getMessageText()
            if (userId != null) {
                sendMessage(userId, messageText)
            }
        }
    }

    private fun loadMessages(userId: String) {
        // Ваш код для загрузки сообщений из Firestore, как вы делали ранее
    }

    private fun sendMessage(receiverId: String, messageText: String) {
        val senderId = auth.currentUser?.uid
        if (senderId != null) {
            val message = Message(senderId, receiverId, messageText, System.currentTimeMillis())

            // Сохраните сообщение в Firestore
            firestore.collection("messages").add(message)
                .addOnSuccessListener {
                    // Успешно отправлено
                    messages.add(messageText)
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    // Ошибка отправки
                }
        }
    }

    private fun getMessageText(): String {
        val messageEditText = findViewById<EditText>(R.id.messageEditText)
        return messageEditText.text.toString()
    }
}
