package com.example.lat_ugd1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lat_ugd1.room.UserDB
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {
    lateinit var bundle: Bundle
    val db by lazy { UserDB(this) }
    lateinit var bottomNavigation: BottomNavigationView
    private lateinit var name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_menu)
        val userId = getBundle()
        val fragment1 = FragmentHome()
        val fragment2 = FragmentLocation(userId)
        val fragment3 = FragmentProfile(userId)
        val fragment4 = FragmentNotification()


        setCurrentFragment(fragment1)
        bottomNavigation = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigation.setOnNavigationItemReselectedListener {
            if (it.itemId == R.id.page_1) {
                setCurrentFragment(fragment1)
            } else if (it.itemId == R.id.page_2) {
//                getUser()
                setCurrentFragment(fragment2)
            } else if (it.itemId == R.id.page_3){
                setCurrentFragment(fragment3)
            } else if (it.itemId == R.id.page_3){
                setCurrentFragment(fragment4)
            }
            true
        }

    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_fragment,fragment)
            commit()
        }

    fun getBundle():Int{
        bundle = intent.getBundleExtra("idUser")!!
        var idUser : Int = bundle.getInt("idUser")!!
        return idUser
    }
}