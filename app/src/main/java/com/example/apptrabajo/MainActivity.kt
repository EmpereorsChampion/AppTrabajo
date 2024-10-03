package com.example.apptrabajo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apptrabajo.ui.theme.AppTrabajoTheme
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import java.util.regex.Pattern
import androidx.compose.ui.tooling.preview.Preview

data class User(val username: String, val password: String)

class MainActivity : ComponentActivity() {
    private val registeredUsers = mutableListOf<User>() // Lista de usuarios registrados

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTrabajoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController, registeredUsers) }
                    composable("register") { RegisterScreen(navController, registeredUsers) } // Pasar la lista a RegisterScreen
                    composable("home") { HomeScreen() } // Nueva pantalla de inicio
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController, registeredUsers: List<User>) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.value.isEmpty() || password.value.isEmpty()) {
                    errorMessage = "Todos los campos son obligatorios"
                } else {
                    // Lógica de autenticación
                    if (isValidCredentials(username.value, password.value, registeredUsers)) {
                        navController.navigate("home") // Navegar a la pantalla de inicio
                    } else {
                        errorMessage = "Credenciales inválidas"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("register") }) { // Navegación a registro
            Text("¿No tienes una cuenta? Regístrate")
        }
    }
}

@Composable
fun RegisterScreen(navController: NavController, registeredUsers: MutableList<User>) {
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()) {
                    errorMessage = "Todos los campos son obligatorios"
                } else if (!isValidEmail(email.value)) {
                    errorMessage = "Formato de correo inválido"
                } else if (password.value.length < 6) {
                    errorMessage = "La contraseña debe tener al menos 6 caracteres"
                } else {
                    // Agregar el nuevo usuario a la lista
                    registeredUsers.add(User(username.value, password.value))
                    navController.navigate("login") // Regresar a la pantalla de inicio de sesión
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("login") }) { // Navegación a inicio de sesión
            Text("¿Ya tienes una cuenta? Inicia sesión")
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    )
    return emailPattern.matcher(email).matches()
}

fun isValidCredentials(username: String, password: String, registeredUsers: List<User>): Boolean {
    // Verifica si las credenciales coinciden con algún usuario registrado
    return registeredUsers.any { it.username == username && it.password == password }
}

@Composable
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Home") // Pantalla de inicio
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    AppTrabajoTheme {
        // Muestra la pantalla de inicio de sesión en la vista previa
        LoginScreen(navController = rememberNavController(), registeredUsers = listOf()) // Proporcionar lista vacía para la vista previa
    }
}
