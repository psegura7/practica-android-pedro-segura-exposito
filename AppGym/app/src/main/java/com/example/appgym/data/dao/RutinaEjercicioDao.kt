package com.example.appgym.data.dao

import androidx.room.*
import com.example.appgym.data.entities.RutinaEjercicio

@Dao
interface RutinaEjercicioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(rutinaEjercicio: RutinaEjercicio)

    @Update
    suspend fun actualizar(rutinaEjercicio: RutinaEjercicio)

    @Delete
    suspend fun eliminar(rutinaEjercicio: RutinaEjercicio)

    @Query("SELECT * FROM rutina_ejercicio WHERE rutinaId = :rutinaId ORDER BY orden")
    suspend fun obtenerPorRutina(rutinaId: Int): List<RutinaEjercicio>
}