package com.example.lat_ugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MenuActivity : AppCompatActivity() {
    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val fragment1 = FragmentHome()
        val fragment2 = FragmentProfile()
        val fragment3 = FragmentNotification()

        setCurrentFragment(fragment1)
        bottomNavigation = findViewById(R.id.bottom_navigation_view) as BottomNavigationView
        bottomNavigation.setOnNavigationItemReselectedListener{
            if()
        }

        NavigationBarItemView.OnItemSelectedListener { item ->
            when (item.itemId) {
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
}