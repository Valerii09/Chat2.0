package com.example.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R.id


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Элементы интерфейса для ввода данных пользователя
        val buttonSignIn = parent.findViewById<Button>(R.id.buttonSignIn)

        // Обработчик нажатия на кнопку "Войти"
        buttonSignIn.setOnClickListener(View.OnClickListener {

            // Если авторизация успешна, перейдите к главной активности
            openMainActivity()
        })
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
