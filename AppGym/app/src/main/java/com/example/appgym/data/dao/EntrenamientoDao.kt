package com.example.appgym.data.dao

import androidx.room.*
import com.example.appgym.data.entities.Entrenamiento

@Dao
interface EntrenamientoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(entrenamiento: Entrenamiento)

    @Update
    suspend fun actualizar(entrenamiento: Entrenamiento)

    @Delete
    suspend fun eliminar(entrenamiento: Entrenamiento)

    @Query("SELECT * FROM entrenamientos WHERE usuarioId = :usuarioId")
    suspend fun obtenerPorUsuario(usuarioId: Int): List<Entrenamiento>
}