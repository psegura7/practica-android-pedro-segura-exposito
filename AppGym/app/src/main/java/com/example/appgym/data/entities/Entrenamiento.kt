package com.example.appgym.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "entrenamientos",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Rutina::class,
            parentColumns = ["id"],
            childColumns = ["rutinaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Entrenamiento(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val usuarioId: Int,
    val rutinaId: Int,
    val fecha: String,
    val duracion: Int,
    val volumenTotal: Float
)