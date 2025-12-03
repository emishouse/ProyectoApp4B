package com.example.proyectoapp4b.ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectoapp4b.ui.components.SigoTopBar

@Composable
fun MenuScreen(
    username: String,                // ← AHORA RECIBE EL NOMBRE DEL USUARIO
    onLogout: () -> Unit = {},
    onNavigateModules: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // 🔝 TopBar con nombre dinámico
        SigoTopBar(
            username = username,
            onLogout = onLogout,
            onMenuClick = { /* aquí luego agregamos el drawer lateral */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Menú Principal",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Kardex
        Button(
            onClick = { onNavigateModules("kardex") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Kárdex")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Horario
        Button(
            onClick = { onNavigateModules("horario") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Horario")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    MenuScreen(username = "Eduardo")  // ← PREVIEW FUNCIONA CORRECTAMENTE
}


