package com.example.proyectoapp4b.ui.menu

import androidx.compose.foundation.clickable    // Permite que un Card responda a toques
import androidx.compose.foundation.layout.*     // Column, Row, Spacer, Modifier
import androidx.compose.foundation.rememberScrollState // Control del scroll vertical
import androidx.compose.foundation.verticalScroll       // Agrega scroll a la columna
import androidx.compose.material3.*            // Componentes Material 3 (Card, Text, etc.)
import androidx.compose.runtime.Composable     // Anotación para funciones Compose
import androidx.compose.ui.Modifier            // Modificador para tamaño, padding, etc.
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight // Negritas para textos
import androidx.compose.ui.unit.dp             // Medidas en dp
import com.example.proyectoapp4b.ui.components.SigoTopBar // Barra superior personalizada

@Composable
fun MenuScreen(
    username: String,
    personFullName: String,
    email: String,
    profileName: String,
    accessModule: String,
    personId: Int,
    id: Int,
    register: String,
    roles: List<String>,
    active: Boolean,
    termsConditions: Boolean,
    onLogout: () -> Unit,
    onNavigateModules: (String) -> Unit,
    onMenuPrincipal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // TopBar
        SigoTopBar(
            username = username,
            onLogout = { onLogout() },
            onMenuPrincipal = onMenuPrincipal
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Bienvenida
        Text(
            text = "Bienvenido: $personFullName",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))


        // Botón Historial Académico
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

        // Botón Perfil
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
/*
        Spacer(modifier = Modifier.height(12.dp))
        // Datos básicos (Nombre, Usuario, Email)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(0.dp), // 🔹 sin sombra
            colors = CardDefaults.cardColors(containerColor = Color.Transparent) // 🔹 sin fondo
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nombre: $personFullName", style = MaterialTheme.typography.bodyLarge)
                Text("Usuario: $username", style = MaterialTheme.typography.bodyLarge)
                Text("Email: $email", style = MaterialTheme.typography.bodyLarge)
                Text("Perfil: $profileName", style = MaterialTheme.typography.bodyLarge)
                Text("Módulo: $accessModule", style = MaterialTheme.typography.bodyLarge)
                Text("Roles: ${roles.joinToString(", ")}", style = MaterialTheme.typography.bodyLarge)
                Text("ID Persona: $personId", style = MaterialTheme.typography.bodyLarge)
                Text("ID Registro: $id", style = MaterialTheme.typography.bodyLarge)
                Text("Usuario activo: ${if (active) "Sí" else "No"}", style = MaterialTheme.typography.bodyLarge)
                Text("Fecha de registro: $register", style = MaterialTheme.typography.bodyLarge)
                Text("Términos y condiciones: ${if (termsConditions) "Aceptados" else "No aceptados"}", style = MaterialTheme.typography.bodyLarge)
            }
        }

 */
    }
}


/* ===============================================================
   RELACIÓN DE MenuScreen.kt CON EL RESTO DE LA APLICACIÓN
   ===============================================================

1) QUÉ NECESITA PARA FUNCIONAR
   - Recibe "username" desde LoginScreen → NavHost
   - Recibe callbacks:
        • onLogout() → Limpia sesión / navega a login
        • onNavigateModules(route) → Router para navegar a:
              - "historial"
              - "perfil"
        • onMenuPrincipal() → Opción de menú desde el SigoTopBar

   - Usa componente:
        • SigoTopBar (barra superior reutilizada)


2) FLUJO COMPLETO EN LA NAVEGACIÓN
   - Desde NavHost:
        composable("menu/{username}") { backStackEntry ->
            val user = backStackEntry.arguments?.getString("username") ?: ""
            MenuScreen(
                username = user,
                onLogout = { navController.navigate("login") { popUpTo(0) } },
                onNavigateModules = { route ->
                    navController.navigate("$route/$user")
                },
                onMenuPrincipal = {
                    navController.navigate("menu/$user")
                }
            )
        }

3) RELACIÓN CON OTRAS PANTALLAS
   - "historial" → HistorialAcademicoScreen
   - "perfil"    → PerfilScreen (cuando la agregues)

   Cada Card llama:
       onNavigateModules("historial")
       onNavigateModules("perfil")


4) RESUMEN DEL PROPÓSITO DE MenuScreen
   - Es el **menú principal** que aparece tras iniciar sesión.
   - Conecta al usuario con los módulos principales del sistema SIGO.
   - Solo muestra acciones; no contiene lógica de negocio.
   - Funciona como router visual simple.


5) PUNTOS A MEJORAR (opcional)
   - Agregar íconos en los cards
   - Añadir animaciones (animateContentSize)
   - Convertir los módulos en una lista cargada dinámicamente
   - Mostrar rol del usuario (alumno / docente / admin)
*/




