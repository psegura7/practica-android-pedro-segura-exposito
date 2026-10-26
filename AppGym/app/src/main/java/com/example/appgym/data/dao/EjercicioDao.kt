package com.example.appgym.data.dao

import androidx.room.*
import com.example.appgym.data.entities.Ejercicio

@Dao
interface EjercicioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(ejercicio: Ejercicio)

    @Update
    suspend fun actualizar(ejercicio: Ejercicio)

    @Delete
    suspend fun eliminar(ejercicio: Ejercicio)

    @Query("SELECT * FROM ejercicios")
    suspend fun obtenerTodos(): List<Ejercicio>

    @Query("SELECT * FROM ejercicios WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Ejercicio?
}