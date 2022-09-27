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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentProfile : Fragment(){

    var iduser = "id_user"
    var pref = "preference"

    val db by lazy {activity?.let { UserDB(it) }}
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences? = null

        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(pref, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getInt(iduser, 0)

           var username = binding.tvUsername
            var email = binding.tvEmail
            var date = binding.tvDate
            var phone = binding.tvPhone

        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDao()?.getUser(id)?.get(0)
            username.setText(user?.username)
            email.setText(user?.email)
            date.setText(user?.tanggalLahir)
            phone.setText(user?.noTelp)
        }



    }
}


