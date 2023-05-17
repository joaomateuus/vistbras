package com.example.vistbras.repositories

import com.example.vistbras.rest.RetrofitService

class FiscalRepository constructor(private val retrofitService: RetrofitService) {
    fun getVistorias(token: String) =
        retrofitService.getVistorias(token)

    fun getLoggedFiscal(token: String, userId: Int?) =
        retrofitService.getLoggedFiscal(token, userId)

}