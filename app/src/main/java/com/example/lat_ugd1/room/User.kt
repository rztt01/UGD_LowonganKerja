package com.example.lat_ugd1.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val tanggalLahir: String,
    val noTelp: String
)