package com.example.proyectoapp4b

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val usuario = intent.getStringExtra("usuario")
        val txtMensaje = findViewById<TextView>(R.id.txtMensajeMenu)

        txtMensaje.text = "Navegación exitosa, bienvenido $usuario"
    }
}

