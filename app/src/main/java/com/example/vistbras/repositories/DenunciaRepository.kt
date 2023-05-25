package com.example.vistbras.repositories

import com.example.vistbras.models.Denuncia
import com.example.vistbras.rest.RetrofitService

class DenunciaRepository constructor(private val retrofitService: RetrofitService) {
    fun createDenuncia(token: String, denuncia: Denuncia) =
        retrofitService.createDenuncia(token, denuncia)

    fun getDenuncias() = retrofitService.getDenuncias()

}