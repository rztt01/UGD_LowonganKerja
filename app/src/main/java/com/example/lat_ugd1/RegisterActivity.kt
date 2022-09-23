package com.example.lat_ugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lat_ugd1.databinding.ActivityRegisterBinding
import com.example.lat_ugd1.room.User
import com.example.lat_ugd1.room.UserDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    val db by lazy { UserDB(this) }
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        val dataUser = Bundle()

        val inputUserNameRegister = binding.inputLayoutUsername2
        val inputPasswordRegister= binding.inputLayoutPassword2
        val inputConfirmPasswordRegister = binding.inputConfirmPassword
        val inputTanggalLahir = binding.inputLayoutTanggalLahir
        val inputEmailRegister = binding.inputLayoutEmail
        val inputNoTelp = binding.inputLayoutNoTelp

        val btnRegister2: Button = binding.btnRegister2
        // Aksi pada btnLogin
        btnRegister2.setOnClickListener(View.OnClickListener {
            var checkRegister = false

            val username: String = inputUserNameRegister.getEditText()?.getText().toString()
            val password: String = inputPasswordRegister.getEditText()?.getText().toString()
            val confirm: String = inputConfirmPasswordRegister.getText().toString()
            val email: String = inputEmailRegister.getEditText()?.getText().toString()
            val tanggallahir: String =  inputTanggalLahir.getEditText()?.getText().toString()
            val notelp: String = inputNoTelp.getEditText()?.getText().toString()

            // Pengecekan apakah inputan kosong
            if (username.isEmpty()) {
                inputUserNameRegister.setError("Username must be filled with text")
                checkRegister = false
            }

            // Pengecekan apakah inputan kosong
            if (password.isEmpty()) {
                inputPasswordRegister.setError("Password must be filled with text")
                checkRegister = false
            // Pengecekan apakah input confirm password sama dengan password
            } else if (!password.equals(confirm)) {
                inputConfirmPasswordRegister.setError("Password would not be matched")
                checkRegister = false
            }

            // Pengecekan apakah inputan kosong
            if (email.isEmpty()) {
                inputEmailRegister.setError("Email must be filled with text")
                checkRegister = false
            }

            // Pengecekan apakah inputan kosong
            if (tanggallahir.isEmpty()) {
                inputTanggalLahir.setError("Tanggal Lahir must be filled with text")
                checkRegister = false
            }

            // Pengecekan apakah inputan kosong
            if (notelp.isEmpty()) {
                inputNoTelp.setError("Nomor Telpon must be filled with text")
                checkRegister = false
            }

            if(username.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && email.isNotEmpty() && tanggallahir.isNotEmpty() && notelp.isNotEmpty() && password == confirm) {
                dataUser.putString("username", username)
                dataUser.putString("password", password)
                checkRegister = true
            }

            if (!checkRegister) return@OnClickListener

            if (checkRegister == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().addUser(
                        User(0, username, password, email, tanggallahir, notelp)
                    )
                    finish()
                }
                val moveHome = Intent(this@RegisterActivity, MainActivity::class.java)
                moveHome.putExtras(dataUser)
                startActivity(moveHome)
            }

        })
    }
}