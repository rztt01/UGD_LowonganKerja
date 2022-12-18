package com.example.lat_ugd1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lat_ugd1.databinding.ActivityPdfactivityBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itextpdf.kernel.geom.Line
import com.squareup.picasso.Picasso

class ShowPerusahaanActivity : AppCompatActivity() {
    lateinit var bundle: Bundle
    val dataUser = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_perusahaan)

        val url_1: String = "https://cdn.sbvu.ac.in/wp-content/uploads/2020/04/Mahatma-Gandhi-medical-College-and-research-Institute-1-1.jpg"
        val url_2: String = "https://i.pinimg.com/originals/65/99/b2/6599b266be6f5ee2c598c907710ba018.png"

        val imgGlide = findViewById<ImageView>(R.id.ImgViewGlide1)
        val imgPicasso = findViewById<ImageView>(R.id.ImgViewPicasso)

        //Library Glide
        Glide
            .with(this)
            .load(url_1)
            .placeholder(R.drawable.animation)
            .error(R.drawable.img_2)
            .into(imgGlide)

        //Library Picasso
        Picasso.get()
            .load(url_2)
            .placeholder(R.drawable.animation)
            .error(R.drawable.img_2)
            .into(imgPicasso)

        val idUser = getBundle()
        val back = findViewById<FloatingActionButton>(R.id.back)
        back.setOnClickListener{
            dataUser.putInt("idUser", idUser)
            val close = Intent(this@ShowPerusahaanActivity, MenuActivity::class.java)
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