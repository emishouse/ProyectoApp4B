package com.example.proyectoapp4b.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
        ) {
            SigoTopBar(
                username = username,
                onLogout = onLogout,
                onMenuPrincipal = onMenuPrincipal
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bienvenido: $username",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mi Historial Académico
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateModules("historial") },
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Mi Historial Académico",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Estatus, curso y evaluaciones.\nConsulta periódicamente tu historial y estate al pendiente de tu estatus académico.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mi Perfil
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateModules("perfil") },
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Mi Perfil",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Datos personales, de contacto y más.\nValida tu información personal y manténla siempre actualizada.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    MenuScreen(username = "Chucho")
}


