package com.example.vistbras.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.models.User
import com.example.vistbras.repositories.UserRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.cadastro_usuario.CadastroUsuarioViewModel
import com.example.vistbras.viewmodel.cadastro_usuario.CadastroUsuarioViewModelFactory

class CadastroUsuarioActivity : AppCompatActivity() {
    private lateinit var viewModel: CadastroUsuarioViewModel
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var nomeInput: EditText
    private lateinit var usuarioInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var cpfInput: EditText
    private lateinit var telefoneInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var passwordConfirmInput: EditText
    private lateinit var submitButtom: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        nomeInput = findViewById(R.id.nome_input)
        usuarioInput = findViewById(R.id.usuario_input)
        emailInput = findViewById(R.id.email_input)
        cpfInput = findViewById(R.id.cpf_input)
        telefoneInput = findViewById(R.id.telefone_input)
        passwordInput = findViewById(R.id.password_input)
        passwordConfirmInput = findViewById(R.id.passwordconfirm_input)
        submitButtom = findViewById(R.id.btn_submit)

        viewModel = ViewModelProvider(
            this, CadastroUsuarioViewModelFactory(UserRepository(retrofitService))
        ).get(
            CadastroUsuarioViewModel::class.java
        )

        handleFormSubmit()
    }

    override fun onStart() {
        super.onStart()
        viewModel.status.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this,
                    "Usuario criado com sucesso",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Erro ao criar usario. Tente novamente.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun handleFormSubmit() {
        submitButtom.setOnClickListener {
            val user = User(
                username = usuarioInput.text.toString(),
                nome = nomeInput.text.toString(),
                email = emailInput.text.toString(),
                cpf = cpfInput.text.toString(),
                is_fiscal = false,
                numero_telefone = telefoneInput.text.toString(),
                is_staff = false,
                is_superuser = false,
                password = passwordInput.text.toString(),
                password_confirm = passwordConfirmInput.text.toString()
            )

            viewModel.createUser(user)
        }
    }

    private fun resetForm() {
        usuarioInput.text.clear()
        nomeInput.text.clear()
        emailInput.text.clear()
        cpfInput.text.clear()
        telefoneInput.text.clear()
        passwordInput.text.clear()
        passwordConfirmInput.text.clear()
    }
}