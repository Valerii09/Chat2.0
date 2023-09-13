package com.example.chat

import android.content.Intent
import com.google.firebase.database.FirebaseDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        val buttonGoToAuth: Button = findViewById(R.id.buttonGoToAuth)

        buttonRegister.setOnClickListener {
            registerUser()
        }

        buttonGoToAuth.setOnClickListener {
            openAuthActivity()
        }
    }

    private fun registerUser() {
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val firstNameEditText = findViewById<EditText>(R.id.editTextFirstName)
        val lastNameEditText = findViewById<EditText>(R.id.editTextLastName)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    saveUserData(user, firstName, lastName, email)
                    openAuthActivity()
                } else {
                    val exception = task.exception
                    if (exception?.localizedMessage != null) {
                        Toast.makeText(
                            this,
                            "Ошибка регистрации: ${exception.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // Добавьте этот лог для отладки
                    Log.e("RegistrationActivity", "Registration failed", exception)
                }
            }
    }

    private fun saveUserData(user: FirebaseUser?, firstName: String, lastName: String, email: String) {
        if (user != null) {
            val userId = user.uid
            val userRef = firestore.collection("users").document(userId)

            val userData = User(userId, firstName, email)
            userRef.set(userData)
                .addOnSuccessListener {
                    // Успешное сохранение данных в Firestore

                    val database = FirebaseDatabase.getInstance()
                    val databaseReference = database.getReference("users")

                    val realtimeUserData = mapOf(
                        "userId" to userId,
                        "username" to firstName, // Ваш выбор, как использовать имя пользователя
                        "email" to email
                    )

                    // Сохранение данных в Realtime Database
                    databaseReference.child(userId).setValue(realtimeUserData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Ошибка при сохранении данных пользователя: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.e("RegistrationActivity", "Failed to save user data in Realtime Database", e)
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Ошибка при сохранении данных пользователя в Firestore: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("RegistrationActivity", "Failed to save user data in Firestore", e)
                }
        }
    }


    private fun openAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}
