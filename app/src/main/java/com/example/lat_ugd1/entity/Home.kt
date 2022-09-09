package com.example.lat_ugd1.entity

import com.example.lat_ugd1.R

class Home( var lowongan:String, var nama: String, var alamat:String,var gaji:String, var img:Int) {
    companion object{
        @JvmField
        var listOfHome = arrayOf(
            Home("Barista", "Cemara Coffe Shop","Yogyakarta", "Rp 500.000 - Rp 1.000.000", R.drawable.barista),
            Home("Akuntan", "BCA Finance","Jambi", "Rp 1.000.000 - Rp 3.000.000", R.drawable.akuntan),
            Home("Bengkel", "Toyota Indonesia","Jakarta", "Rp 2.500.000 - Rp 5.000.000", R.drawable.bengkel),
            Home("Arsitek", "PT Douland","Seatle", "Rp 4.200.000 - Rp 7.000.000", R.drawable.arsitek),
            Home("Buruh", "Toko Semesta","Manado", "Rp 400.000 - Rp 2.200.000", R.drawable.buruh),
            Home("Driver", "Blue Bird","Bogor", "Rp 1.500.000 - Rp 5.000.000", R.drawable.driver),
            Home("Kasir", "Estuary Cafe","Yogyakarta", "Rp 1.000.000 - Rp 3.000.000", R.drawable.kasir),
            Home("Kuli", "De Britto","Demangan", "Rp 1.500.000 - Rp 4.500.000", R.drawable.kuli),
            Home("Programmer", "Tokopedia","Bali", "Rp 8.000.000 - Rp 20.000.000", R.drawable.progammer),
            Home("Dosen", "Atma Jaya Yogyakarta","Yogyakarta", "Rp 3.000.000 - Rp 10.000.000", R.drawable.dosen)
        )
    }
}