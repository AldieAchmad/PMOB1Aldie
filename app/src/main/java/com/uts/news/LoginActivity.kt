package com.uts.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LoginActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val pass = intent.getStringExtra("password")
        val email = intent.getStringExtra("email")
        val etEmail = findViewById<EditText>(R.id.username_edittext)
        val etPassword = findViewById<EditText>(R.id.password_edittext)
        val btnLogin = findViewById<Button>(R.id.login_button)

        btnLogin.setOnClickListener {
            if (email == etEmail.text.toString() && pass == etPassword.text.toString()) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()

                Log.d("Login Activity", "Login: Login Berhasil")
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT)
                    .show()
            }else{
                etPassword.setText("")

                Log.e("Login Activity", "Login: Username atau Password Salah")
                Toast.makeText(this, "Username atau Password Salah", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}