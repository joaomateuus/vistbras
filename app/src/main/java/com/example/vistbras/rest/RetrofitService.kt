package com.example.vistbras.rest

import com.example.vistbras.models.Empresa
import com.example.vistbras.models.Extintor
import com.example.vistbras.models.FiscalRequest
import com.example.vistbras.models.FiscalUser
import com.example.vistbras.models.LoginRequest
import com.example.vistbras.models.LoginResponse
import com.example.vistbras.models.User
import com.example.vistbras.models.Vistoria
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("api/v1/vistorias/")
    fun getVistorias(
        @Header("Authorization") authorization: String
    ): Call<List<Vistoria>>

    @POST("api/v1/fiscais/get_fiscal_by_user_id/")
    fun getLoggedFiscal(
        @Header("Authorization") authorization: String,
        @Query("user") userId: Int?
    ): Call<FiscalRequest>

    @GET("api/v1/empresas/")
    fun getEmpresas(
        @Header("Authorization") authorization: String
    ): Call<List<Empresa>>

    @POST("api/v1/extintores")
    fun createExtintor(
        @Header("Authorization") authorization: String,
        @Body extintor: Extintor
    ): Call<ResponseBody>

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