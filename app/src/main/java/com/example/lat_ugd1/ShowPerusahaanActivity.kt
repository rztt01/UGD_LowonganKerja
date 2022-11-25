package com.example.lat_ugd1

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class ShowPerusahaanActivity : AppCompatActivity() {
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
    }
}