package com.example.vistbras.models

data class Vistoria(
    val data_agendada: String,
    val data_realizada: Any,
    val denuncia: Int,
    val empresa: String,
    val extintor: Int,
    val fiscal: Int,
    val id: Int,
    val informacoes: String,
    val resultado: Any
)