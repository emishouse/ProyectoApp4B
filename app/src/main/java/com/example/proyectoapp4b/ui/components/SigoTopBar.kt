package com.example.proyectoapp4b.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectoapp4b.R

@Composable
fun SigoTopBar(
    username: String,
    onLogout: () -> Unit,
    onMenuPrincipal: () -> Unit // acción para regresar al menú principal
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF009688))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Grupo izquierdo: logo + menú
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.sigo),
                contentDescription = "Logo",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Botón menú hamburguesa
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Menú desplegable con solo una opción
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Menú Principal") },
                        onClick = {
                            expanded = false
                            onMenuPrincipal() // acción para regresar
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Grupo derecho: usuario + logout
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Usuario",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = username,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(onClick = onLogout) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SigoTopBarPreview() {
    SigoTopBar(
        username = "Usuario Demo",
        onLogout = {},
        onMenuPrincipal = {}
    )
}


