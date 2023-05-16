package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.models.LoginRequest
import com.example.vistbras.models.UserSession
import com.example.vistbras.repositories.UserRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.store.LoggedUserSession
import com.example.vistbras.viewmodel.login.MainActivityViewModel
import com.example.vistbras.viewmodel.login.MainActivityViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var usuarioInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var submitButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usuarioInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        submitButton = findViewById(R.id.btn_submit)


        viewModel = ViewModelProvider(
            this, MainActivityViewModelFactory(UserRepository(retrofitService))
        ).get(
            MainActivityViewModel::class.java
        )

        handleFormSubmit()
    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessLogin.observe(this, Observer {
            UserSession.setToken(it.access)
            viewModel.getLoggedUser(it.access)
        })

        viewModel.statusFetchUser.observe(this, Observer {
            val user = LoggedUserSession.getUser()

            if (user != null) {
                if (user.is_fiscal) {
                    startActivity(Intent(this, HomeFiscalActivity::class.java))
                } else {
                    startActivity(Intent(this, HomeUsuarioActivity::class.java))
                }
            } else {
                Toast.makeText(
                    this,
                    "Erro ao fazer login. Tente novamente.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    private fun handleFormSubmit() {
        submitButton.setOnClickListener {
            viewModel.loginUser(
                LoginRequest(
                    usuarioInput.text.toString(),
                    passwordInput.text.toString()
                )
            )
        }
    }
}