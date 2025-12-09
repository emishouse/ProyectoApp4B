package com.example.proyectoapp4b.ui.login

import android.app.Activity                   // Permite cerrar la actividad actual (finish())
import androidx.compose.foundation.Image      // Para mostrar imágenes en Compose
import androidx.compose.foundation.background // Para aplicar colores de fondo a contenedores
import androidx.compose.foundation.clickable  // Para hacer elementos clickeables
import androidx.compose.foundation.layout.*   // Para usar Row, Column, Spacer, Box y padding
import androidx.compose.foundation.shape.RoundedCornerShape // Para bordes redondeados en campos y botones
import androidx.compose.material.icons.Icons  // Acceso al set de íconos por defecto
import androidx.compose.material.icons.filled.Close // Ícono de X para cerrar app
import androidx.compose.material.icons.filled.Lock  // Ícono de candado para contraseña
import androidx.compose.material.icons.filled.Person // Ícono de usuario
import androidx.compose.material.icons.filled.Warning // Ícono de advertencia para errores
import androidx.compose.material3.*            // Componentes Material 3 (Button, TextField, IconButton)
import androidx.compose.runtime.*              // Para estados con remember y mutableStateOf
import androidx.compose.ui.Alignment           // Para alinear elementos en Column, Row, Box
import androidx.compose.ui.Modifier            // Modificador general para COMPOSE
import androidx.compose.ui.graphics.Color      // Manejo de colores personalizados
import androidx.compose.ui.platform.LocalContext // Para obtener el contexto actual (Activity)
import androidx.compose.ui.res.painterResource // Para cargar imágenes desde drawable
import androidx.compose.ui.text.font.FontWeight // Para cambiar peso de texto (negritas)
import androidx.compose.ui.text.input.PasswordVisualTransformation // Ocultar contraseña
import androidx.compose.ui.text.input.VisualTransformation         // Mostrar/Ocultar contraseña
import androidx.compose.ui.text.style.TextAlign // Alinear texto
import androidx.compose.ui.tooling.preview.Preview // Para vista previa en Android Studio
import androidx.compose.ui.unit.dp             // Para tamaños, márgenes y paddings en dp
import androidx.lifecycle.viewmodel.compose.viewModel // Para obtener el ViewModel dentro de Compose
import com.example.proyectoapp4b.R            // Acceso a recursos (logos, íconos)
import com.example.proyectoapp4b.viewmodel.LoginViewModel // ViewModel que maneja login

