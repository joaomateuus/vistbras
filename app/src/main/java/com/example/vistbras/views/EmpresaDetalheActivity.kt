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
import com.example.vistbras.viewmodel.editar_empresa.EmpresaDetalheViewModel
import com.example.vistbras.viewmodel.editar_empresa.EmpresaDetalheViewModelFactory

class EmpresaDetalheActivity : AppCompatActivity() {
    private lateinit var viewModel: EmpresaDetalheViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var token: String

    private var empresaId: Int = 0
    private lateinit var nomeInput: EditText
    private lateinit var cnpjInput: EditText
    private lateinit var contatoInput: EditText
    private lateinit var enderecoInput: EditText
    private lateinit var areaInput: EditText
    private lateinit var btnSubmit: TextView
    private lateinit var btnDelete: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_detalhe)

        viewModel = ViewModelProvider(
            this, EmpresaDetalheViewModelFactory(EmpresaRepository(retrofitService))
        ).get(
            EmpresaDetalheViewModel::class.java
        )

        token = UserSession.getToken()
        empresaId = intent.getIntExtra("empresaId", 0)
        nomeInput = findViewById(R.id.nome_input)
        cnpjInput = findViewById(R.id.cnpj_input)
        contatoInput = findViewById(R.id.contato_input)
        enderecoInput = findViewById(R.id.endereco_input)
        areaInput = findViewById(R.id.area_input)
        btnSubmit = findViewById(R.id.btn_submit)
        btnDelete = findViewById(R.id.btn_delete)

        setupUi()
    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessFetchEmpresa.observe(this, Observer {
            mountForm(it)
        })

        viewModel.failFetchEmpresa.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.statusEditEmpresa.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this, "Empresa Editada com sucesso",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this, EmpresasAcitvity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this, "Não foi possivel editar a empresa",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun setupUi() {
        viewModel.getEmpresa(token, empresaId)
        handleEditSubmit()
//        handleDelete(empresaId)
    }

//    private fun handleDelete(empresaId: Int) {
//        btnDelete.setOnClickListener {
//
//        }
//    }

    private fun handleEditSubmit() {
        btnSubmit.setOnClickListener {
            val data = Empresa(
                nome = nomeInput.text.toString(),
                cnpj = cnpjInput.text.toString(),
                contato = contatoInput.text.toString(),
                endereco = enderecoInput.text.toString(),
                area_atuacao = areaInput.text.toString()
            )

            viewModel.editEmpresa(token, empresaId, data)
        }
    }

    private fun mountForm(empresa: Empresa) {
        nomeInput.setText(empresa.nome)
        cnpjInput.setText(empresa.cnpj)
        contatoInput.setText(empresa.contato)
        enderecoInput.setText(empresa.endereco)
        areaInput.setText(empresa.area_atuacao)
    }

}