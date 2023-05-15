package com.example.vistbras.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.models.FiscalUser
import com.example.vistbras.models.User
import com.example.vistbras.repositories.UserRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.cadastro_fiscal.CadastroFiscalViewModel
import com.example.vistbras.viewmodel.cadastro_fiscal.CadastroFiscalViewModelFactory

class CadastroFiscalActivity : AppCompatActivity() {
    private lateinit var viewModel: CadastroFiscalViewModel
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var codFiscalInput: EditText
    private lateinit var empresaInput: EditText
    private lateinit var nomeInput: EditText
    private lateinit var usuarioInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var cpfInput: EditText
    private lateinit var telefoneInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var passwordConfirmInput: EditText
    private lateinit var submitButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_fiscal)

        codFiscalInput = findViewById(R.id.codfiscal_input)
        empresaInput = findViewById(R.id.empresa_input)
        nomeInput = findViewById(R.id.nome_input)
        usuarioInput = findViewById(R.id.usuario_input)
        emailInput = findViewById(R.id.email_input)
        cpfInput = findViewById(R.id.cpf_input)
        telefoneInput = findViewById(R.id.telefone_input)
        passwordInput = findViewById(R.id.password_input)
        passwordConfirmInput = findViewById(R.id.passwordconfirm_input)
        submitButton = findViewById(R.id.btn_submit)

        viewModel = ViewModelProvider(
            this, CadastroFiscalViewModelFactory(UserRepository(retrofitService))
        ).get(
            CadastroFiscalViewModel::class.java
        )

        handleFormSubmit()
    }

    override fun onStart() {
        super.onStart()
        viewModel.status.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this,
                    "Fiscal criado com sucesso",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Erro ao criar fiscal. Tente novamente.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun handleFormSubmit() {
        submitButton.setOnClickListener {
            val fiscal = FiscalUser(
                codigo_fiscal = codFiscalInput.text.toString(),
                empresa_fiscal = empresaInput.text.toString(),
                user_data = User(
                    username = usuarioInput.text.toString(),
                    nome = nomeInput.text.toString(),
                    email = emailInput.text.toString(),
                    cpf = cpfInput.text.toString(),
                    is_fiscal = true,
                    numero_telefone = telefoneInput.text.toString(),
                    is_staff = false,
                    is_superuser = false,
                    password = passwordInput.text.toString(),
                    password_confirm = passwordConfirmInput.text.toString()
                )
            )

            viewModel.createFiscalUser(fiscal)
        }
    }
}