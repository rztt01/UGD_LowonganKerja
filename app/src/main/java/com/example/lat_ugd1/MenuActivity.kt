package com.example.lat_ugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.lat_ugd1.room.UserDB
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    lateinit var bottomNavigation: BottomNavigationView
    private lateinit var name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val fragment1 = FragmentHome()
        val fragment2 = FragmentProfile()
        val fragment3 = FragmentNotification()

        name = findViewById(R.id.username)

        setCurrentFragment(fragment1)
        bottomNavigation = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigation.setOnNavigationItemReselectedListener {
            if (it.itemId == R.id.page_1) {
                setCurrentFragment(fragment1)
            } else if (it.itemId == R.id.page_2) {
                getUser()
                setCurrentFragment(fragment2)
            } else {
                setCurrentFragment(fragment3)
            }
            true
        }

    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_fragment,fragment)
            commit()
        }
    fun getUser(){
        //userId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            //sermentara id user 0 karena anggapan user hanya 1 saat ini
            val users = db.userDao().getUser(0)[0]
            name.setText(users.username)
        }
    }
}