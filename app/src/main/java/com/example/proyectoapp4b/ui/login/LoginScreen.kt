package com.example.proyectoapp4b.ui.login

import android.app.Activity                   // Permite cerrar la actividad actual (finish())
import androidx.compose.foundation.Image      // Para mostrar imágenes en Compose
import androidx.compose.foundation.background // Para aplicar colores de fondo a contenedores
import androidx.compose.foundation.clickable  // Para hacer elementos clickeables
import androidx.compose.foundation.layout.*   // Para usar Row, Column, Spacer, Box y padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape // Para bordes redondeados en campos y botones
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons  // Acceso al set de íconos por defecto
import androidx.compose.material.icons.filled.Close // Ícono de X para cerrar app
import androidx.compose.material.icons.filled.Lock  // Ícono de candado para contraseña
import androidx.compose.material.icons.filled.Person // Ícono de usuario
import androidx.compose.material.icons.filled.Warning // Ícono de advertencia para errores
import androidx.compose.material3.*            // Componentes Material 3 (Button, TextField, IconButton)
import androidx.compose.runtime.*              // Para estados con remember y mutableStateOf
import androidx.compose.ui.Alignment           // Para alinear elementos en Column, Row, Box
import androidx.compose.ui.Modifier            // Modificador general para COMPOSE
import androidx.compose.ui.platform.LocalContext // Para obtener el contexto actual (Activity)
import androidx.compose.ui.res.painterResource // Para cargar imágenes desde drawable
import androidx.compose.ui.text.font.FontWeight // Para cambiar peso de texto (negritas)
import androidx.compose.ui.text.input.PasswordVisualTransformation // Ocultar contraseña
import androidx.compose.ui.text.input.VisualTransformation         // Mostrar/Ocultar contraseña
import androidx.compose.ui.text.style.TextAlign // Alinear texto
import androidx.compose.ui.unit.dp             // Para tamaños, márgenes y paddings en dp
import com.example.proyectoapp4b.R            // Acceso a recursos (logos, íconos)
import com.example.proyectoapp4b.viewmodel.LoginViewModel // ViewModel que maneja login
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.example.proyectoapp4b.data.model.UserResponse

/* =======================================================================
   DOCUMENTACIÓN DETALLADA (línea a línea / bloque por bloque)
   - NO se modificaron tus comentarios que ya estaban en el código.
   - Los comentarios nuevos explican propósito, control de flujo, y efectos
     colaterales (p. ej. navegación, validaciones, resources).
   ======================================================================= */

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (user: UserResponse) -> Unit = {},
    onForgotPassword: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState().value
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Si login fue exitoso, llamamos callback
    if (uiState.loginSuccess && uiState.user != null) {
        onLoginSuccess(uiState.user)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Botón cerrar app
        IconButton(
            onClick = { (context as? Activity)?.finish() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(36.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // 🔹 habilita scroll
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Logos ---
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sigo),
                    contentDescription = "Logo SIGO",
                    modifier = Modifier.height(60.dp).padding(end = 12.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.utm),
                    contentDescription = "Logo UTM",
                    modifier = Modifier.height(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // --- Mensaje de error ---
            uiState.errorMessage?.let { error ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Advertencia",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }//Cierre del

            // --- Usuario ---
            OutlinedTextField(
                value = uiState.username,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Matrícula") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Usuario") },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(8.dp)
            )

            if (uiState.username.isNotEmpty() && uiState.username.length < 11) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Advertencia",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Matrícula no válida",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Contraseña ---
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
                trailingIcon = {
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
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Botón login ---
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = uiState.username.length >= 10 && !uiState.isLoading
            ) {
                Text("Iniciar Sesión", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿Olvidaste la contraseña?",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.clickable { onForgotPassword() },
                textAlign = TextAlign.Center
            )
        }

        // 🔹 Indicador de carga con overlay opaco
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000000).copy(alpha = 0.6f))
                    .zIndex(1f), // 🔹 asegura que esté encima
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 5.dp
                )
            }
        }

    }
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









