package com.example.vistbras.repositories

import com.example.vistbras.models.VistoriaRealizada
import com.example.vistbras.rest.RetrofitService
import okhttp3.MultipartBody

class VistoriaRealizadaRepository constructor(private val retrofitService: RetrofitService) {
    fun createVistoria(
        token: String,
        vistoriaRealizada: VistoriaRealizada,
    ) =
        retrofitService.createVistoria(token, vistoriaRealizada)

    fun getVistoriasRealizadas(token: String) =
        retrofitService.getVistoriasRealizadas(token)

    fun getVistoriaRealizada(token: String, vistoriaId: Int) =
        retrofitService.getVistoriaRealizada(token, vistoriaId)

}