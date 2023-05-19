package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vistbras.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExtintoresActivity : AppCompatActivity() {
    private lateinit var btnCreateExtintor: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extintores)

        btnCreateExtintor = findViewById(R.id.fab_new_extintor)
    }

    override fun onStart() {
        super.onStart()
        btnCreateExtintor.setOnClickListener {
            startActivity(Intent(this, CadastroExtintorActivity::class.java))
        }
    }
}