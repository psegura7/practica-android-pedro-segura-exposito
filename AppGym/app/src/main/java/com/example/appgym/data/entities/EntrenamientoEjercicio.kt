package com.example.appgym.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "entrenamiento_ejercicio",
    foreignKeys = [
        ForeignKey(
            entity = Entrenamiento::class,
            parentColumns = ["id"],
            childColumns = ["entrenamientoId"],
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
data class EntrenamientoEjercicio(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val entrenamientoId: Int,
    val ejercicioId: Int,
    val seriesRealizadas: Int,
    val repeticionesRealizadas: Int,
    val pesoUtilizado: Float
)