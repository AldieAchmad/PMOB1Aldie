package com.uts.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.username_edittext)
        val password = findViewById<EditText>(R.id.password_edittext)
        val btnRegistrasi = findViewById<Button>(R.id.register_button)
        btnRegistrasi.setOnClickListener {
            if (!username.text.isNullOrEmpty() && !password.text.isNullOrEmpty()) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.putExtra("email", username.text.toString())
                intent.putExtra("password", password.text.toString())
                startActivity(intent)
                finish()

                Log.d("Register Activity", "Register: Pendaftaran Berhasil")
                Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Log.e("Register Activity", "Username & Password Tidak Boleh Kosong")
                Toast.makeText(this, "Username & Password Tidak Boleh Kosong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}