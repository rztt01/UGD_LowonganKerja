package com.example.lat_ugd1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lat_ugd1.api.UserApi
import com.example.lat_ugd1.databinding.ActivityRegisterBinding
import com.example.lat_ugd1.models.User
import com.example.lat_ugd1.room.UserDB
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val CHANNEL_ID_1 = "channerl_notification_01"
    private val notificationId1 = 101

    private  var queue: RequestQueue? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createNotificationChannel()

        queue= Volley.newRequestQueue(this)

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
            val dataUser = Bundle()

            val username: String = inputUserNameRegister.getEditText()?.getText().toString()
            val password: String = inputPasswordRegister.getEditText()?.getText().toString()
            val confirm: String = inputConfirmPasswordRegister.getText().toString()
            val email: String = inputEmailRegister.getEditText()?.getText().toString()
            val tanggallahir: String =  inputTanggalLahir.getEditText()?.getText().toString()
            val notelp: String = inputNoTelp.getEditText()?.getText().toString()

            val user = User(username, password, email, tanggallahir, notelp )

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

                val stringRequest:StringRequest = object : StringRequest(Method.POST,UserApi.ADD_URL,
                    Response.Listener { response ->
                        val gson = Gson()
                        val user = gson.fromJson(response, User::class.java)

                        if (user != null){
                            Toast.makeText(this@RegisterActivity, "Data User Berhasi Ditambah", Toast.LENGTH_LONG).show()
                            sendNotification()
                            dataUser.putString("name", username)
                            dataUser.putString("pass", password)

                        }

                        val returnIntent = Intent()
                        setResult(RESULT_OK, returnIntent)
                        finish()
                    }, Response.ErrorListener { error ->
                        try {
                            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            Toast.makeText(
                                this@RegisterActivity,
                                errors.getString("Error: message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }catch (e: java.lang.Exception){
                            Log.d("Error di mana", e.message.toString())
                            Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                ){
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "aplication/json"
                        return headers
                    }

                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray{
                        val gson = Gson()
                        val requestBody = gson.toJson(user)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }

                queue!!.add(stringRequest)

                val moveHome = Intent(this@RegisterActivity, MainActivity::class.java)

                moveHome.putExtras(dataUser)
                startActivity(moveHome)
            }

        })

    }

    private fun sendNotification() {
        val intent : Intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0,intent, 0)

        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        //broadcastIntent.putExtra("toastMessage",)
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val bitmap_bigPicture = ContextCompat.getDrawable(this, R.drawable.ic_regis_success)?.toBitmap()

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(bitmap_bigPicture)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
            .setContentTitle("Notification Register")
            .setContentText("Helo! Register Success.")
            .setStyle(bigPictureStyle)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // untuk menentukan posisi notif dimana
        with(NotificationManagerCompat.from(this)){
            notify(notificationId1,builder.build())
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }

    }
}