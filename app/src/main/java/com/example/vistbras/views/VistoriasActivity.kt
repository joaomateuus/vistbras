package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vistbras.R

class VistoriasActivity : AppCompatActivity() {
    private lateinit var btnAgendadas: View
    private lateinit var btnRealizadas: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vistorias)

        btnAgendadas = findViewById(R.id.btn_vist_agendadas)
        btnRealizadas = findViewById(R.id.btn_vist_realizadas)

        setupUi()
    }

    private fun setupUi() {
        btnAgendadas.setOnClickListener {
            startActivity(Intent(this, VistoriasAgendadasActivity::class.java))
        }
        btnRealizadas.setOnClickListener {
            startActivity(Intent(this, VistoriasRealizadasActivity::class.java))
        }
    }
}