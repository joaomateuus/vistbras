package com.example.vistbras.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.vistbras.models.LoginRequest
import com.example.vistbras.models.LoginResponse
import com.example.vistbras.models.User
import com.example.vistbras.repositories.UserRepository
import com.example.vistbras.store.LoggedUserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class MainActivityViewModel(
    private val repository: UserRepository
) : ViewModel() {
    //    private val tokenStorage = TokenStorage(context)
//    private val LoggedUserSession = LoggedUserSession(context)
    val statusLogin = MutableLiveData<Boolean>()
    val statusFetchUser = MutableLiveData<Boolean>()
    val sucessLogin = MutableLiveData<LoginResponse>()

    //    val sucessFetchLoggedUser = MutableLiveData<User>()
    val errorMessage = MutableLiveData<String>()

    fun getLoggedUser(token: String) {
        try {
            val decodedJWT: DecodedJWT = JWT.decode(token)
            val userId: Int = decodedJWT.getClaim("user_id").asInt()

            val request = repository.getLoggedUser(token, userId)

            request.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        response.body()?.let { LoggedUserSession.setUser(it) }
                        statusFetchUser.postValue(true)
                    } else {
                        statusFetchUser.postValue(false)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    errorMessage.postValue("Não foi possivel entrar. Verifique seu usuário e senha.")
                }
            })

        } catch (e: Exception) {
            // Falha na decodificação do token
            e.printStackTrace()
        }
    }

    fun loginUser(data: LoginRequest) {
        val request = repository.loginUser(data)

        // salvar o token
        // fazer fetch do user e guardar suas informcaoes
        // passar o is_fiscal para fazer a validacao no frontend
        request.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.code() == 200) {
                    sucessLogin.postValue(response.body())
                } else {
                    errorMessage.postValue("Não foi possivel entrar. Verifique seu usuário e senha.")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}