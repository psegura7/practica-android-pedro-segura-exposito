package com.example.appgym.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgym.data.database.DatabaseProvider
import com.example.appgym.data.entities.Ejercicio
import com.example.appgym.data.entities.Rutina
import com.example.appgym.data.entities.RutinaEjercicio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RutinaViewModel(application: Application) : AndroidViewModel(application) {
    private val db = DatabaseProvider.getDatabase(application)
    private val rutinaDao = db.rutinaDao()
    private val ejercicioDao = db.ejercicioDao()
    private val rutinaEjercicioDao = db.rutinaEjercicioDao()

    private val _rutinas = MutableStateFlow<List<Rutina>>(emptyList())
    val rutinas: StateFlow<List<Rutina>> = _rutinas.asStateFlow()

    // Lista de todos los ejercicios disponibles en la base de datos
    private val _ejerciciosDisponibles = MutableStateFlow<List<Ejercicio>>(emptyList())
    val ejerciciosDisponibles: StateFlow<List<Ejercicio>> = _ejerciciosDisponibles.asStateFlow()

    init {
        cargarEjercicios()
    }

    fun cargarEjercicios() {
        viewModelScope.launch {
            _ejerciciosDisponibles.value = ejercicioDao.obtenerTodos()
        }
    }

    fun cargarRutinasPorUsuario(usuarioId: Int) {
        viewModelScope.launch {
            if (usuarioId != -1) {
                _rutinas.value = rutinaDao.obtenerPorUsuario(usuarioId)
            }
        }
    }

    fun crearRutinaConEjercicios(
        nombre: String,
        usuarioId: Int,
        ejerciciosSeleccionados: List<Ejercicio>,
        onComplete: () -> Unit
    ) {
        viewModelScope.launch {
            // 1. Creamos la rutina (sin descripción)
            val nuevaRutina = Rutina(
                nombre = nombre,
                descripcion = "", // Ya no se usa descripción de texto
                usuarioId = usuarioId
            )
            // Insertamos y obtenemos el ID generado (requiere que RutinaDao.insertar devuelva Long)
            val rutinaId = rutinaDao.insertar(nuevaRutina).toInt()

            // 2. Insertamos cada ejercicio seleccionado en la tabla intermedia RutinaEjercicio
            ejerciciosSeleccionados.forEachIndexed { index, ejercicio ->
                val rutinaEjercicio = RutinaEjercicio(
                    rutinaId = rutinaId,
                    ejercicioId = ejercicio.id,
                    series = 3,          // Valores por defecto o ajustables
                    repeticiones = 10,
                    peso = 0f,
                    descanso = 60,
                    orden = index + 1
                )
                rutinaEjercicioDao.insertar(rutinaEjercicio)
            }

            cargarRutinasPorUsuario(usuarioId)
            onComplete()
        }
    }
}