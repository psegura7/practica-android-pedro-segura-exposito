package com.example.appgym.data.dao

import androidx.room.*
import com.example.appgym.data.entities.Rutina

@Dao
interface RutinaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(rutina: Rutina)

    @Update
    suspend fun actualizar(rutina: Rutina)

    @Delete
    suspend fun eliminar(rutina: Rutina)

    @Query("SELECT * FROM rutinas")
    suspend fun obtenerTodas(): List<Rutina>

    @Query("SELECT * FROM rutinas WHERE usuarioId = :usuarioId")
    suspend fun obtenerPorUsuario(usuarioId: Int): List<Rutina>
}