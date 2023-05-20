package com.example.vistbras.models

data class ExtintorItem(
    val codigo: String,
    val data_validade: String,
    val empresa: Int? = null,
    val local: String,
    val nivel: String,
    val tamanho: String,
    val termo_garantia: Boolean,
    val tipo: String
)