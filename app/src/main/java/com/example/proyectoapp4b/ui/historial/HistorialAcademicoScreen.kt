package com.example.proyectoapp4b.ui.historial

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectoapp4b.ui.components.SigoTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialAcademicoScreen(
    navController: NavController,
    username: String,
    onLogout: () -> Unit = {},
    onMenuPrincipal: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar personalizada
        SigoTopBar(
            username = username,
            onLogout = onLogout,
            onMenuPrincipal = onMenuPrincipal
        )

        // Flecha de regreso alineada a la izquierda
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color(0xFF009688)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido principal
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Bienvenido: $username",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Aquí se mostrará el historial académico del usuario.",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistorialAcademicoScreenPreview() {
    val navController = rememberNavController()
    HistorialAcademicoScreen(
        navController = navController,
        username = "Mahal"
    )
}
