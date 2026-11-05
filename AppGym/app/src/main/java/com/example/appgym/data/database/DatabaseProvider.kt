package com.example.appgym.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appgym.data.entities.Ejercicio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "gym_database"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Insertamos ejercicios iniciales en un hilo secundario al crear la BD
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val ejercicioDao = database.ejercicioDao()

                                // Añade aquí tus ejercicios por defecto
                                ejercicioDao.insertar(Ejercicio(nombre = "Press de Banca", descripcion = "Ejercicio para pecho con barra", grupoMuscular = "Pecho", imagenUrl = null, videoUrl = null))

                                ejercicioDao.insertar(Ejercicio(nombre = "Sentadillas", descripcion = "Ejercicio para piernas y glúteos", grupoMuscular = "Piernas", imagenUrl = null, videoUrl = null))

                                ejercicioDao.insertar(Ejercicio(nombre = "Dominadas", descripcion = "Ejercicio para espalda", grupoMuscular = "Espalda", imagenUrl = null, videoUrl = null))

                                ejercicioDao.insertar(Ejercicio(nombre = "Curl de Bíceps", descripcion = "Ejercicio para brazos", grupoMuscular = "Bíceps", imagenUrl = null, videoUrl = null))
                            }
                        }
                    }
                })
                .build()

            INSTANCE = instance
            instance
        }
    }
}