package com.example.vistbras.models

data class VistoriaRealizada(
    val vistoria_agendada: Int,
    val anexos_img: String? = null,
    val bocal_vedado: Boolean,
    val comentarios: String,
    val data_realizada: String? = null,
    val extintor: Int,
    val extintor_codigo: String? = null,
    val fiscal: Int,
    val fiscal_nome: String? = null,
    val id: Int? = null,
    val indicador_pressao: Boolean,
    val informacoes: String? = null,
    val local_acesso: Boolean,
    val numero_protocolo: String? = null,
    val pino_seguranca: Boolean,
    val resultado: String? = null,
)