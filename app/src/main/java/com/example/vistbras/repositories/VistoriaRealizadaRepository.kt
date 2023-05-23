package com.example.vistbras.repositories

import com.example.vistbras.models.VistoriaRealizada
import com.example.vistbras.rest.RetrofitService

class VistoriaRealizadaRepository constructor(private val retrofitService: RetrofitService) {
    fun createVistoria(token: String, vistoriaRealizada: VistoriaRealizada) =
        retrofitService.createVistoria(token, vistoriaRealizada)

}