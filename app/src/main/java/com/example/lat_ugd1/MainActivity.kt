package com.example.lat_ugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    // Atribute yang akan kita pakai
    private lateinit var inputUserName: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setText()

        supportActionBar?.hide()

        // Ubah Title pada App Bar Aplikasi
        setTitle("User Login")

        // Hubungkan variabel dengan view di layoutnya.
        inputUserName = findViewById(R.id.inputUsername)
        inputPassword = findViewById(R.id.inputPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        // Aksi pada btnLogin
        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val userData = intent.extras
            val username: String = inputUserName.getText().toString()
            val password: String = inputPassword.getText().toString()

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

            if (userData != null) {
                if (username != userData.getString("username")) {
                    inputUserName.setError("Username incorrect")
                    checkLogin = false
                } else if (password != userData.getString("password")) {
                    inputPassword.setError("Password incorrect")
                    checkLogin = false
                }
            }


            if(userData != null){
                if (username == userData.getString("username") && password == userData.getString("password")) {
                    checkLogin = true
                }
            }

            if (checkLogin != true) {
                return@OnClickListener
            }else{
                Snackbar.make(mainLayout, "Login Successful!", Snackbar.LENGTH_LONG).show()
                val moveHome = Intent(this@MainActivity, MenuActivity::class.java)
                startActivity(moveHome)
            }

        })

        // Aksi btnRgister ketika di klik
        btnRegister.setOnClickListener(View.OnClickListener {
            var checkRegister = false
            val username: String = inputUserName.getText().toString()
            val password: String = inputPassword.getText().toString()

            if (checkRegister == true) return@OnClickListener
            val moveRegister = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        })

        }
        fun setText(){
            inputUserName = findViewById(R.id.inputUsername)
            inputPassword = findViewById(R.id.inputPassword)
            val userData = intent.extras

            if(userData!=null){
                inputUserName.setText(userData.getString("username"))
                inputPassword.setText(userData.getString("password"))
            }
        }
    }
