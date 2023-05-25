package com.example.vistbras.viewmodel.vistorias

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.VistoriaAgendada
import com.example.vistbras.models.VistoriaRealizada
import com.example.vistbras.repositories.VistoriaAgendadaRepository
import com.example.vistbras.repositories.VistoriaRealizadaRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class VistoriaViewModel constructor(
    private val vistoriaAgendadaRepository: VistoriaAgendadaRepository,
    private val vistoriaRealizadaRepository: VistoriaRealizadaRepository
) : ViewModel() {
    val sucessGetVistoriasRealizadas = MutableLiveData<List<VistoriaRealizada>>()
    val failGetVistoriasRealizadas = MutableLiveData<String>()
    val sucessGetVistoriasAgendadas = MutableLiveData<List<VistoriaAgendada>>()
    val failGetVistoriasAgendadas = MutableLiveData<String>()


    fun getVistoriasRealizadas(token: String) {
        val request = vistoriaRealizadaRepository.getVistoriasRealizadas(token)

        request.enqueue(object : Callback<List<VistoriaRealizada>> {
            override fun onResponse(
                call: Call<List<VistoriaRealizada>>,
                response: Response<List<VistoriaRealizada>>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetVistoriasRealizadas.postValue(response.body())
                } else {
                    failGetVistoriasRealizadas.postValue("Nao foi possivel buscar as vistorias")
                }
            }

            override fun onFailure(call: Call<List<VistoriaRealizada>>, t: Throwable) {
                failGetVistoriasRealizadas.postValue("Nao foi possivel buscar as vistorias")
            }
        })
    }

    fun getVistoriasAgendadas(token: String) {
        val request = vistoriaAgendadaRepository.getVistoriasAgendadas(token)

        request.enqueue(object : Callback<List<VistoriaAgendada>> {
            override fun onResponse(
                call: Call<List<VistoriaAgendada>>,
                response: Response<List<VistoriaAgendada>>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetVistoriasAgendadas.postValue(response.body())
                } else {
                    failGetVistoriasAgendadas.postValue("Nao foi possivel buscar empresas")
                }
            }

            override fun onFailure(call: Call<List<VistoriaAgendada>>, t: Throwable) {
                failGetVistoriasAgendadas.postValue("Nao foi possivel buscar empresas")
            }
        })
    }
}