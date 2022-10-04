package com.example.lat_ugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lat_ugd1.databinding.ActivityEditProfilBinding
import com.example.lat_ugd1.room.User
import com.example.lat_ugd1.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfilActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    var sharedPreferences: SharedPreferences? = null
    private lateinit var binding: ActivityEditProfilBinding

    private val key = "id_user"
    private val myPreferences = "preference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(key, "")!!.toInt()

        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDao()?.getUser(id)?.get(0)
            withContext(Dispatchers.Main) {
                binding.username.setText(user?.username)
                binding.email.setText(user?.email)
                binding.tglLahir.setText(user?.tanggalLahir)
                binding.noTelp.setText(user?.noTelp)
            }
        }

        binding.btnSave.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().updateUser(
                    User(
                        id,
                        binding.username.text.toString(),"",
                        binding.email.text.toString(),
                        binding.tglLahir.text.toString(),
                        binding.noTelp.text.toString()
                    )
                )
            }
            finish()
            val intent = Intent(this, MenuActivity::class.java)
            val bundle = Bundle()
            bundle.putString("key", "filled")
            intent.putExtra("keyBundle", bundle)
            startActivity(intent)

        }




    }


}