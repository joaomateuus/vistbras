package com.example.vistbras.viewmodel.vistoria_realizada

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.VistoriaRealizada
import com.example.vistbras.repositories.VistoriaRealizadaRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class VistoriaRealizadaViewModel constructor(private val repository: VistoriaRealizadaRepository) :
    ViewModel() {
    val sucessGetVistoria = MutableLiveData<VistoriaRealizada>()
    val failGetVistoria = MutableLiveData<String>()

    fun getVistoriaRealizada(token: String, vistoriaId: Int) {
        val request = repository.getVistoriaRealizada(token, vistoriaId)

        request.enqueue(object : Callback<VistoriaRealizada> {
            override fun onResponse(
                call: Call<VistoriaRealizada>,
                response: Response<VistoriaRealizada>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetVistoria.postValue(response.body())
                } else {
                    failGetVistoria.postValue("Não foi possível buscar a vistoria")
                }
            }

            override fun onFailure(call: Call<VistoriaRealizada>, t: Throwable) {
                failGetVistoria.postValue("Não foi possível buscar a vistoria")
            }
        })

    }

}