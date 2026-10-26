package com.example.appgym.ui.scene

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PrincipalScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "🏋 Bienvenido a AppGym",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Button(
                    onClick = {
                        // Pantalla Entrenamientos
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(Icons.Default.FitnessCenter, null)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Entrenamientos")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        // Pantalla Rutinas
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(Icons.Default.ViewList, null)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Mis rutinas")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        // Pantalla Perfil
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(Icons.Default.Person, null)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Mi perfil")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {

                        navController.navigate("login") {
                            popUpTo("principal") {
                                inclusive = true
                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(Icons.Default.ExitToApp, null)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Cerrar sesión")

                }

            }

        }

    }

}