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
import com.example.vistbras.adapters.VistoriaRealizadaItemAdapter
import com.example.vistbras.models.UserSession
import com.example.vistbras.models.VistoriaRealizada
import com.example.vistbras.repositories.VistoriaAgendadaRepository
import com.example.vistbras.repositories.VistoriaRealizadaRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.vistorias.VistoriaViewModel
import com.example.vistbras.viewmodel.vistorias.VistoriasViewModelFactory

class VistoriasRealizadasActivity : AppCompatActivity() {
    private lateinit var viewModel: VistoriaViewModel
    private lateinit var adapter: VistoriaRealizadaItemAdapter
    private lateinit var rvVistorias: RecyclerView
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vistorias_realizadas)

        viewModel = ViewModelProvider(
            this,
            VistoriasViewModelFactory(
                VistoriaAgendadaRepository(retrofitService),
                VistoriaRealizadaRepository(retrofitService)
            )
        ).get(
            VistoriaViewModel::class.java
        )

        rvVistorias = findViewById(R.id.rv_vistorias)
        token = UserSession.getToken()
        setupUi()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVistoriasRealizadas(token)
    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessGetVistoriasRealizadas.observe(this, Observer {
            updateVistoriasList(it)
        })
        viewModel.failGetVistoriasAgendadas.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun updateVistoriasList(vistorias: List<VistoriaRealizada>) {
        adapter.setVistoriasRealizadas(vistorias)

        rvVistorias.visibility = View.VISIBLE
    }

    private fun setupUi() {
        this.adapter = VistoriaRealizadaItemAdapter {
            val intent = Intent(this, VistoriaRealizadaActivity::class.java)
            intent.putExtra("vistoriaRealizadaId", it.id)
            startActivity(intent)
        }

        rvVistorias.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        rvVistorias.layoutManager = LinearLayoutManager(this@VistoriasRealizadasActivity)
        rvVistorias.adapter = this.adapter
    }
}