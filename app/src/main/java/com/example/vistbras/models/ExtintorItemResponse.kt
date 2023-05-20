package com.example.vistbras.models

data class ExtintorItemResponse(
    val codigo: String,
    val data_validade: String,
    val empresa: Int,
    val empresa_nome: String,
    val id: Int,
    val local: String,
    val nivel: String,
    val qrcode: Qrcode?,
    val tamanho: String,
    val termo_garantia: Boolean,
    val tipo: String
)