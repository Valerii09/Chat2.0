package com.example.chat

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Получите информацию о пользователе из предыдущей активности или из вашего хранилища данных
        val username = intent.getStringExtra("username")
        val email = intent.getStringExtra("email")

        // Найдите элементы интерфейса в XML-макете и установите значения
        val textViewUsername = findViewById<TextView>(R.id.textViewUsername)
        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)

        textViewUsername.text = username
        textViewEmail.text = email

        // Вы можете добавить код для отображения другой личной информации о пользователе
    }
}
