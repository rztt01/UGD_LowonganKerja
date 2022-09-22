package com.example.lat_ugd1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lat_ugd1.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentProfile : Fragment(){
    val db by lazy { UserDB(this) }
    private var userId: Int = 0

    private lateinit var username:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
        setupView()
    }
    fun setupView(){
        getUser()
    }
    fun getUser(){
        //userId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            //sermentara id user 0 karena anggapan user hanya 1 saat ini
            val users = db.userDao().getUser(userId)[0]
            username.setText(users.username)
        }
    }
}