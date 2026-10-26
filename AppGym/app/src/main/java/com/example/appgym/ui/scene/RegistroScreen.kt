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
import com.example.appgym.data.entities.Usuario
import com.example.appgym.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun RegistroScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

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
            "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(

            modifier = Modifier.fillMaxWidth(),

            onClick = {

                if (
                    nombre.isBlank() ||
                    email.isBlank() ||
                    contraseña.isBlank() ||
                    edad.isBlank()
                ) {

                    Toast.makeText(
                        context,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                scope.launch {

                    usuarioViewModel.registrarUsuario(

                        Usuario(

                            nombreUsuario = nombre,
                            email = email,
                            contraseña = contraseña,
                            fotoPerfil = null,
                            fechaRegistro = LocalDate.now().toString(),
                            edad = edad.toInt()

                        )

                    )

                    Toast.makeText(
                        context,
                        "Usuario registrado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.popBackStack()

                }

            }

        ) {

            Text("Registrarse")

        }

    }

}