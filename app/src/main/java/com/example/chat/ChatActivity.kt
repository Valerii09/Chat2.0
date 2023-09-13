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
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var messages: MutableList<String>
    private lateinit var dialogId: String // Идентификатор текущего диалога

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val userId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("username")
        val messageId = System.currentTimeMillis().toString()

        val listView = findViewById<ListView>(R.id.listView)
        messages = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messages)
        listView.adapter = adapter

        // Определите идентификатор диалога на основе идентификаторов пользователей
        if (userId != null) {
            dialogId = getDialogId(auth.currentUser?.uid, userId)
            loadMessages(dialogId)
        }

        val sendButton = findViewById<Button>(R.id.sendButton)
        sendButton.setOnClickListener {
            val messageText = getMessageText()
            if (userId != null) {
                sendMessage(dialogId, messageText)
            }
        }
    }

    private fun loadMessages(dialogId: String) {
        // Загрузите сообщения для данного диалога из Firestore
        firestore.collection("dialogs").document(dialogId).collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                val newMessages = mutableListOf<String>() // Создайте новый список сообщений
                snapshot?.documents?.forEach { document ->
                    val messageText = document.getString("messageText")
                    if (messageText != null) {
                        newMessages.add(messageText) // только новые сообщения
                    }
                }
                messages.clear() // Очистить старый список
                messages.addAll(newMessages) //  новые сообщения в список
                adapter.notifyDataSetChanged()
            }
    }


    private fun sendMessage(dialogId: String, messageText: String) {
        val senderId = auth.currentUser?.uid
        if (senderId != null) {
            val messageId = System.currentTimeMillis().toString() // Уникальный идентификатор сообщения
            val message = Message(senderId, dialogId, messageText, messageId)

            // Сохраните сообщение в Firestore в подколлекции messages текущего диалога
            firestore.collection("dialogs").document(dialogId).collection("messages").document(messageId).set(message)
                .addOnSuccessListener {
                    // Успешно отправлено
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

    private fun getDialogId(userId1: String?, userId2: String?): String {
        // Сортируйте идентификаторы пользователей и объединяйте их, чтобы получить уникальный идентификатор диалога
        val sortedUserIds = listOfNotNull(userId1, userId2).sorted()
        return sortedUserIds.joinToString("_")
    }


}
