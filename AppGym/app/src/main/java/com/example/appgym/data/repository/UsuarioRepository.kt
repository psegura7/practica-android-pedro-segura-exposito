package com.example.appgym.data.repository

import com.example.appgym.data.dao.UsuarioDao
import com.example.appgym.data.entities.Usuario
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun insertarUsuario(usuario: Usuario) {
        usuarioDao.insertar(usuario)
    }

    fun obtenerTodosLosUsuarios(): Flow<List<Usuario>> {
        return usuarioDao.obtenerTodos()
    }

    suspend fun eliminar(usuario: Usuario) {
        usuarioDao.eliminar(usuario)
    }

    suspend fun iniciarSesion(
        email: String,
        contraseña: String
    ): Usuario? {
        return usuarioDao.iniciarSesion(email, contraseña)
    }

    suspend fun obtenerPorEmail(email: String): Usuario? {
        return usuarioDao.obtenerPorEmail(email)
    }

    suspend fun actualizar(usuario: Usuario) {
        usuarioDao.actualizar(usuario)
    }

    suspend fun obtenerPorId(id: Int): Usuario? {
        return usuarioDao.obtenerPorId(id)
    }
}