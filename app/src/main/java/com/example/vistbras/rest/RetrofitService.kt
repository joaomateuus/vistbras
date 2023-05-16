package com.example.vistbras.rest

import com.example.vistbras.models.FiscalUser
import com.example.vistbras.models.LoginRequest
import com.example.vistbras.models.LoginResponse
import com.example.vistbras.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {

    @POST("account/usuarios/")
    fun createUser(@Body user: User): Call<ResponseBody>

    @POST("api/v1/fiscais/")
    fun createFiscalUser(@Body fiscal: FiscalUser): Call<ResponseBody>

    @POST("token/")
    fun loginUser(@Body data: LoginRequest): Call<LoginResponse>

    @GET("account/usuarios/{id}/")
    fun getLoggedUser(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Call<User>

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