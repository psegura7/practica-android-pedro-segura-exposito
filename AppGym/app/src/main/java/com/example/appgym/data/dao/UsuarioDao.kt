package com.example.appgym.data.dao

import androidx.room.*
import com.example.appgym.data.entities.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    @Update
    suspend fun actualizar(usuario: Usuario)

    @Delete
    suspend fun eliminar(usuario: Usuario)

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerTodos(): List<Usuario>

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