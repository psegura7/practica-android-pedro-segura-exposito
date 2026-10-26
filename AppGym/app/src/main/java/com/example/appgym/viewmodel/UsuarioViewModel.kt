package com.example.appgym.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgym.data.entities.Usuario
import com.example.appgym.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios

    fun cargarUsuarios() {
        viewModelScope.launch {
            _usuarios.value = repository.obtenerTodos()
        }
    }

    suspend fun registrarUsuario(
        usuario: Usuario
    ) {
        repository.insertarUsuario(usuario)
    }

    fun eliminar(usuario: Usuario) {
        viewModelScope.launch {
            repository.eliminar(usuario)
            cargarUsuarios()
        }
    }
    suspend fun iniciarSesion(
        email: String,
        contraseña: String
    ) = repository.iniciarSesion(email, contraseña)
}