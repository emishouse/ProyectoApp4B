package com.example.proyectoapp4b.model

data class Usuario(
    val nombre: String? = null,
    val primerApellido: String? = null,
    val segundoApellido: String? = null,
    val fechaNacimiento: String? = null,
    val estadoNacimiento: String? = null,
    val sexo: String? = null,
    val curp: String? = null,
    val nss: String? = null,
    val telefono: String? = null,
    val correoAlternativo: String? = null,
    val correoInstitucional: String? = null,
    val usuario: String? = null,            // Matrícula o ID UTM
    val perfil: String? = null,
    val estatusPerfil: String? = null
)
