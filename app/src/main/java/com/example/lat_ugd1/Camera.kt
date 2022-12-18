package com.example.lat_ugd1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.Camera
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import java.lang.Exception
import java.util.ResourceBundle.getBundle

class Camera : AppCompatActivity() {
    lateinit var bundle: Bundle
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null

    val dataUser = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val idUser = getBundle()
        try{
            mCamera = Camera.open()
        }catch (e:Exception){
            Log.d("Error", "Failed to get Camera" + e.message)
        }
        if (mCamera != null){
            mCameraView = CameraView(this, mCamera!!)
            val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
            camera_view.addView(mCameraView)
        }
        @SuppressLint("MissingINdlatedId", "LocalSuppress") val imageClose =
            findViewById<View>(R.id.imgClose) as ImageButton
        imageClose.setOnClickListener{

            dataUser.putInt("idUser", idUser)
            val close = Intent(this@Camera, MenuActivity::class.java)
            close.putExtra("idUser",dataUser)
            startActivity(close)
        }
    }
    fun getBundle():Int{
        bundle = intent.getBundleExtra("idUser")!!
        var idUser : Int = bundle.getInt("idUser")!!
        return idUser
    }
}