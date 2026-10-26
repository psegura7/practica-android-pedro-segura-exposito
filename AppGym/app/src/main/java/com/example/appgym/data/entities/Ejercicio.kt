package com.example.appgym.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ejercicios")
data class Ejercicio(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val descripcion: String,
    val grupoMuscular: String,
    val imagenUrl: String?,
    val videoUrl: String?
)