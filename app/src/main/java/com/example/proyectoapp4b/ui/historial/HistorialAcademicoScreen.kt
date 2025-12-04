// --- HistorialAcademicoScreen.kt ---
package com.example.proyectoapp4b.ui.historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectoapp4b.ui.components.SigoTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialAcademicoScreen(
    navController: NavController,
    username: String,
    onLogout: () -> Unit = {},
    onMenuPrincipal: () -> Unit = {},
    viewModel: HistorialAcademicoViewModel = viewModel()
) {
    val historial by viewModel.historial.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top bar personalizada
            SigoTopBar(
                username = username,
                onLogout = onLogout,
                onMenuPrincipal = onMenuPrincipal
            )

            // Flecha de regreso
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

            Text(
                text = "Historial Académico",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔄 Mostrar lista de cuatrimestres con botón en cada card
            historial.forEach { cuatri ->
                CuatrimestreCard(
                    cuatri = cuatri,
                    onAddClick = {
                        // Acción futura para este cuatrimestre
                    }
                )
            }
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
