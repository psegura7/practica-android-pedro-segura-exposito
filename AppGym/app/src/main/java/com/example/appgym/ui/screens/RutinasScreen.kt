package com.example.appgym.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appgym.data.entities.Ejercicio
import com.example.appgym.viewmodel.RutinaViewModel
import com.example.appgym.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinasScreen(
    navController: NavController,
    rutinaViewModel: RutinaViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    val usuarioLogueado by usuarioViewModel.usuarioLogueado.collectAsState()
    val listaRutinas by rutinaViewModel.rutinas.collectAsState()
    val ejerciciosDisponibles by rutinaViewModel.ejerciciosDisponibles.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }
    var nombreRutina by remember { mutableStateOf("") }

    // Conjunto para llevar el control de los ejercicios seleccionados
    var ejerciciosSeleccionados = remember { mutableStateListOf<Ejercicio>() }

    LaunchedEffect(usuarioLogueado) {
        usuarioLogueado?.let {
            rutinaViewModel.cargarRutinasPorUsuario(it.id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Rutinas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                ejerciciosSeleccionados.clear()
                nombreRutina = ""
                mostrarDialogo = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir Rutina")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (listaRutinas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tienes rutinas creadas todavía.\nPulsa '+' para crear una.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listaRutinas) { rutina ->
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
                                    text = rutina.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }

            // Diálogo de creación con selección de ejercicios
            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogo = false },
                    title = { Text("Crear Nueva Rutina") },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = nombreRutina,
                                onValueChange = { nombreRutina = it },
                                label = { Text("Nombre de la rutina (ej. Día de Pecho)") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Selecciona los ejercicios:",
                                style = MaterialTheme.typography.labelLarge
                            )

                            // Lista de ejercicios de la base de datos con Checkbox
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                items(ejerciciosDisponibles) { ejercicio ->
                                    val isSelected = ejerciciosSeleccionados.contains(ejercicio)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = isSelected,
                                            onCheckedChange = { checked ->
                                                if (checked) {
                                                    ejerciciosSeleccionados.add(ejercicio)
                                                } else {
                                                    ejerciciosSeleccionados.remove(ejercicio)
                                                }
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = ejercicio.nombre,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = ejercicio.grupoMuscular,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                usuarioLogueado?.let { usuario ->
                                    if (nombreRutina.isNotBlank() && ejerciciosSeleccionados.isNotEmpty()) {
                                        rutinaViewModel.crearRutinaConEjercicios(
                                            nombre = nombreRutina,
                                            usuarioId = usuario.id,
                                            ejerciciosSeleccionados = ejerciciosSeleccionados.toList()
                                        ) {
                                            mostrarDialogo = false
                                        }
                                    }
                                }
                            }
                        ) {
                            Text("Guardar Rutina")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarDialogo = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}