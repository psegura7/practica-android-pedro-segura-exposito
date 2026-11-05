package com.example.appgym.data.dao

import androidx.room.*
import com.example.appgym.data.entities.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // <-- Añade esto para evitar crasheos por duplicados
    suspend fun insertar(usuario: Usuario): Long // <-- Cambiado para devolver el ID generado (Long)

    @Update
    suspend fun actualizar(usuario: Usuario)

    @Delete
    suspend fun eliminar(usuario: Usuario)

    @Query("SELECT * FROM usuarios")
    fun obtenerTodos(): Flow<List<Usuario>>

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email")
    suspend fun obtenerPorEmail(email: String): Usuario?

    @Query("""
        SELECT * FROM usuarios
        WHERE email = :email
        AND contraseña = :contraseña
        LIMIT 1
    """)
    suspend fun iniciarSesion(
        email: String,
        contraseña: String
    ): Usuario?
}