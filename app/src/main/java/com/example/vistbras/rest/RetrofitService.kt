package com.example.vistbras.rest

import com.example.vistbras.models.FiscalUser
import com.example.vistbras.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("account/usuarios/")
    fun createUser(@Body user: User): Call<ResponseBody>

    @POST("api/v1/fiscais/")
    fun createFiscalUser(@Body fiscal: FiscalUser): Call<ResponseBody>

    companion object {
        private val retrofitService: RetrofitService by lazy {
            val retrofitService = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService.create(RetrofitService::class.java)
        }

        fun getInstance(): RetrofitService {
            return retrofitService
        }
    }
}