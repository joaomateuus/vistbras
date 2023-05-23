package com.example.vistbras.models

data class VistoriaAgendada(
    val contato: String,
    val data_agendada: String,
    val empresa: Int,
    val empresa_agendada: String? = null,
    val id: Int? = null,
    val protocolo_agendamento: String? = null,
    val status: String? = null
)