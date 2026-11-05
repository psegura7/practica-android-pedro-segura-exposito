package com.example.appgym.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgym.data.database.DatabaseProvider
import com.example.appgym.data.entities.Usuario
import com.example.appgym.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    // Inicializamos la base de datos y el repositorio internamente
    private val db = DatabaseProvider.getDatabase(application)
    private val repository = UsuarioRepository(db.usuarioDao())

    // 1. Lista reactiva de todos los usuarios
    val usuarios: StateFlow<List<Usuario>> = repository.obtenerTodosLosUsuarios()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // <-- Directamente SharingStarted
            initialValue = emptyList()
        )
    // 2. Estado del usuario logueado actualmente
    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null)
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado.asStateFlow()

    fun registrarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repository.insertarUsuario(usuario)
        }
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