package com.example.appgym.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "rutina_ejercicio",
    foreignKeys = [
        ForeignKey(
            entity = Rutina::class,
            parentColumns = ["id"],
            childColumns = ["rutinaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ejercicio::class,
            parentColumns = ["id"],
            childColumns = ["ejercicioId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RutinaEjercicio(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val rutinaId: Int,
    val ejercicioId: Int,
    val series: Int,
    val repeticiones: Int,
    val peso: Float,
    val descanso: Int,
    val orden: Int
)