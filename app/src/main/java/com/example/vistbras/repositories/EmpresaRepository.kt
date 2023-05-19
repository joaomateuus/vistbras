package com.example.vistbras.repositories

import com.example.vistbras.rest.RetrofitService

class EmpresaRepository constructor(private val retrofitService: RetrofitService) {
    fun getEmpresas(token: String) = retrofitService.getEmpresas(token)

}