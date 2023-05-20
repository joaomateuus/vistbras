package com.example.vistbras.repositories

import com.example.vistbras.models.Empresa
import com.example.vistbras.rest.RetrofitService

class EmpresaRepository constructor(private val retrofitService: RetrofitService) {
    fun getEmpresas(token: String) =
        retrofitService.getEmpresas(token)

    fun createEmpresa(token: String, empresa: Empresa) =
        retrofitService.createEmpresa(token, empresa)

    fun getEmpresa(token: String, empresaId: Int) =
        retrofitService.getEmpresa(token, empresaId)

    fun editEmpresa(token: String, empresaId: Int, empresa: Empresa) =
        retrofitService.editEmpresa(token, empresaId, empresa)

    fun deleteEmpresa(token: String, empresaId: Int) =
        retrofitService.deleteEmpresa(token, empresaId)

}