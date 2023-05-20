package com.example.vistbras.viewmodel.cadastro_empresa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.Empresa
import com.example.vistbras.repositories.EmpresaRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroEmpresaViewModel constructor(private val repository: EmpresaRepository) :
    ViewModel() {
    val statusCreateEmpresa = MutableLiveData<Boolean>()

    fun createEmpresa(token: String, empresa: Empresa) {
        val request = repository.createEmpresa(token, empresa)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    statusCreateEmpresa.postValue(true)
                } else {
                    statusCreateEmpresa.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                statusCreateEmpresa.postValue(true)
            }
        })
    }


}