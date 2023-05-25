package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.vistbras.R
import com.example.vistbras.models.User
import com.example.vistbras.models.UserSession
import com.example.vistbras.store.LoggedUserSession

class HomeUsuarioActivity : AppCompatActivity() {
    private lateinit var btnCadastrarDenuncia: View
    private lateinit var btnDenuncias: View
    private lateinit var btnLogout: View
    private lateinit var nomeUser: TextView
    private lateinit var emailUser: TextView
    private lateinit var user: User
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_usuario)

        token = UserSession.getToken()
        user = LoggedUserSession.getUser()!!
        btnCadastrarDenuncia = findViewById(R.id.btn_cadastrar_denun)
        nomeUser = findViewById(R.id.nome_user_text)
        emailUser = findViewById(R.id.email_usuario_text)
        btnDenuncias = findViewById(R.id.btn_denuncias)
        btnLogout = findViewById(R.id.btn_logout)

        mountHeader(user)
        setupUi()
    }

    private fun setupUi() {
        btnCadastrarDenuncia.setOnClickListener {
            startActivity(Intent(this, CadastrarDenunciaActivity::class.java))
        }
        btnDenuncias.setOnClickListener {
            startActivity(Intent(this, DenunciaActivity::class.java))
        }
        btnLogout.setOnClickListener {
            UserSession.cleanSession()
            LoggedUserSession.clearUser()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun mountHeader(user: User) {
        nomeUser.text = user.nome
        emailUser.text = user.email ?: user.cpf
    }
}