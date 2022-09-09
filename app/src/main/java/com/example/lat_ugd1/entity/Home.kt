package com.example.lat_ugd1.entity

import com.example.lat_ugd1.R

class Home( var lowongan:String, var nama: String, var alamat:String,var gaji:String, var img:Int) {
    companion object{
        @JvmField
        var listOfHome = arrayOf(
            Home("Barista", "Cemara Coffe Shop","jl.Babarsai, Yogyakarta", "Rp 500.000 - Rp 1.000.000", R.drawable.barista),
        )
    }
}