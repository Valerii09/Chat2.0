package com.example.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        auth = FirebaseAuth.getInstance()

        // Обработчик нажатия на кнопку "Войти"
        buttonLogin.setOnClickListener(View.OnClickListener {
            // Здесь обработайте вход пользователя
            login()
        })

        // Обработчик нажатия на кнопку "Зарегистрироваться"
        buttonRegister.setOnClickListener(View.OnClickListener {
            // Здесь перейдите к активности регистрации
            openRegisterActivity()
        })
    }

    private fun login() {
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Успешная авторизация
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Авторизация успешна", Toast.LENGTH_SHORT).show()
                    openMainActivity()
                } else {
                    // Ошибка авторизации
                    Toast.makeText(this, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun openRegisterActivity() {

        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun openMainActivity() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
