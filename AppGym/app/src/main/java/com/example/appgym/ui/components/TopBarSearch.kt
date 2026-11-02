package com.example.appgym.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSearch(
    textoBusqueda: String,
    onTextoChange: (String) -> Unit
) {
    TopAppBar(
        title = {
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = onTextoChange,
                placeholder = { Text("Buscar usuario...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}