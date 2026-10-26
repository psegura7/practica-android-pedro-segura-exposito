package com.example.appgym.data.dao

import androidx.room.*
import com.example.appgym.data.entities.EntrenamientoEjercicio

@Dao
interface EntrenamientoEjercicioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(entrenamientoEjercicio: EntrenamientoEjercicio)

    @Update
    suspend fun actualizar(entrenamientoEjercicio: EntrenamientoEjercicio)

    @Delete
    suspend fun eliminar(entrenamientoEjercicio: EntrenamientoEjercicio)

    @Query("SELECT * FROM entrenamiento_ejercicio WHERE entrenamientoId = :entrenamientoId")
    suspend fun obtenerPorEntrenamiento(entrenamientoId: Int): List<EntrenamientoEjercicio>
}