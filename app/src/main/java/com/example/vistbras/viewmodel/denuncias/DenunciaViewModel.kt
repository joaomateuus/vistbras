package com.example.vistbras.viewmodel.denuncias

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.Denuncia
import com.example.vistbras.repositories.DenunciaRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class DenunciaViewModel constructor(private val repository: DenunciaRepository) : ViewModel() {
    val sucessGetDenuncias = MutableLiveData<List<Denuncia>>()
    val failGetDenuncias = MutableLiveData<String>()

    fun getDenuncias() {
        val request = repository.getDenuncias()

        request.enqueue(object : Callback<List<Denuncia>> {
            override fun onResponse(
                call: Call<List<Denuncia>>,
                response: Response<List<Denuncia>>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetDenuncias.postValue(response.body())
                } else {
                    failGetDenuncias.postValue("Não foi possivel buscar as denuncias")
                }
            }

            override fun onFailure(call: Call<List<Denuncia>>, t: Throwable) {
                failGetDenuncias.postValue("Não foi possivel buscar as denuncias")
            }
        })
    }

}