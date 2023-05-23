package com.example.vistbras.viewmodel.selecionar_vist_agendada

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.VistoriaAgendada
import com.example.vistbras.repositories.FiscalRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.HTTP
import java.net.HttpURLConnection

class SelecionarVistoriaAgendadaViewModel constructor(private val repository: FiscalRepository) :
    ViewModel() {
    val sucessGetVistorias = MutableLiveData<List<VistoriaAgendada>>()
    val failGetVistorias = MutableLiveData<String>()

    fun getVistoriasAgendadas(token: String) {
        val request = repository.getVistorias(token)

        request.enqueue(object : Callback<List<VistoriaAgendada>> {
            override fun onResponse(
                call: Call<List<VistoriaAgendada>>,
                response: Response<List<VistoriaAgendada>>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetVistorias.postValue(response.body())
                } else {
                    failGetVistorias.postValue("Nao foi possivel buscar as vistorias agendadas")
                }
            }

            override fun onFailure(call: Call<List<VistoriaAgendada>>, t: Throwable) {
                failGetVistorias.postValue("Nao foi possivel buscar as vistorias agendadas")
            }
        })
    }


}