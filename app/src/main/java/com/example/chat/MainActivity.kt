package com.example.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonUserList = findViewById<Button>(R.id.buttonUserList)

        // Обработчик нажатия на кнопку "Список пользователей"
        buttonUserList.setOnClickListener(View.OnClickListener {
            // Здесь открывается активность списка пользователей (UserListActivity)
            openUserListActivity()
        })
    }

    private fun openUserListActivity() {
        val intent = Intent(this, UserListActivity::class.java)
        startActivity(intent)
    }
}
