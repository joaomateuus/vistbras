package com.example.vistbras.repositories

import com.example.vistbras.models.VistoriaAgendada
import com.example.vistbras.rest.RetrofitService

class VistoriaAgendadaRepository constructor(private val retrofitService: RetrofitService) {
    fun createVistoriaAgendada(token: String, vistoriaAgendada: VistoriaAgendada) =
        retrofitService.createVistoriaAgendada(token, vistoriaAgendada)

    fun getVistoriasAgendadas(token: String) = retrofitService.getVistoriasAgendadas(token)


}