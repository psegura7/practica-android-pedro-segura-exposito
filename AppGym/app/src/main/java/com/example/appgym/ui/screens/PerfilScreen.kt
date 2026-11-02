package com.example.appgym.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appgym.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    usuarioIdPerfil: Int = -1 // -1 indica que es tu propio perfil
) {
    val usuarioLogueado by usuarioViewModel.usuarioLogueado.collectAsState()

    // Determinamos si es nuestro propio perfil
    val esMiPerfil = usuarioIdPerfil == -1 || usuarioIdPerfil == usuarioLogueado?.id

    // ESTADO PARA CONTROLAR SI ESTÁ EN MODO EDICIÓN O MODO VISTA NORMAL
    var editando by remember { mutableStateOf(false) }

    // Estados para los campos editables
    var nombreUsuario by remember { mutableStateOf("") }
    var edadTexto by remember { mutableStateOf("") }

    // Estados para mostrar en modo normal
    var nombreMostrado by remember { mutableStateOf("") }
    var emailMostrado by remember { mutableStateOf("") }
    var edadMostrada by remember { mutableStateOf("") }

    // Cargamos los datos correspondientes
    LaunchedEffect(usuarioIdPerfil, usuarioLogueado) {
        if (esMiPerfil) {
            usuarioLogueado?.let {
                nombreUsuario = it.nombreUsuario
                edadTexto = it.edad.toString()
                nombreMostrado = it.nombreUsuario
                emailMostrado = it.email
                edadMostrada = it.edad.toString()
            }
        } else {
            val usuarioAjeno = usuarioViewModel.obtenerUsuarioPorId(usuarioIdPerfil)
            usuarioAjeno?.let {
                nombreMostrado = it.nombreUsuario
                emailMostrado = it.email
                edadMostrada = it.edad.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (esMiPerfil) "Mi Perfil" else "Perfil de Usuario") },
                actions = {
                    // Si es tu propio perfil, mostramos un botón de editar en la barra superior
                    if (esMiPerfil) {
                        IconButton(onClick = { editando = !editando }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar perfil"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 1. FOTO DE PERFIL
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Foto",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. DEPENDIENDO DE SI ESTÁ EDITANDO O NO
            if (esMiPerfil && editando) {
                // --- MODO EDICIÓN ---
                OutlinedTextField(
                    value = nombreUsuario,
                    onValueChange = { nombreUsuario = it },
                    label = { Text("Nombre de usuario") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = edadTexto,
                    onValueChange = { edadTexto = it },
                    label = { Text("Edad") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        usuarioLogueado?.let { actual ->
                            val usuarioModificado = actual.copy(
                                nombreUsuario = nombreUsuario,
                                edad = edadTexto.toIntOrNull() ?: actual.edad
                            )
                            usuarioViewModel.actualizarPerfil(usuarioModificado)
                            // Salimos del modo edición al guardar
                            editando = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Cambios")
                }
            } else {
                // --- MODO VISTA NORMAL (Lectura) ---
                Text(
                    text = if (esMiPerfil) nombreUsuario.ifEmpty { nombreMostrado } else nombreMostrado,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (!esMiPerfil) {
                    Text(
                        text = emailMostrado,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "Edad: ${if (esMiPerfil) edadTexto.ifEmpty { edadMostrada } else edadMostrada} años",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (esMiPerfil) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Pulsa el icono de lápiz arriba a la derecha para editar tu perfil.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}