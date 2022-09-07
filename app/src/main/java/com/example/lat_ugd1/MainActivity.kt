package com.example.lat_ugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    // Atribute yang akan kita pakai
    private lateinit var inputUserName: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ubah Title pada App Bar Aplikasi
        setTitle("User Login")

        // Hubungkan variabel dengan view di layoutnya.
        inputUserName = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        // Aksi pada btnLogin
        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = inputUserName.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            // Pengecekan apakah inputan kosong
            if (username.isEmpty()) {
                inputUserName.setError("Username must be filled with text")
                checkLogin = false
            }

            // Pengecekan apakah inputan kosong
            if (password.isEmpty()) {
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            // Ganti Password dengan NPM kalian.
            if (username == "admin" && password == "0724") {
                inputUserName.getEditText()?.setText("")
                inputPassword.getEditText()?.setText("")
                checkLogin = true
                Snackbar.make(mainLayout, "Login Successful!", Snackbar.LENGTH_LONG).show()
            }

        })

        // Aksi btnRgister ketika di klik
        btnRegister.setOnClickListener(View.OnClickListener {
            var checkRegister = false
            val username: String = inputUserName.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            if (checkRegister == true) return@OnClickListener
            val moveHome = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(moveHome)
        })



        }
    }
