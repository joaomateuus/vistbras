package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.vistbras.adapters.ExtintorItemAdapter
import com.example.vistbras.models.Extintores
import com.example.vistbras.models.UserSession
import com.example.vistbras.repositories.ExtintoresRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.extintores.ExtintoresViewModel
import com.example.vistbras.viewmodel.extintores.ExtintoresViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExtintoresActivity : AppCompatActivity() {
    private lateinit var viewModel: ExtintoresViewModel
    private lateinit var token: String
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var noExtintoresImg: ImageView
    private lateinit var noExtintoresText: TextView
    private lateinit var arrowNewExtintor: ImageView
    private lateinit var adapter: ExtintorItemAdapter
    private lateinit var rvExtintores: RecyclerView
    private lateinit var btnCreateExtintor: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extintores)

        viewModel = ViewModelProvider(
            this,
            ExtintoresViewModelFactory(ExtintoresRepository(retrofitService))
        ).get(
            ExtintoresViewModel::class.java
        )

        token = UserSession.getToken()
        rvExtintores = findViewById(R.id.rv_extintores)
        btnCreateExtintor = findViewById(R.id.fab_new_extintor)
        noExtintoresImg = findViewById(R.id.img_no_extintores)
        noExtintoresText = findViewById(R.id.tv_no_extintores)
        arrowNewExtintor = findViewById(R.id.img_arrow)

        setupUi()
    }

    override fun onStart() {
        super.onStart()

        viewModel.extintores.observe(this, Observer {
            updateExtintoresList(it)
        })

        viewModel.errorMessageExtintores.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getExtintores(token)
    }

    private fun setupUi() {
        btnCreateExtintor.setOnClickListener {
            startActivity(Intent(this, CadastroExtintorActivity::class.java))
        }

        this.adapter = ExtintorItemAdapter {
            val intent = Intent(this, ExtintorDetalheActivity::class.java)
            intent.putExtra("extintorId", it.id)
            startActivity(intent)
        }

        rvExtintores.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        rvExtintores.layoutManager = LinearLayoutManager(this@ExtintoresActivity)
        rvExtintores.adapter = this.adapter
    }

    fun updateExtintoresList(extintores: Extintores?) {
        adapter.setExtintores(extintores)

        rvExtintores.visibility = View.VISIBLE
        noExtintoresImg.visibility = View.GONE
        noExtintoresText.visibility = View.GONE
        arrowNewExtintor.visibility = View.GONE
    }
}