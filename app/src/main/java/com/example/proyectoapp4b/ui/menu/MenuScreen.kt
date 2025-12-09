package com.example.proyectoapp4b.ui.menu

import androidx.compose.foundation.clickable    // Permite que un Card responda a toques
import androidx.compose.foundation.layout.*     // Column, Row, Spacer, Modifier
import androidx.compose.foundation.rememberScrollState // Control del scroll vertical
import androidx.compose.foundation.verticalScroll       // Agrega scroll a la columna
import androidx.compose.material3.*            // Componentes Material 3 (Card, Text, etc.)
import androidx.compose.runtime.Composable     // Anotación para funciones Compose
import androidx.compose.ui.Modifier            // Modificador para tamaño, padding, etc.
import androidx.compose.ui.text.font.FontWeight // Negritas para textos
import androidx.compose.ui.unit.dp             // Medidas en dp
import com.example.proyectoapp4b.ui.components.SigoTopBar // Barra superior personalizada

@Composable
fun MenuScreen(
    username: String,                       // Nombre del usuario que inició sesión (texto mostrado)
    onLogout: () -> Unit,                   // Callback para cerrar sesión (NavController lo usa)
    onNavigateModules: (String) -> Unit,    // Callback para navegar hacia módulos: historial, perfil, etc.
    onMenuPrincipal: () -> Unit             // Callback que se ejecuta desde la TopBar
) {

    // --------------------------------------------------------------
    // CONTENEDOR PRINCIPAL: una Column scrollable
    // - fillMaxSize() → ocupa toda la pantalla
    // - verticalScroll → permite que el menú sea scrolleable en pantallas pequeñas
    // --------------------------------------------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // --------------------------------------------------------------
        // BARRA SUPERIOR (Componente compartido en todo el proyecto)
        // - Muestra: username, botón logout, botón menú principal
        // - onLogout() se manda directamente desde aquí sin navegación interna
        // --------------------------------------------------------------
        SigoTopBar(
            username = username,
            onLogout = {
                // Se ejecuta el callback enviado desde NavHost
                onLogout()
            },
            onMenuPrincipal = onMenuPrincipal // Redirección a menú principal del sistema SIGO
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --------------------------------------------------------------
        // TEXTO PRINCIPAL DE BIENVENIDA
        // - Se personaliza el saludo con el username
        // --------------------------------------------------------------
        Text(
            text = "Bienvenido: $username",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ==============================================================
        // CARD #1 — Historial Académico
        // - clickable → Llama onNavigateModules("historial")
        // - Es la entrada hacia la pantalla HistorialAcademicoScreen
        // ==============================================================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {
                    // Navegación hacia módulo "historial"
                    onNavigateModules("historial")
                },
            elevation = CardDefaults.cardElevation(6.dp) // Sombra del card
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Título del módulo
                Text(
                    "Mi Historial Académico",
                    style = MaterialTheme.typography.titleMedium
                )

                // Descripción breve
                Text("Revisa tu información académica completa.")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ==============================================================
        // CARD #2 — Perfil del usuario
        // - clickable → Navega a módulo "perfil"
        // - Va hacia la pantalla PerfilScreen (en tu nav)
        // ==============================================================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {
                    // Navegación hacia módulo "perfil"
                    onNavigateModules("perfil")
                },
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Título del módulo "Perfil"
                Text(
                    "Mi Perfil",
                    style = MaterialTheme.typography.titleMedium
                )

                // Breve explicación del módulo
                Text("Consulta y edita tu información personal.")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
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




