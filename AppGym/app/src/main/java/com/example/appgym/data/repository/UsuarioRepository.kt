package com.example.appgym.data.repository

import com.example.appgym.data.dao.UsuarioDao
import com.example.appgym.data.entities.Usuario

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun insertarUsuario(usuario: Usuario) {
        usuarioDao.insertar(usuario)
    }

    suspend fun obtenerTodos(): List<Usuario> {
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
}