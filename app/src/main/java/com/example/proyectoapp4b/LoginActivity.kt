package com.example.proyectoapp4b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.proyectoapp4b.MenuActivity

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtUser = findViewById<EditText>(R.id.txtUser)
        val edtPass = findViewById<EditText>(R.id.txtPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val user = edtUser.text.toString()
            val pass = edtPass.text.toString()

            if (user == "Eduardo" && pass == "123") {
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("usuario", user)
                startActivity(intent)
            } else if (user == "Humberto" && pass == "123") {
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("usuario", user)
                startActivity(intent)
            }
            else if (user == "Chucho" && pass == "123"){
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("usuario", user)
                startActivity(intent)
            }
            else if (user == "Mahal" && pass == "123"){
                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("usuario", user)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

