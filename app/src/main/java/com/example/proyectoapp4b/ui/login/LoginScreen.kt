package com.example.proyectoapp4b.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectoapp4b.R
import com.example.proyectoapp4b.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (String) -> Unit = {}     // ← CAMBIADO: ahora recibe username
) {
    val user by viewModel.user.collectAsState()
    val pass by viewModel.pass.collectAsState()
    val state by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    // 🔵 Mostrar/ocultar contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    // Si el login fue exitoso → enviar username
    if (state is LoginViewModel.LoginState.Success) {
        onLoginSuccess(user)   // ← ENVÍA EL USUARIO REAL
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ❌ Botón cerrar app
        IconButton(
            onClick = { (context as? Activity)?.finish() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = Color.Black,
                modifier = Modifier.size(36.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logos
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sigo),
                    contentDescription = "Logo SIGO",
                    modifier = Modifier
                        .height(60.dp)
                        .padding(end = 12.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.utm),
                    contentDescription = "Logo UTM",
                    modifier = Modifier.height(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Usuario
            OutlinedTextField(
                value = user,
                onValueChange = { viewModel.onUserChange(it) },
                label = { Text("Matrícula") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Usuario")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = pass,
                onValueChange = { viewModel.onPassChange(it) },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Contraseña")
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.ic_visibility
                                else R.drawable.ic_visibility_off
                            ),
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón iniciar sesión
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF009688)
                )
            ) {
                Text(
                    "Iniciar Sesión",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿Olvidaste la contraseña?",
                color = Color.Gray,
                modifier = Modifier.clickable { },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}







