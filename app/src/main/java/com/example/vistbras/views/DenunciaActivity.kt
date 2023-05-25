package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vistbras.R
import com.example.vistbras.adapters.DenunciaItemAdapter
import com.example.vistbras.adapters.VistoriaAgendadaItemAdapter
import com.example.vistbras.models.Denuncia
import com.example.vistbras.repositories.DenunciaRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.denuncias.DenunciaViewModel
import com.example.vistbras.viewmodel.denuncias.DenunciaViewModelFactory

class DenunciaActivity : AppCompatActivity() {
    private lateinit var adapter: DenunciaItemAdapter
    private lateinit var rvDenuncias: RecyclerView
    private lateinit var viewModel: DenunciaViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_denuncia)

        viewModel = ViewModelProvider(
            this, DenunciaViewModelFactory(DenunciaRepository(retrofitService))
        ).get(
            DenunciaViewModel::class.java
        )

        this.adapter = DenunciaItemAdapter {
           Log.i("id", it.nome_usuario.toString())
        }

        rvDenuncias = findViewById(R.id.rv_denuncias)
        rvDenuncias.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        rvDenuncias.layoutManager = LinearLayoutManager(this@DenunciaActivity)
        rvDenuncias.adapter = this.adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDenuncias()
    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessGetDenuncias.observe(this, Observer {
            updateDenunciasList(it)
        })
        viewModel.failGetDenuncias.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }


    private fun updateDenunciasList(denuncias: List<Denuncia>) {
        adapter.setDenuncias(denuncias)

        rvDenuncias.visibility = View.VISIBLE
    }
}