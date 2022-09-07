package com.example.lat_ugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        NavigationBarItemView.OnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.item1 -> {
                    true
                }
                R.id.item2 -> {
                true
            }
            else -> false
        }
    }
}