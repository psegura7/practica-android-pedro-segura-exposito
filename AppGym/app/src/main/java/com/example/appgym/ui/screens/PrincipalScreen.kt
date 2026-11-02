package com.example.appgym.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appgym.ui.components.TopBarSearch // <-- Importamos la barra desde components
import com.example.appgym.viewmodel.UsuarioViewModel

@Composable
fun PrincipalScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    // Obtenemos la lista de usuarios del ViewModel
    val listaUsuarios by usuarioViewModel.usuarios.collectAsState(initial = emptyList())

    // Estado para guardar lo que se escribe en el buscador
    var textoBusqueda by remember { mutableStateOf("") }

    // Filtramos la lista de usuarios según el texto introducido
    val usuariosFiltrados = listaUsuarios.filter { usuario ->
        usuario.nombreUsuario.contains(textoBusqueda, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            // Usamos nuestro componente reutilizable
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

            // Lista para mostrar los usuarios filtrados
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(usuariosFiltrados) { usuario ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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