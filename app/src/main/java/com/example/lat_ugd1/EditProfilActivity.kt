package com.example.lat_ugd1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lat_ugd1.api.UserApi
import com.example.lat_ugd1.databinding.ActivityEditProfilBinding
import com.example.lat_ugd1.models.User
import com.example.lat_ugd1.room.UserDB
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class EditProfilActivity : AppCompatActivity() {
    lateinit var bundle: Bundle
    private var queue: RequestQueue? = null
    val db by lazy { UserDB(this) }
    var sharedPreferences: SharedPreferences? = null
    private lateinit var binding: ActivityEditProfilBinding

    private var password :String = ""
    val dataUser = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val idUser = getBundle()
        queue= Volley.newRequestQueue(this)
        getUserById(idUser)

        binding.btnSave.setOnClickListener { updateUser(idUser)}


//        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
//        val id = sharedPreferences!!.getString(key, "")!!.toInt()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = db?.userDao()?.getUser(id)?.get(0)
//            withContext(Dispatchers.Main) {
//                binding.username.setText(user?.username)
//                binding.email.setText(user?.email)
//                binding.tglLahir.setText(user?.tanggalLahir)
//                binding.noTelp.setText(user?.noTelp)
//            }
//        }
//
//        binding.btnSave.setOnClickListener() {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.userDao().updateUser(
//                    User(
//                        id,
//                        binding.username.text.toString(),"",
//                        binding.email.text.toString(),
//                        binding.tglLahir.text.toString(),
//                        binding.noTelp.text.toString()
//                    )
//                )
//            }
//            finish()
//            val intent = Intent(this, MenuActivity::class.java)
//            val bundle = Bundle()
//            bundle.putString("key", "filled")
//            intent.putExtra("keyBundle", bundle)
//            startActivity(intent)
//
//        }
    }

    fun getBundle():Int{
        bundle = intent.getBundleExtra("idUser")!!
        var idUser : Int = bundle.getInt("idUser")!!
        return idUser
    }

    private fun getUserById(id: Int) {
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, UserApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val user = gson.fromJson(response,User::class.java)

                binding.username.setText(user.username)
                binding.email.setText(user.email)
                binding.tglLahir.setText(user.tanggalLahir)
                binding.noTelp.setText(user.noTelp)

                password = user.password


                FancyToast.makeText(this@EditProfilActivity, "Data berhasil diambil!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
            }, Response.ErrorListener { error ->

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(
                        this@EditProfilActivity,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,false
                    ).show()
                } catch (e: Exception) {
                    FancyToast.makeText(this@EditProfilActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    private fun updateUser(id: Int) {

        val user = User(
            binding.username!!.text.toString(),password,
            binding.email!!.text.toString(),
            binding.tglLahir!!.text.toString(),
            binding.noTelp!!.text.toString(),
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, UserApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()

                var user = gson.fromJson(response, User::class.java)

                if (user != null)
                    FancyToast.makeText(
                        this@EditProfilActivity,
                        "Data berhasil diupdate",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,false
                    ).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)

                dataUser.putInt("idUser", id)
                val moveHome = Intent(this@EditProfilActivity, MenuActivity::class.java)
                moveHome.putExtra("idUser",dataUser)
                startActivity(moveHome)
                finish()
            }, Response.ErrorListener { error ->

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(
                        this@EditProfilActivity,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } catch (e: Exception) {
                    FancyToast.makeText(this@EditProfilActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(user)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }
}