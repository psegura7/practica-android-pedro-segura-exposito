package com.example.appgym.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgym.data.entities.Usuario
import com.example.appgym.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    // 1. Lista reactiva de todos los usuarios
    val usuarios: StateFlow<List<Usuario>> = repository.obtenerTodosLosUsuarios()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 2. Estado del usuario logueado actualmente
    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null)
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado

    fun cargarUsuarios() {
        // Room maneja la reactividad automáticamente mediante el Flow
    }

    suspend fun registrarUsuario(usuario: Usuario) {
        repository.insertarUsuario(usuario)
    }

    fun eliminar(usuario: Usuario) {
        viewModelScope.launch {
            repository.eliminar(usuario)
        }
    }

    suspend fun iniciarSesion(email: String, contraseña: String): Usuario? {
        val usuario = repository.iniciarSesion(email, contraseña)
        if (usuario != null) {
            _usuarioLogueado.value = usuario // Guardamos el usuario activo
        }
        return usuario
    }

    // 3. Función para actualizar el perfil propio
    fun actualizarPerfil(usuarioActualizado: Usuario) {
        viewModelScope.launch {
            repository.actualizar(usuarioActualizado)
            _usuarioLogueado.value = usuarioActualizado
        }
    }

    // 4. Función para buscar un usuario ajeno por su ID
    suspend fun obtenerUsuarioPorId(id: Int): Usuario? {
        return repository.obtenerPorId(id)
    }
}