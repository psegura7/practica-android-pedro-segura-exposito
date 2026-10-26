package com.example.appgym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appgym.data.database.DatabaseProvider
import com.example.appgym.data.repository.UsuarioRepository
import com.example.appgym.ui.scene.LoginScreen
import com.example.appgym.ui.scene.RegistroScreen
import com.example.appgym.ui.scene.UsuariosScreen
import com.example.appgym.viewmodel.UsuarioViewModel
import com.example.appgym.ui.scene.PrincipalScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseProvider.getDatabase(this)
        val repository = UsuarioRepository(database.usuarioDao())
        val usuarioViewModel = UsuarioViewModel(repository)

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "login"
            ) {

                composable("login") {

                    LoginScreen(
                        navController = navController,
                        usuarioViewModel = usuarioViewModel
                    )

                }

                composable("registro") {

                    RegistroScreen(
                        navController = navController,
                        usuarioViewModel = usuarioViewModel
                    )

                }

                composable("principal") {
                    PrincipalScreen(navController)
                }

            }

        }
    }
}