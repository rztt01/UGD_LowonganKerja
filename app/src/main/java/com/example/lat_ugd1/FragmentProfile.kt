package com.example.lat_ugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lat_ugd1.api.UserApi
import com.example.lat_ugd1.databinding.FragmentProfileBinding
import com.example.lat_ugd1.models.User
import com.example.lat_ugd1.room.UserDB
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class FragmentProfile(userId: Int) : Fragment(){
//    val db by lazy {activity?.let { UserDB(it) }}
//    var iduser = "id_user"
//    var pref = "preference"

    private var queue : RequestQueue? = null
    lateinit var bundle: Bundle

    private var userId = userId

    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notificationId1 = 101
    var sharedPreferences : SharedPreferences? = null

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
//        createNotificationChannel()
        super.onViewCreated(view, savedInstanceState)
//        sharedPreferences = activity?.getSharedPreferences(pref, Context.MODE_PRIVATE)
//        val id = sharedPreferences!!.getString(iduser,"")!!.toInt()

        queue = Volley.newRequestQueue(context)

        var username = binding.tvUsername
        var email = binding.tvEmail
        var date = binding.tvDate
        var phone = binding.tvPhone

//        CoroutineScope(Dispatchers.IO).launch {
//            val user = db?.userDao()?.getUser(id)?.get(0)
//            username.setText(user?.username)
//            email.setText(user?.email)
//            date.setText(user?.tanggalLahir)
//            phone.setText(user?.noTelp)
//        }

        val stringRequest: StringRequest = object : StringRequest(Method.GET, UserApi.GET_BY_ID_URL + userId,
        Response.Listener { response ->
                val gson = Gson()
                val user: User = gson.fromJson(response,User::class.java)

                username.setText(user.username)
                email.setText(user.email)
                date.setText(user.tanggalLahir)
                phone.setText(user.noTelp)

            },Response.ErrorListener { error->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        context,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                }catch (e: Exception){
                    Log.d("Error Login", e.message.toString())
                    Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
                }
            }
        ){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String,String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }

        queue!!.add(stringRequest)


        binding.buttonEdit.setOnClickListener{
            val dataUser = Bundle()
            val move = Intent(activity, EditProfilActivity::class.java)
            dataUser.putInt("idUser", userId)
            move.putExtra("idUser", dataUser)
            startActivity(move)
            activity?.finish()
        }
        binding.logout.setOnClickListener{
            val move = Intent(activity, MainActivity::class.java)
            startActivity(move)
            activity?.finish()
        }
        binding.buttonCamera.setOnClickListener{
            val dataUser = Bundle()
            val move = Intent(activity, Camera::class.java)
            dataUser.putInt("idUser", userId)
            move.putExtra("idUser", dataUser)
            startActivity(move)
            activity?.finish()
        }

    }


    fun editProfil(value: Boolean){
        binding.tvUsername.isEnabled = value
        binding.tvEmail.isEnabled = value
        binding.tvPhone.isEnabled = value
        binding.tvDate.isEnabled = value
    }

////    private fun createNotificationChannel(){
////        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
////            val name = "Notification Title"
////            val descriptionText = "Notification Description"
////
////            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply{
////                description = descriptionText
////            }
////
////            val notificationManager: NotificationManager =
////                getSystemSe(Context.NOTIFICATION_SERVICE) as NotificationManager
////            notificationManager.createNotificationChannel(channel1)
////        }
////    }
//
//    private fun sendNotification1(){
//
//        val intent: Intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//        val broadcastIntent: Intent = Intent(this, NotificationReceiver::class.java)
//        broadcastIntent.putExtra("toastMessage", edit_note.text.toString())
//        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
//            .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
//            .setContentTitle(edit_note.text.toString())
//            .setStyle(
//                NotificationCompat.BigTextStyle()
//                    .bigText(edit_note.text.toString())
//                    .setBigContentTitle("Big Context")
//                    .setSummaryText("Summary Text"))
//            .setContentIntent(pendingIntent)
//            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//            .setColor(Color.RED)
//            .setAutoCancel(true)
//            .setOnlyAlertOnce(true)
//            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//
//        with(NotificationManagerCompat.from(this)){
//            notify(notificationId1, builder.build())
//        }
//    }
}


