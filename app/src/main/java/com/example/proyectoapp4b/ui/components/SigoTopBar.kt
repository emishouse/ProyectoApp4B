package com.example.proyectoapp4b.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectoapp4b.R

/**
 * SigoTopBar
 * -----------------------------------------------------------------------------------------------
 * Barra superior utilizada en varias pantallas de la app.
 *
 * Muestra:
 *  - Logo de la aplicación
 *  - Menú desplegable con acceso al menú principal
 *  - Nombre de usuario
 *  - Botón de cerrar sesión
 *
 * @param username Nombre o matrícula del usuario que se muestra en la barra.
 * @param onLogout Acción que se ejecuta al presionar el botón de cierre de sesión.
 * @param onMenuPrincipal Acción para regresar al menú principal.
 */
@Composable
fun SigoTopBar(
    username: String,
    onLogout: () -> Unit,
    onMenuPrincipal: () -> Unit = {}
) {
    // Estado para mostrar/ocultar el menú desplegable
    var expanded by remember { mutableStateOf(false) }

    /* --------------------------------------------------------------------------------------------
       Contenedor principal de la barra superior
       - Ocupa todo el ancho
       - Fondo color verde (#009688)
       - Alinea los elementos de izquierda a derecha
       -------------------------------------------------------------------------------------------- */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF009688))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        /* ----------------------------------------------------------------------------------------
           SECCIÓN IZQUIERDA: LOGO + MENÚ
           ---------------------------------------------------------------------------------------- */
        Row(verticalAlignment = Alignment.CenterVertically) {

            // Logo de SIGO
            Image(
                painter = painterResource(id = R.drawable.sigocel),
                contentDescription = "Logo",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Contiene el botón y el dropdown del menú
            Box {
                // Botón que abre el menú desplegable
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Menú desplegable
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Menú Principal") },
                        onClick = {
                            expanded = false
                            onMenuPrincipal() // Ejecuta callback recibido de la pantalla
                        }
                    )
                }
            }
        }

        // Empuja la sección derecha hacia el extremo final
        Spacer(modifier = Modifier.weight(1f))


        /* ----------------------------------------------------------------------------------------
           SECCIÓN DERECHA: USUARIO + BOTÓN LOGOUT
           ---------------------------------------------------------------------------------------- */
        Row(verticalAlignment = Alignment.CenterVertically) {

            // Ícono de usuario
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Usuario",
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(6.dp))

            // Mostrar el nombre o matrícula
            Text(
                text = username,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Botón de cerrar sesión
            IconButton(onClick = onLogout) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = Color.White
                )
            }
        }
    }
}

/* ================================================================================================
   ¿CÓMO SE RELACIONA ESTA TOP BAR CON EL RESTO DEL PROYECTO?
   ================================================================================================
   ✔ Componente reutilizable en múltiples pantallas:
       - HistorialAcademicoScreen
       - DetalleCuatrimestreScreen
       - PerfilScreen
       - Menú

   ✔ Proporciona consistencia visual:
       - Siempre muestra el logo oficial de SIGO.
       - Contiene un menú unificado para volver al menú principal.
       - Muestra el usuario actual en todas las vistas.

   ✔ Se integra con la navegación:
       - El callback onMenuPrincipal() ejecuta navController.navigate("menu/...").
       - El callback onLogout() limpia el LoginViewModel y regresa al login.

   ✔ Funciona como parte del “layout” global de la app:
       - Actúa como una barra fija superior en todas las pantallas principales.
       - Centraliza las acciones importantes del usuario (volver, cerrar sesión, ver perfil).

   En resumen:
   **SigoTopBar es un componente UI clave reutilizado en toda la app.
   Representa la identidad visual de SIGO, facilita la navegación y concentra las acciones
   principales del usuario mientras mantiene una interfaz limpia y profesional.**
   ================================================================================================ */




