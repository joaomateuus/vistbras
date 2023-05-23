package com.example.vistbras.viewmodel.fiscal_home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.FiscalRequest
import com.example.vistbras.models.Vistoria
import com.example.vistbras.models.VistoriaAgendada
import com.example.vistbras.repositories.FiscalRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class FiscalHomeViewModel(private val repository: FiscalRepository) : ViewModel() {
    val sucessGetVistorias = MutableLiveData<List<VistoriaAgendada>>()
    val sucessGetLoggedFiscal = MutableLiveData<FiscalRequest>()

    val errorMessage = MutableLiveData<String>()
    val errorMessageFetchFiscal = MutableLiveData<String>()

    fun getVistorias(token: String) {
        val request = repository.getVistorias(token)

        request.enqueue(object : Callback<List<VistoriaAgendada>> {
            override fun onResponse(
                call: Call<List<VistoriaAgendada>>,
                response: Response<List<VistoriaAgendada>>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetVistorias.postValue(response.body())
                } else {
                    errorMessage.postValue("Nao foi possivel buscar as vistorias")
                }
            }

            override fun onFailure(call: Call<List<VistoriaAgendada>>, t: Throwable) {
                errorMessage.postValue("Nao foi possivel buscar as vistorias")
            }
        })
    }

    fun getLoggedFiscal(token: String, userId: Int?) {
        val request = repository.getLoggedFiscal(token, userId)

        request.enqueue(object : Callback<FiscalRequest> {
            override fun onResponse(call: Call<FiscalRequest>, response: Response<FiscalRequest>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetLoggedFiscal.postValue(response.body())
                } else {
                    errorMessageFetchFiscal.postValue("Nao foi possivel buscar fiscal logado")
                }
            }

            override fun onFailure(call: Call<FiscalRequest>, t: Throwable) {
                errorMessageFetchFiscal.postValue("Nao foi possivel buscar fiscal logado")
            }
        })
    }

}