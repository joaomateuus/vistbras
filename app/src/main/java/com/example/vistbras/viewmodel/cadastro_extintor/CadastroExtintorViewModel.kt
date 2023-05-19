package com.example.vistbras.viewmodel.cadastro_extintor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.Empresa
import com.example.vistbras.models.ExtintorItem
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.repositories.ExtintoresRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class CadastroExtintorViewModel
constructor(
    private val repository: ExtintoresRepository,
    private val empresaRepository: EmpresaRepository
) : ViewModel() {
    val sucessFetchEmpresas = MutableLiveData<List<Empresa>>()
    val statusCreateExtintor = MutableLiveData<Boolean>()
    val errorMessageFetchEmpresas = MutableLiveData<String>()

    fun getEmpresas(token: String) {
        val request = empresaRepository.getEmpresas(token)

        request.enqueue(object : Callback<List<Empresa>> {
            override fun onResponse(call: Call<List<Empresa>>, response: Response<List<Empresa>>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    sucessFetchEmpresas.postValue(response.body())
                } else {
                    errorMessageFetchEmpresas.postValue("Não foi possível buscar as empresas")

                }
            }

            override fun onFailure(call: Call<List<Empresa>>, t: Throwable) {
                errorMessageFetchEmpresas.postValue("Não foi possível buscar as empresas")
            }
        })
    }

    fun createExtintor(token: String, extintor: ExtintorItem) {
        val request = repository.createExtintor(token, extintor)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    statusCreateExtintor.postValue(true)
                } else {
                    statusCreateExtintor.postValue(false)
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                statusCreateExtintor.postValue(false)
            }
        })
    }


}