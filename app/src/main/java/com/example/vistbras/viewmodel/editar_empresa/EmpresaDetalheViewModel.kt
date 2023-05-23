package com.example.vistbras.viewmodel.editar_empresa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.Empresa
import com.example.vistbras.repositories.EmpresaRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class EmpresaDetalheViewModel constructor(private val repository: EmpresaRepository) : ViewModel() {
    val statusEditEmpresa = MutableLiveData<Boolean>()
    val sucessFetchEmpresa = MutableLiveData<Empresa>()
    val failFetchEmpresa = MutableLiveData<String>()

    fun getEmpresa(token: String, empresaId: Int) {
        val request = repository.getEmpresa(token, empresaId)

        request.enqueue(object : Callback<Empresa> {
            override fun onResponse(call: Call<Empresa>, response: Response<Empresa>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessFetchEmpresa.postValue(response.body())
                } else {
                    failFetchEmpresa.postValue("Não foi possicel buscar a empresa")
                }
            }

            override fun onFailure(call: Call<Empresa>, t: Throwable) {
                failFetchEmpresa.postValue("Não foi possicel buscar a empresa")
            }
        })
    }

    fun editEmpresa(token: String, empresaId: Int, empresa: Empresa) {
        val request = repository.editEmpresa(token, empresaId, empresa)

        request.enqueue(object : Callback<Empresa> {
            override fun onResponse(call: Call<Empresa>, response: Response<Empresa>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    statusEditEmpresa.postValue(true)
                } else {
                    statusEditEmpresa.postValue(false)
                }
            }

            override fun onFailure(call: Call<Empresa>, t: Throwable) {
                statusEditEmpresa.postValue(false)
            }
        })
    }


}