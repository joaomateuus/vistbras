package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.models.Empresa
import com.example.vistbras.models.UserSession
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.cadastro_empresa.CadastroEmpresaViewModel
import com.example.vistbras.viewmodel.cadastro_empresa.CadastroEmpresaViewModelFactory

class CadastroEmpresaActivity : AppCompatActivity() {
    private lateinit var viewModel: CadastroEmpresaViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var token: String

    private lateinit var nomeInput: EditText
    private lateinit var cnpjInput: EditText
    private lateinit var contatoInput: EditText
    private lateinit var enderecoInput: EditText
    private lateinit var areaInput: EditText
    private lateinit var btnClear: TextView
    private lateinit var btnSubmit: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_empresa_acitivity)

        viewModel = ViewModelProvider(
            this,
            CadastroEmpresaViewModelFactory(EmpresaRepository(retrofitService))
        ).get(
            CadastroEmpresaViewModel::class.java
        )

        token = UserSession.getToken()
        nomeInput = findViewById(R.id.nome_input)
        cnpjInput = findViewById(R.id.cnpj_input)
        contatoInput = findViewById(R.id.contato_input)
        enderecoInput = findViewById(R.id.endereco_input)
        areaInput = findViewById(R.id.area_input)
        btnClear = findViewById(R.id.btn_limpar)
        btnSubmit = findViewById(R.id.btn_submit)

        setupUi()

    }

    override fun onStart() {
        super.onStart()

        viewModel.statusCreateEmpresa.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Empresa criada com sucesso", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, EmpresasAcitvity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Não foi possivel criar empresa", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun setupUi() {
        btnClear.setOnClickListener {
            clearForm()
        }
        btnSubmit.setOnClickListener {
            handleFormSubmit()
        }
    }

    private fun handleFormSubmit() {
        val data = Empresa(
            nome = nomeInput.text.toString(),
            cnpj = nomeInput.text.toString(),
            contato = contatoInput.text.toString(),
            endereco = enderecoInput.text.toString(),
            area_atuacao = areaInput.text.toString()
        )

        viewModel.createEmpresa(token, data)
    }

    private fun clearForm() {
        nomeInput.setText("")
        cnpjInput.setText("")
        contatoInput.setText("")
        enderecoInput.setText("")
        areaInput.setText("")
    }
}