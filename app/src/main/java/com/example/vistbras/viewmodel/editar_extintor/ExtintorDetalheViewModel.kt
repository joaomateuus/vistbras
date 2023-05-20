package com.example.vistbras.viewmodel.editar_extintor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.ExtintorItem
import com.example.vistbras.models.ExtintorItemResponse
import com.example.vistbras.repositories.ExtintoresRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class ExtintorDetalheViewModel constructor(private val repository: ExtintoresRepository) :
    ViewModel() {
    val sucessGetExtintor = MutableLiveData<ExtintorItemResponse>()
    val failGetExtintor = MutableLiveData<String>()
    val statusEditExtintor = MutableLiveData<Boolean>()
    val statusDeleteExtintor = MutableLiveData<Boolean>()

    fun getExtintor(token: String, extintorId: Int) {
        val request = repository.getExtintor(token, extintorId)

        request.enqueue(object : Callback<ExtintorItemResponse> {
            override fun onResponse(
                call: Call<ExtintorItemResponse>,
                response: Response<ExtintorItemResponse>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessGetExtintor.postValue(response.body())
                } else {
                    failGetExtintor.postValue("Não foi possivel buscar o extintor")
                }
            }

            override fun onFailure(call: Call<ExtintorItemResponse>, t: Throwable) {
                failGetExtintor.postValue("Não foi possivel buscar o extintor")
            }
        })

    }

    fun editExtintor(token: String, extintorId: Int, extintor: ExtintorItem) {
        val request = repository.editExtintor(token, extintorId, extintor)

        request.enqueue(object : Callback<ExtintorItemResponse> {
            override fun onResponse(
                call: Call<ExtintorItemResponse>,
                response: Response<ExtintorItemResponse>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    statusEditExtintor.postValue(true)
                } else {
                    statusEditExtintor.postValue(false)
                }
            }

            override fun onFailure(call: Call<ExtintorItemResponse>, t: Throwable) {
                statusEditExtintor.postValue(false)
            }
        })

    }


}