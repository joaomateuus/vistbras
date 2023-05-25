package com.example.vistbras.viewmodel.cadastro_denuncia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.Denuncia
import com.example.vistbras.repositories.DenunciaRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class CadastroDenunciaViewModel constructor(private val repository: DenunciaRepository) :
    ViewModel() {
    val statusCreateDenuncia = MutableLiveData<Boolean>()

    fun createDenuncia(token: String, denuncia: Denuncia) {
        val request = repository.createDenuncia(token, denuncia)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    statusCreateDenuncia.postValue(true)
                } else {
                    statusCreateDenuncia.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                statusCreateDenuncia.postValue(false)
            }
        })

    }


}