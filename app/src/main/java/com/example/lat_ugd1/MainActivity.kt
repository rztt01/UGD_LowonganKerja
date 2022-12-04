package com.example.lat_ugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lat_ugd1.api.UserApi
import com.example.lat_ugd1.models.User
import com.example.lat_ugd1.room.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    // Atribute yang akan kita pakai
    private lateinit var inputUserName: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout
    var sharedPreferences : SharedPreferences? = null

    private  var queue: RequestQueue? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setText()
        var iduser = "id_user"
        var pref = "preference"
        val moveMainMenu = Intent(this,MenuActivity::class.java)

        queue= Volley.newRequestQueue(this)

        supportActionBar?.hide()

        // Ubah Title pada App Bar Aplikasi
        setTitle("User Login")

        // Hubungkan variabel dengan view di layoutnya.
        inputUserName = findViewById(R.id.inputUsername)
        inputPassword = findViewById(R.id.inputPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE)

        // Aksi pada btnLogin
        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val userData = intent.extras
            val username: String = inputUserName.getText().toString()
            val password: String = inputPassword.getText().toString()
            val dataUser = Bundle()

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
                inputUserName.setText(userData.getString("name"))
                inputPassword.setText(userData.getString("pass"))

            }

            if (!username.isEmpty() && !password.isEmpty()){
                val stringRequest: StringRequest = object : StringRequest(Method.GET, UserApi.GET_ALL_URL,
                    Response.Listener { response ->
                        val gson = Gson()
                        var userList: Array<User> = gson.fromJson(response,Array<User>::class.java)
                        if(userList.isEmpty()){
                            FancyToast.makeText(this@MainActivity, "Tidak ada user terdaftar", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                        }else{
                            for (user in userList){
                                if(username == user.username){
                                    if(password == user.password){
                                        checkLogin = true
                                        break
                                    }else{
                                        FancyToast.makeText(this@MainActivity, "Password salah", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                                    }
                                }
                            }
                            if(!checkLogin){
                                FancyToast.makeText(this@MainActivity, "Username salah", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                            }else{
                                FancyToast.makeText(this@MainActivity, "Login Successful!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()

                                val moveHome = Intent(this@MainActivity, MenuActivity::class.java)

                                startActivity(moveHome)
                            }

                        }
                    },Response.ErrorListener { error ->
                        try {
                            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            FancyToast.makeText(
                                this@MainActivity,
                                errors.getString("Error: message"),
                                FancyToast.LENGTH_SHORT,
                                FancyToast.ERROR,
                                false
                            ).show()
                        }catch (e: java.lang.Exception){
                            Log.d("Error di mana", e.message.toString())
                            FancyToast.makeText(this@MainActivity, e.message, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()
                        }
                    }
                ){
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "aplication/json"
                        return headers
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
                queue!!.add(stringRequest)
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
