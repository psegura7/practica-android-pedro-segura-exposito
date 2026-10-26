package com.example.appgym.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appgym.data.dao.*
import com.example.appgym.data.entities.*

@Database(
    entities = [
        Usuario::class,
        Rutina::class,
        Ejercicio::class,
        RutinaEjercicio::class,
        Entrenamiento::class,
        EntrenamientoEjercicio::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    abstract fun rutinaDao(): RutinaDao

    abstract fun ejercicioDao(): EjercicioDao

    abstract fun rutinaEjercicioDao(): RutinaEjercicioDao

    abstract fun entrenamientoDao(): EntrenamientoDao

    abstract fun entrenamientoEjercicioDao(): EntrenamientoEjercicioDao
}