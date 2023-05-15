package com.example.vistbras.viewmodel.cadastro_usuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vistbras.models.User
import com.example.vistbras.repositories.UserRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroUsuarioViewModel(private val repository: UserRepository) : ViewModel() {
    val status = MutableLiveData<Boolean>()

    fun createUser(user: User) {
        val request = repository.createUser(user)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    status.postValue(true)
                } else {
                    status.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                status.postValue(false)
            }
        })
    }
}