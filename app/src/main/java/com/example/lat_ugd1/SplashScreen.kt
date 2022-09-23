package com.example.lat_ugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)

        if(!sharedPreferences!!.contains("status")){
            setStatus("true")

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            },3000)
        }

        if(sharedPreferences!!.getString("status","")=="true"){
            setStatus("false")
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            },3000)
        }

        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)*/
    }

    private fun setStatus(status:String){
        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()

        editor.putString("status", status)
        editor.apply()
    }
}