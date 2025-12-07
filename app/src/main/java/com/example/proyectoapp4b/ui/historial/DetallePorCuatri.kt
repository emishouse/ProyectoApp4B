package com.example.proyectoapp4b.ui.historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectoapp4b.ui.components.SigoTopBar

data class MateriaDetalle(
    val nombre: String,
    val docente: String,
    val progreso: String,
    val evaluacion: String,
    val desempeno: String,
    val unidades: List<String>
)

@Composable
fun DetalleCuatrimestreScreen(
    navController: NavController,
    numero: Int,
    username: String = "Usuario",
    onLogout: () -> Unit = { navController.navigate("login") },
    onMenuPrincipal: () -> Unit = { navController.navigate("menu/$username") }
) {
    val materias = listOf(
        MateriaDetalle(
            nombre = "Inglés IV",
            docente = "Lic. Marco Antonio Suarez Villanueva",
            progreso = "50%",
            evaluacion = "--",
            desempeno = "Por capturar",
            unidades = listOf(
                "El pasado (Estratégico)",
                "Pasado Simple vs. Pasado Continuo (Por capturar)"
            )
        ),
        MateriaDetalle(
            nombre = "Ética Profesional",
            docente = "Lic. Laura Perez Pazos",
            progreso = "66%",
            evaluacion = "--",
            desempeno = "Por capturar",
            unidades = listOf("Introducción a la Ética (Estratégico)")
        )
    )

    val titulo = "Detalle del ${numero}º cuatrimestre"

    Scaffold(
        topBar = {
            SigoTopBar(
                username = username,
                onLogout = onLogout,
                onMenuPrincipal = onMenuPrincipal
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Título con flecha de regreso
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
                    text = titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(materias) { materia ->
                    MateriaCard(materia)
                }
            }
        }
    }
}

@Composable
fun MateriaCard(materia: MateriaDetalle) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(materia.nombre, style = MaterialTheme.typography.titleMedium)
            Text("Docente: ${materia.docente}", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))
            Text("Progreso: ${materia.progreso}")
            Text("Evaluación: ${materia.evaluacion}")
            Text("Desempeño: ${materia.desempeno}")
            Spacer(Modifier.height(8.dp))
            Text("Unidades Temáticas:", style = MaterialTheme.typography.labelMedium)
            materia.unidades.forEach { unidad ->
                Text("- $unidad", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}