/* =======================================================================
   DOCUMENTACIÓN DETALLADA (línea a línea / bloque por bloque)
   - NO se modificaron tus comentarios que ya estaban en el código.
   - Los comentarios nuevos explican propósito, control de flujo, y efectos
     colaterales (p. ej. navegación, validaciones, resources).
   ======================================================================= */

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (String) -> Unit = {},
    onForgotPassword: () -> Unit = {}
) {
    // --- Suscripciones al estado expuesto por el ViewModel ---
    // user: flujo/StateFlow con el valor actual del campo matrícula (String)
    val user by viewModel.user.collectAsState()

    // pass: flujo/StateFlow con el valor actual del campo contraseña (String)
    val pass by viewModel.pass.collectAsState()

    // state: flujo/StateFlow que representa el estado del proceso de login
    // (por ejemplo: Idle, Loading, Success, Error). Se usa para mostrar mensajes/UX.
    val state by viewModel.loginState.collectAsState()

    // Contexto actual de la composición — se usa para cerrar la Activity si se quiere.
    val context = LocalContext.current

    // Control local para mostrar/ocultar la contraseña en el campo.
    var passwordVisible by remember { mutableStateOf(false) }

    // ----------------------------
    // NAVEGACIÓN AUTOMÁTICA: si el ViewModel indica Success se dispara el callback
    // Esto evita que el botón haga la navegación y delega la decisión al estado.
    // ----------------------------
    if (state is LoginViewModel.LoginState.Success) {
        // Llamada inmediata para notificar a quien usa este composable que el login fue ok.
        // onLoginSuccess suele llamar a navController.navigate("menu/$username")
        onLoginSuccess(user)
    }

    // -------------------------------------------------------------------------
    // Contenedor principal (Box) que ocupa toda la pantalla y aplica fondo blanco.
    // Es el root visual de la pantalla de login.
    // -------------------------------------------------------------------------
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // -------------------------------------------------------
        // Botón superior derecho para cerrar la app (ícono X)
        // - Usa el context convertido a Activity para llamar finish().
        // - Evita dependencias directas con la Activity en el ViewModel.
        // -------------------------------------------------------
        // Botón cerrar app ❌
        IconButton(
            onClick = { (context as? Activity)?.finish() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,      // Ícono X
                contentDescription = "Cerrar",
                tint = Color.Black,
                modifier = Modifier.size(36.dp)         // Tamaño del ícono
            )
        }

        // ---------------------------------------------------------------------
        // Columna central: logos, campos, validaciones, botón login y link
        // - centrada verticalmente para UX amigable en pantallas grandes/pequeñas.
        // ---------------------------------------------------------------------
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,      // Centra verticalmente
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --------------------------
            // ROW: Logos SIGO y UTM lado a lado
            // - PainterResource carga imágenes desde res/drawable.
            // - Mantén las imágenes optimizadas (webp/png) para reducir APK size.
            // --------------------------
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sigo),
                    contentDescription = "Logo SIGO",
                    modifier = Modifier
                        .height(60.dp)
                        .padding(end = 12.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.utm),
                    contentDescription = "Logo UTM",
                    modifier = Modifier.height(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ---------------------------------------------------------------------
            // BLOQUE DE MENSAJE DE ERROR EN CASO DE CREDENCIALES INVÁLIDAS
            // - Este bloque aparece arriba del campo matrícula para mayor visibilidad.
            // - Se muestra cuando el ViewModel emitió LoginState.Error.
            // ---------------------------------------------------------------------
            //Opción 2: Mensaje arriba del campo "Matrícula" (más visible desde el inicio)
            //Coloca este bloque justo antes del OutlinedTextField de matrícula:

            if (state is LoginViewModel.LoginState.Error) {
                // Caja centrada con icono de advertencia + texto.
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Advertencia",
                            tint = Color(0xFFD32F2F),   // Rojo de error
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Matrícula o contraseña incorrectos",
                            color = Color(0xFFD32F2F),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // --------------------------
            // CAMPO: Matrícula (OutlinedTextField)
            // - value viene del ViewModel y onValueChange delega al ViewModel.
            // - shape RoundedCornerShape para look consistente con diseño Material.
            // - Se recomienda validar la matrícula en el ViewModel también (no solo UI).
            // --------------------------
            // Matrícula
            OutlinedTextField(
                value = user,
                onValueChange = {
                    // Actualiza el estado en el ViewModel (source of truth).
                    viewModel.onUserChange(it)
                },
                label = { Text("Matrícula") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Usuario")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(8.dp)
            )

            // ---------------------------------------------------------------------
            // VALIDACIÓN INMEDIATA EN UI PARA MATRÍCULA (feedback instantáneo)
            // - Si el usuario ha empezado a escribir (isNotEmpty) y longitud < 11,
            //   mostramos advertencia local. Esta validación complementa (no reemplaza)
            //   la validación del ViewModel/Backend.
            // ---------------------------------------------------------------------
            // 🔴 Validación de matrícula
            if (user.isNotEmpty() && user.length < 11) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Advertencia",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Matrícula no válida",
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --------------------------
            // CAMPO: Contraseña (OutlinedTextField con visibilidad toggle)
            // - leadingIcon: candado
            // - trailingIcon: botón que alterna passwordVisible
            // - visualTransformation: PasswordVisualTransformation oculta los caracteres
            // --------------------------
            // Contraseña
            OutlinedTextField(
                value = pass,
                onValueChange = {
                    // Actualiza la contraseña en el ViewModel (no en local solamente).
                    viewModel.onPassChange(it)
                },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Contraseña")
                },
                trailingIcon = {
                    // Botón con ícono personalizado (visibility / visibility_off)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.ic_visibility
                                else R.drawable.ic_visibility_off
                            ),
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --------------------------
            // BOTÓN: Iniciar Sesión
            // - Llama viewModel.login() que hace la lógica (repo/API).
            // - enabled = user.length >= 10: deshabilita el botón si matrícula es corta.
            // - Considerar manejar estado Loading para deshabilitar doble submit.
            // --------------------------
            // Botón iniciar sesión
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF009688)
                ),
                enabled = user.length >= 10 // 🔒 desactiva si matrícula no válida
            ) {
                Text(
                    "Iniciar Sesión",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --------------------------
            // LINK: Olvidé contraseña
            // - clickable { onForgotPassword() } dispara callback para navegar a pantalla recuperar.
            // - No hace validación; solo navega.
            // --------------------------
            // ¿Olvidaste la contraseña?
            Text(
                text = "¿Olvidaste la contraseña?",
                color = Color.Gray,
                modifier = Modifier.clickable {
                    onForgotPassword()
                },
                textAlign = TextAlign.Center
            )

            /*
                        // 🔴 Mensaje de error debajo
                        if (state is LoginViewModel.LoginState.Error) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "Advertencia",
                                        tint = Color(0xFFD32F2F),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Matrícula o contraseña incorrectos",
                                        color = Color(0xFFD32F2F),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

             */

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}

/* =======================================================================
   BLOQUE FINAL: EJEMPLOS CONCRETOS DE CÓMO SE RELACIONA ESTE ARCHIVO
   =======================================================================
   (Copia-pega este bloque en un README o en el mismo archivo para referencia)
*/

/*
1) DEPENDENCIAS DIRECTAS (qué necesita para funcionar)
   - LoginViewModel: expone:
       • user: StateFlow<String>
       • pass: StateFlow<String>
       • loginState: StateFlow<LoginState> (Idle / Loading / Success / Error)
       • onUserChange(String), onPassChange(String), login()
   - Recursos gráficos en /res/drawable:
       • R.drawable.sigo
       • R.drawable.utm
       • R.drawable.ic_visibility
       • R.drawable.ic_visibility_off
   - NavHost / AppNavHost para recibir onLoginSuccess y onForgotPassword callbacks.

2) FLUJO DE DATOS y NAVEGACIÓN (paso a paso)
   - Usuario escribe matrícula y contraseña.
     viewModel.onUserChange(...) y viewModel.onPassChange(...) actualizan StateFlows.
   - Usuario presiona "Iniciar Sesión":
     LoginScreen llama viewModel.login()
       -> LoginViewModel lanza corrutina que llama SigoRepository.login(user, pass)
       -> Repository llama ApiService (Retrofit) -> Backend
       -> Si la respuesta es exitosa, ViewModel emite LoginState.Success
          y posiblemente guarda Usuario en memoria/local (opcional).
       -> Si falla, ViewModel emite LoginState.Error

   - UI reacciona:
       • Si LoginState.Success -> LoginScreen detecta y ejecuta onLoginSuccess(user)
         => Normalmente navController.navigate("menu/$user") en AppNavHost.
       • Si LoginState.Error -> se muestra el mensaje "Matrícula o contraseña incorrectos".

3) NAVHOST (cómo usar onLoginSuccess / onForgotPassword)
   En AppNavHost:
   composable("login") {
       LoginScreen(
           viewModel = hiltViewModel(),
           onLoginSuccess = { username ->
               navController.navigate("menu/$username") {
                   popUpTo("login") { inclusive = true }
               }
           },
           onForgotPassword = {
               navController.navigate("recuperar")
           }
       )
   }

4) MEJORAS / PUNTOS A REVISAR (recomendaciones)
   - Mostrar un indicador de carga (ProgressIndicator) cuando loginState == Loading.
   - Manejar explícitamente errores de red vs. credenciales inválidas (mostrar mensajes distintos).
   - Evitar exponer la matrícula completa en logs o mensajes no cifrados.
   - Considerar persistir token/Usuario en DataStore o SharedPreferences si hay sesión persistente.
   - Proteger el flujo de "onLoginSuccess" para que solo ocurra una vez (ej. limpiar el estado Success cuando se navega).

5) EJEMPLO DE LoginViewModel (resumen mínimo funcional)
   class LoginViewModel(private val repository: SigoRepository) : ViewModel() {
       private val _user = MutableStateFlow("")
       val user: StateFlow<String> = _user

       private val _pass = MutableStateFlow("")
       val pass: StateFlow<String> = _pass

       private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
       val loginState: StateFlow<LoginState> = _loginState

       fun onUserChange(v: String) { _user.value = v }
       fun onPassChange(v: String) { _pass.value = v }

       fun login() {
           viewModelScope.launch {
               _loginState.value = LoginState.Loading
               val result = repository.login(_user.value, _pass.value)
               _loginState.value = if (result != null) LoginState.Success else LoginState.Error
           }
       }

       sealed class LoginState { object Idle; object Loading; object Success; object Error }
   }

6) RESUMEN RÁPIDO
   - Este archivo es la pantalla de entrada (login).
   - Depende del LoginViewModel y recursos gráficos.
   - Controla la navegación mediante callbacks (onLoginSuccess / onForgotPassword).
   - UI valida localmente la longitud de la matrícula y delega validaciones reales al ViewModel/backend.
*/









