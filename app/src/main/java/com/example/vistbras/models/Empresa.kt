package com.example.vistbras.models

data class Empresa(
    val id: Int? = null,
    val nome: String,
    val area_atuacao: String,
    val cnpj: String,
    val contato: String,
    val endereco: String,
)