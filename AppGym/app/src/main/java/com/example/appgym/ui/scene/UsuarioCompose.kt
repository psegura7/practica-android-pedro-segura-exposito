package com.example.appgym.ui.scene

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appgym.viewmodel.UsuarioViewModel

@Composable
fun UsuariosScreen(
    viewModel: UsuarioViewModel
) {

    val usuarios by viewModel.usuarios.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Bienvenido a AppGym",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Usuarios registrados",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(usuarios) { usuario ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Usuario: ${usuario.nombreUsuario}"
                        )

                        Text(
                            text = "Email: ${usuario.email}"
                        )

                        Text(
                            text = "Edad: ${usuario.edad}"
                        )

                    }

                }

            }

        }

    }

}