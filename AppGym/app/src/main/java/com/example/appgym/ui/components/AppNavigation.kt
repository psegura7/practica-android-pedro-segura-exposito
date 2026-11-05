package com.example.appgym.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appgym.ui.screens.*
import com.example.appgym.viewmodel.RutinaViewModel
import com.example.appgym.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    modifier: Modifier = Modifier // <-- Recibe el modificador del Scaffold
) {
    val rutinaViewModel: RutinaViewModel = viewModel()

    Box(modifier = modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "login") {

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
                PrincipalScreen(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel
                )
            }

            composable("rutinas") {
                RutinasScreen(
                    navController = navController,
                    rutinaViewModel = rutinaViewModel,
                    usuarioViewModel = usuarioViewModel
                )
            }

            composable("perfil") {
                PerfilScreen(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    usuarioIdPerfil = -1
                )
            }

            composable("perfil/{usuarioId}") { backStackEntry ->
                val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toIntOrNull() ?: -1
                PerfilScreen(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    usuarioIdPerfil = usuarioId
                )
            }
        }
    }
}