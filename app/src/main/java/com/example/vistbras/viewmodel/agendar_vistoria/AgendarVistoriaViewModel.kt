package com.example.vistbras.viewmodel.agendar_vistoria

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.Empresa
import com.example.vistbras.models.VistoriaAgendada
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.repositories.VistoriaAgendadaRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.HTTP
import java.net.HttpURLConnection


class AgendarVistoriaViewModel constructor(
    private val repository: VistoriaAgendadaRepository,
    private val empresasRepository: EmpresaRepository
) : ViewModel() {
    val sucessGetEmpresas = MutableLiveData<List<Empresa>>()
    val failGetEmpresas = MutableLiveData<String>()
    val statusCreateAgendarVistoria = MutableLiveData<Boolean>()

    fun getEmpresas(token: String) {
        val request = empresasRepository.getEmpresas(token)

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

    fun createVistoriaAgendada(token: String, vistoriaAgendada: VistoriaAgendada) {
        val request = repository.createVistoriaAgendada(token, vistoriaAgendada)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    statusCreateAgendarVistoria.postValue(true)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                statusCreateAgendarVistoria.postValue(false)
            }
        })
    }


}