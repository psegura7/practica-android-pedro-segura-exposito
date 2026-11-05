package com.example.appgym.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appgym.ui.components.TopBarSearch
import com.example.appgym.viewmodel.UsuarioViewModel

@Composable
fun PrincipalScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val listaUsuarios by usuarioViewModel.usuarios.collectAsState()
    var textoBusqueda by remember { mutableStateOf("") }

    val usuariosFiltrados = listaUsuarios.filter { usuario ->
        usuario.nombreUsuario.contains(textoBusqueda, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopBarSearch(
                textoBusqueda = textoBusqueda,
                onTextoChange = { textoBusqueda = it }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(usuariosFiltrados) { usuario ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Navega al perfil del usuario usando la ruta configurada en el NavHost
                                navController.navigate("perfil/${usuario.id}")
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = usuario.nombreUsuario,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = usuario.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}