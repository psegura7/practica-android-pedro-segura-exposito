package com.example.appgym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appgym.ui.components.BottomBar
import com.example.appgym.ui.components.AppNavigation
import com.example.appgym.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            // Inicializamos el ViewModel de forma limpia y automática con Jetpack Compose
            val usuarioViewModel: UsuarioViewModel = viewModel()

            // Obtenemos la ruta actual para la BottomBar
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val pantallasConBarra = listOf("principal", "rutinas", "perfil")

            Scaffold(
                bottomBar = {
                    if (currentRoute in pantallasConBarra) {
                        BottomBar(navController = navController)
                    }
                }
            ) { innerPadding ->
                // Llamamos a nuestro componente de navegación centralizado
                AppNavigation(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}