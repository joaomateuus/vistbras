package com.example.vistbras.viewmodel.realizar_vistoria

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.ExtintorItemResponse
import com.example.vistbras.models.Extintores
import com.example.vistbras.models.VistoriaRealizada
import com.example.vistbras.repositories.ExtintoresRepository
import com.example.vistbras.repositories.VistoriaRealizadaRepository
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class RealizarVistoriaViewModel constructor(
    private val repository: VistoriaRealizadaRepository,
    private val extintoresRepository: ExtintoresRepository
) : ViewModel() {
    val extintores = MutableLiveData<Extintores>()
    val failExtintores = MutableLiveData<String>()
    val statusCreateVistoria = MutableLiveData<Boolean>()

    fun getExtintores(token: String) {
        val request = extintoresRepository.getExtintores(token)

        request.enqueue(object : Callback<Extintores> {
            override fun onFailure(call: Call<Extintores>, t: Throwable) {
                failExtintores.postValue("Não foi possivel buscar os extintores")
            }

            override fun onResponse(call: Call<Extintores>, response: Response<Extintores>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    extintores.postValue(response.body())
                } else {
                    failExtintores.postValue("Não foi possivel buscar os extintores")
                }
            }
        })
    }

    fun createVistoria(token: String, vistoriaRealizada: VistoriaRealizada) {
        val request = repository.createVistoria(token, vistoriaRealizada)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    statusCreateVistoria.postValue(true)
                } else {
                    statusCreateVistoria.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                statusCreateVistoria.postValue(false)
            }
        })

    }
}