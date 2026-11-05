package com.example.appgym.data.dao
import androidx.room.*
import com.example.appgym.data.entities.Ejercicio

@Dao
interface EjercicioDao {
    @Query("SELECT * FROM Ejercicio")
    suspend fun obtenerTodos(): List<Ejercicio> // <-- Añade suspend

    @Insert
    suspend fun insertar(ejercicio: Ejercicio): Long
}