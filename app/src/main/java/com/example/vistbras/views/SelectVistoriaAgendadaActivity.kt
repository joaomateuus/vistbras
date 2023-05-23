package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vistbras.R
import com.example.vistbras.adapters.VistoriaAgendadaItemAdapter
import com.example.vistbras.models.UserSession
import com.example.vistbras.models.VistoriaAgendada
import com.example.vistbras.repositories.FiscalRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.store.LoggedUserSession
import com.example.vistbras.viewmodel.selecionar_vist_agendada.SelecionarVistoriaAgendadaViewModel
import com.example.vistbras.viewmodel.selecionar_vist_agendada.SelecionarVistoriaAgendadaViewModelFactory

class SelectVistoriaAgendadaActivity : AppCompatActivity() {
    private lateinit var viewModel: SelecionarVistoriaAgendadaViewModel
    private lateinit var adapter: VistoriaAgendadaItemAdapter
    private lateinit var token: String
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var rvVistorias: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_vistoria_agendada_acitivty)

        viewModel = ViewModelProvider(
            this, SelecionarVistoriaAgendadaViewModelFactory(FiscalRepository(retrofitService))
        ).get(
            SelecionarVistoriaAgendadaViewModel::class.java
        )

        rvVistorias = findViewById(R.id.rv_vistorias)
        token = UserSession.getToken()

        setupUi()

    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessGetVistorias.observe(this, Observer {
            updateVistoriasList(it)
        })

        viewModel.failGetVistorias.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVistoriasAgendadas(token)
    }

    private fun setupUi() {
        this.adapter = VistoriaAgendadaItemAdapter {
            val intent = Intent(this, RealizarVistoriaActivity::class.java)
            intent.putExtra("vistoriaAgendadaId", it.id)
            startActivity(intent)
        }

        rvVistorias.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        rvVistorias.layoutManager = LinearLayoutManager(this@SelectVistoriaAgendadaActivity)
        rvVistorias.adapter = this.adapter
    }

    private fun updateVistoriasList(vistorias: List<VistoriaAgendada>) {
        adapter.setVistoriasAgendadas(vistorias)

        rvVistorias.visibility = View.VISIBLE
    }
}