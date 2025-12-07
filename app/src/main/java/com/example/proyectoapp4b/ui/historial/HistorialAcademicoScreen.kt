package com.example.proyectoapp4b.ui.historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            // ✅ Top bar completa
            SigoTopBar(
                username = username,
                onLogout = onLogout,
                onMenuPrincipal = onMenuPrincipal
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Título con flecha de regreso al lado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Historial Académico",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔄 Lista de cuatrimestres
            historial.forEach { cuatri ->
                CuatrimestreCard(
                    cuatri = cuatri,
                    onAddClick = {
                        navController.navigate("detalleCuatrimestre/${cuatri.numero}")
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
        username = "Chucho"
    )
}