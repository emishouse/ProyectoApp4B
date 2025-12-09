package com.example.proyectoapp4b.ui.historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectoapp4b.viewmodel.HistorialAcademicoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectoapp4b.ui.components.SigoTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialAcademicoScreen(
    navController: NavController,
    username: String,
    onLogout: () -> Unit,
    onMenuPrincipal: () -> Unit,
    viewModel: HistorialAcademicoViewModel = viewModel()
) {
    val historial by viewModel.historial.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SigoTopBar(
            username = username,
            onLogout = {
                onLogout()
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            onMenuPrincipal = onMenuPrincipal
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Historial Académico",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        historial.forEach { cuatri ->
            CuatrimestreCard(
                cuatri = cuatri,
                onAddClick = {
                    navController.navigate(
                        "detalleCuatrimestre/${cuatri.numero}/$username"
                    )
                }
            )
        }
    }
}

