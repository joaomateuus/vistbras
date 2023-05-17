package com.example.vistbras.models

data class User(
    val id: Int? = null,
    val email: String,
    val is_fiscal: Boolean,
    val is_staff: Boolean,
    val is_superuser: Boolean,
    val nome: String,
    val cpf: String,
    val numero_telefone: String,
    val password: String,
    val password_confirm: String,
    val username: String
)