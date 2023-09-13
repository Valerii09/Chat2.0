package com.example.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserListActivity : AppCompatActivity() {

    private lateinit var userList: ListView
    private lateinit var usernames: MutableList<String> // Создаем список для имен пользователей
    private lateinit var users: MutableList<User>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        userList = findViewById(R.id.userList)
        users = mutableListOf()
        usernames = mutableListOf() // Инициализируем список для имен

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames) // Используем список имен для адаптера
        userList.adapter = adapter

        firestore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val userId = document.id
                    val username = document.getString("username") ?: ""
                    val email = document.getString("email") ?: ""
                    val user = User(userId, username, email)
                    users.add(user)
                    usernames.add(username) // Добавляем имя в список имен
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("UserListActivity", "Error getting users", exception)
            }

        userList.setOnItemClickListener { _, _, position, _ ->
            val selectedUser = users[position]
            openChatDialog(selectedUser)
        }
    }

    private fun openChatDialog(selectedUser: User) {
        val intent = Intent(this, ChatActivity::class.java)

        intent.putExtra("userId", selectedUser.userId)
        intent.putExtra("username", selectedUser.username)

        startActivity(intent)
    }
}
