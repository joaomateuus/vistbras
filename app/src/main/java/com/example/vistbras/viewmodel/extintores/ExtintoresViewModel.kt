package com.example.vistbras.viewmodel.extintores

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.Extintores
import com.example.vistbras.repositories.ExtintoresRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class ExtintoresViewModel constructor(private val repository: ExtintoresRepository) : ViewModel() {
    val extintores = MutableLiveData<Extintores>()
    val errorMessageExtintores = MutableLiveData<String>()

    fun getExtintores(token: String) {
        val request = repository.getExtintores(token)

        request.enqueue(object : Callback<Extintores> {
            override fun onResponse(call: Call<Extintores>, response: Response<Extintores>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    extintores.postValue(response.body())
                } else {
                    errorMessageExtintores.postValue("Não foi possivel buscar os extintores")
                }
            }

            override fun onFailure(call: Call<Extintores>, t: Throwable) {
                errorMessageExtintores.postValue("Não foi possivel buscar os extintores")
            }
        })
    }

}