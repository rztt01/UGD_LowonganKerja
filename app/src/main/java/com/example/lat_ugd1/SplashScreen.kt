package com.example.lat_ugd1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {

    var prevStarted = "yes"
    override fun onResume() {
        super.onResume()
        val isTrue: Boolean = true
        val isFalse: Boolean = false
        val sharedpreferences: SharedPreferences

        sharedpreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        if (!sharedpreferences.getBoolean(prevStarted, isFalse)) {
            val editor = sharedpreferences.edit()
            editor.putBoolean(prevStarted, isTrue)
            editor.apply()
        } else {
            moveToSecondary()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun moveToSecondary() {
        // use an intent to travel from one activity to another.
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}