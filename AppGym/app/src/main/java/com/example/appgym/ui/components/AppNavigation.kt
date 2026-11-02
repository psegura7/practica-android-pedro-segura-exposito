package com.example.appgym.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // <-- Asegúrate de importar esto
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appgym.ui.screens.*
import com.example.appgym.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    modifier: Modifier = Modifier // 1. Añadimos el parámetro aquí
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier // 2. Se lo aplicamos al NavHost para que respete el padding del Scaffold
    ) {
        // LOGIN
        composable("login") {
            LoginScreen(navController = navController, usuarioViewModel = usuarioViewModel)
        }

        // REGISTRO
        composable("registro") {
            RegistroScreen(navController = navController, usuarioViewModel = usuarioViewModel)
        }

        // INICIO / PRINCIPAL
        composable("principal") {
            PrincipalScreen(navController = navController, usuarioViewModel = usuarioViewModel)
        }

        // RUTINAS
        composable("rutinas") {
            // RutinasScreen(navController)
        }

        // PERFIL PROPIO (Desde la barra inferior)
        composable("perfil") {
            PerfilScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel,
                usuarioIdPerfil = -1
            )
        }

        // PERFIL DE OTRO USUARIO (Clicando en la lista)
        composable("perfil/{usuarioId}") { backStackEntry ->
            val usuarioIdStr = backStackEntry.arguments?.getString("usuarioId")
            val usuarioId = usuarioIdStr?.toIntOrNull() ?: -1

            PerfilScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel,
                usuarioIdPerfil = usuarioId
            )
        }
    }
}