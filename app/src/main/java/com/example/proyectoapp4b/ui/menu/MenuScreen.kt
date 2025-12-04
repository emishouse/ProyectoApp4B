package com.example.proyectoapp4b.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectoapp4b.ui.components.SigoTopBar

@Composable
fun MenuScreen(
    username: String,
    onLogout: () -> Unit = {},
    onNavigateModules: (String) -> Unit = {},
    onMenuPrincipal: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // 🔵 TopBar
        SigoTopBar(
            username = username,
            onLogout = onLogout,
            onMenuPrincipal = onMenuPrincipal
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 👋 Bienvenida
        Text(
            text = "Bienvenido: $username",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔷 Tarjeta: Mi Historial Académico
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onNavigateModules("historial") },
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Mi Historial Académico",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Estatus, cursos y evaluaciones.\nConsulta periódicamente tu historial y estate al pendiente de tu estatus académico.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔷 Tarjeta: Mi Perfil (la que faltaba 👇)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onNavigateModules("perfil") },
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Mi Perfil",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Datos personales, de contacto y más.\nValida tu información personal y mantenla siempre actualizada.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    MenuScreen(username = "Chucho")
}

