package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vistbras.R
import com.example.vistbras.adapters.EmpresaItemAdapter
import com.example.vistbras.models.Empresa
import com.example.vistbras.models.Empresas
import com.example.vistbras.models.UserSession
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.empresas.EmpresasViewModel
import com.example.vistbras.viewmodel.empresas.EmpresasViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EmpresasAcitvity : AppCompatActivity() {
    private lateinit var viewModel: EmpresasViewModel
    private lateinit var token: String
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var noEmpresasImg: ImageView
    private lateinit var noEmpresasText: TextView
    private lateinit var arrowNewEmpresa: ImageView
    private lateinit var adapter: EmpresaItemAdapter
    private lateinit var rvEmpresas: RecyclerView
    private lateinit var btnCreateEmpresa: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresas_acitvity)

        viewModel = ViewModelProvider(
            this,
            EmpresasViewModelFactory(EmpresaRepository(retrofitService))
        ).get(
            EmpresasViewModel::class.java
        )

        token = UserSession.getToken()
        rvEmpresas = findViewById(R.id.rv_empresas)
        btnCreateEmpresa = findViewById(R.id.fab_new_empresa)
        noEmpresasImg = findViewById(R.id.img_no_empresas)
        noEmpresasText = findViewById(R.id.tv_no_empresas)
        arrowNewEmpresa = findViewById(R.id.img_arrow)

        setupUi()

    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessGetEmpresas.observe(this, Observer {
            updateEmpresasList(it)
        })

        viewModel.failGetEmpresas.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEmpresas(token)
    }

    private fun setupUi() {
        btnCreateEmpresa.setOnClickListener {
            startActivity(Intent(this, CadastroEmpresaActivity::class.java))
        }

        this.adapter = EmpresaItemAdapter {
            Log.i("empresa", it.nome)
        }

        rvEmpresas.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        rvEmpresas.layoutManager = LinearLayoutManager(this@EmpresasAcitvity)
        rvEmpresas.adapter = this.adapter
    }

    private fun updateEmpresasList(empresas: List<Empresa>?) {
        adapter.setEmpresas(empresas)

        rvEmpresas.visibility = View.VISIBLE
        noEmpresasImg.visibility = View.GONE
        noEmpresasText.visibility = View.GONE
        arrowNewEmpresa.visibility = View.GONE
    }
}