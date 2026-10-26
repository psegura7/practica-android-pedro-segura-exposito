package com.example.appgym.ui.scene

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appgym.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {

    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                scope.launch {

                    val usuario =
                        usuarioViewModel.iniciarSesion(
                            email,
                            contraseña
                        )

                    if (usuario != null) {

                        navController.navigate("principal") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }

                    } else {

                        Toast.makeText(
                            context,
                            "Email o contraseña incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Entrar")

        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(

            onClick = {

                navController.navigate("registro")

            }

        ) {

            Text("¿No tienes cuenta? Regístrate")

        }

    }

}