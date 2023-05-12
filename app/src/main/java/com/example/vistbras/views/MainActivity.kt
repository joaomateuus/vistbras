package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.vistbras.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val layoutLogin = findViewById<View>(R.id.login_container)
        val layoutSenha = findViewById<View>(R.id.senha_container)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        val cadastreUsuario = findViewById<TextView>(R.id.cadastre_usuario)
        val cadastreFiscal = findViewById<TextView>(R.id.cadastro_fiscal)
        val botaoEntrar = findViewById<RelativeLayout>(R.id.entrar_)


        cadastreUsuario.setOnClickListener {
            startActivity(Intent(this, CadastroUsuarioActivity::class.java))
        }

        cadastreFiscal.setOnClickListener {
            startActivity(Intent(this, CadastroFiscalActivity::class.java))
        }

        botaoEntrar.setOnClickListener {
            Log.i("botao", "Entrei no botao de entrada")
            Log.i("botao", username.text.toString())
            Log.i("botao", password.text.toString())
            startActivity(Intent(this, HomeFiscalActivity::class.java))

        }


        username.setOnClickListener {
            username.getText().clear()
        }

        password.setOnClickListener {
            password.getText().clear()
        }
    }
}