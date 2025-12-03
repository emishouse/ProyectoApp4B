package com.example.proyectoapp4b

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.proyectoapp4b.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNavHost()   // 👈 AHORA EL NAVHOST ESTÁ SEPARADO
            }
        }
    }
}



