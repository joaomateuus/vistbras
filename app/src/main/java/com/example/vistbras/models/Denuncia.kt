package com.example.vistbras.models

data class Denuncia(
    val anexo: String? = null,
    val descricao: String,
    val dt_denuncia: String? = null,
    val id: Int? = null,
    val local_ocorrencia: String,
    val numero_protocolo: String? = null,
    val status: String? = null,
    val usuario: Int? = null,
    val nome_usuario: String? = null

)