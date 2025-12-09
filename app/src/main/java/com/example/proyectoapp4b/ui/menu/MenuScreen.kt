package com.example.proyectoapp4b.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectoapp4b.ui.components.SigoTopBar

@Composable
fun MenuScreen(
    username: String,
    onLogout: () -> Unit,
    onNavigateModules: (String) -> Unit,
    onMenuPrincipal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        SigoTopBar(
            username = username,
            onLogout = {
                onLogout()
            },
            onMenuPrincipal = onMenuPrincipal
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Bienvenido: $username",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onNavigateModules("historial") },
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Mi Historial Académico", style = MaterialTheme.typography.titleMedium)
                Text("Revisa tu información académica completa.")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onNavigateModules("perfil") },
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Mi Perfil", style = MaterialTheme.typography.titleMedium)
                Text("Consulta y edita tu información personal.")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}




