package com.example.vistbras.viewmodel.empresas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.Empresa
import com.example.vistbras.repositories.EmpresaRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class EmpresasViewModel constructor(private val repository: EmpresaRepository) : ViewModel() {
    val sucessGetEmpresas = MutableLiveData<List<Empresa>>()
    val failGetEmpresas = MutableLiveData<String>()


    fun getEmpresas(token: String) {
        val request = repository.getEmpresas(token)

        request.enqueue(object : Callback<List<Empresa>> {
            override fun onResponse(call: Call<List<Empresa>>, response: Response<List<Empresa>>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetEmpresas.postValue(response.body())
                } else {
                    failGetEmpresas.postValue("Não foi possível buscar as empresas")

                }
            }

            override fun onFailure(call: Call<List<Empresa>>, t: Throwable) {
                failGetEmpresas.postValue("Não foi possível buscar as empresas")
            }
        })
    }


}