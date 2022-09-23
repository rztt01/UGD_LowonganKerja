package com.example.lat_ugd1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lat_ugd1.databinding.FragmentProfileBinding
import com.example.lat_ugd1.room.UserDB

class FragmentProfile : Fragment(){

    val db by lazy {activity?.let { UserDB(it) }}
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var iduser = "id_user"
        var pref = "preference"
        var sharedPreferences : SharedPreferences? = null

        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(pref, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getInt(iduser, 0)
        val prefe = sharedPreferences!!.getString(pref, "")

//        var username = binding.root.tvUsername
//        var email = binding.root.tvEmail
//        var date = binding.root.tvDate
//        var phone = binding.root.tvPhone
//
//
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = db?.userDao()?.getUser(id)?.get(0)
//            username.setText(user?.username)
//            email.setText(user?.email)
//            date.setText(user?.tanggalLahir)
//            phone.setText(user?.noTelp)
//        }



    }
}