package com.example.appgym.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["email"], unique = true)]
)
data class Usuario(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombreUsuario: String,
    val email: String,
    val contraseña: String,
    val fotoPerfil: String?,
    val fechaRegistro: String,
    val edad: Int
)