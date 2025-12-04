// --- CuatrimestreCard.kt ---
package com.example.proyectoapp4b.ui.historial

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectoapp4b.model.Cuatrimestre

@Composable
fun CuatrimestreCard(
    cuatri: Cuatrimestre,
    onAddClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Cuatrimestre ${cuatri.numero} - ${cuatri.periodo}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Estado: ${cuatri.estado}")
            Text(text = "Carrera: ${cuatri.carrera}")
            Text(text = "Grupo: ${cuatri.grupo}")
            Text(text = "Tutor: ${cuatri.tutor}")
            Text(text = "Progreso: ${cuatri.progreso}%")
            Text(text = "Desempeño: ${cuatri.desempeno}")

            Spacer(modifier = Modifier.height(12.dp))

            // Botón de agregar dentro del card
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onAddClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar",
                        tint = Color(0xFF009688)
                    )
                }
            }
        }
    }
}