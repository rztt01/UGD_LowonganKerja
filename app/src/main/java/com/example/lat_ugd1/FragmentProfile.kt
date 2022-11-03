package com.example.lat_ugd1

import android.content.Context
import android.content.Intent
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
    val db by lazy {activity?.let { UserDB(it) }}
    var iduser = "id_user"
    var pref = "preference"

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
        sharedPreferences = activity?.getSharedPreferences(pref, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(iduser,"")!!.toInt()

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

        binding.buttonEdit.setOnClickListener{
            val move = Intent(activity, EditProfilActivity::class.java)
            startActivity(move)
            activity?.finish()
        }
        binding.logout.setOnClickListener{
            val move = Intent(activity, MainActivity::class.java)
            startActivity(move)
            activity?.finish()
        }
        binding.buttonCamera.setOnClickListener{
            val move = Intent(activity, MainActivity::class.java)
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


