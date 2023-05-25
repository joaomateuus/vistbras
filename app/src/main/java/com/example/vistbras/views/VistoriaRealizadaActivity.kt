package com.example.vistbras.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vistbras.R
import com.example.vistbras.models.FiscalRequest
import com.example.vistbras.models.User
import com.example.vistbras.models.UserSession
import com.example.vistbras.models.VistoriaRealizada
import com.example.vistbras.repositories.VistoriaRealizadaRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.store.LoggedUserSession
import com.example.vistbras.viewmodel.vistoria_realizada.VistoriaRealizadaViewModel
import com.example.vistbras.viewmodel.vistoria_realizada.VistoriaRealizadaViewModelFactory

class VistoriaRealizadaActivity : AppCompatActivity() {
    private lateinit var token: String
    private lateinit var viewModel: VistoriaRealizadaViewModel
    private val retrofitService = RetrofitService.getInstance()
    private var fiscal: FiscalRequest? = null
    private var user: User? = null

    private lateinit var dtInspecaoText: TextView
    private lateinit var fiscalizadorText: TextView

    private lateinit var imageAnexo: ImageView

    private lateinit var bocalQuestion: TextView
    private lateinit var indicadorQuestion: TextView
    private lateinit var pinoQuestion: TextView
    private lateinit var localQuestion: TextView

    private lateinit var informacoesInput: TextView
    private lateinit var comentariosInput: TextView
    private lateinit var resultadoInput: TextView
    private lateinit var imgAnexo: ImageView
    var vistoriaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vistoria_realizada)

        viewModel = ViewModelProvider(
            this,
            VistoriaRealizadaViewModelFactory(
                VistoriaRealizadaRepository(retrofitService)
            )
        ).get(
            VistoriaRealizadaViewModel::class.java
        )
        vistoriaId = intent.getIntExtra("vistoriaRealizadaId", 0)
        dtInspecaoText = findViewById(R.id.dt_inspecao_text)
        fiscalizadorText = findViewById(R.id.fiscal_nome_text)
        bocalQuestion = findViewById(R.id.first_question)
        indicadorQuestion = findViewById(R.id.second_question)
        pinoQuestion = findViewById(R.id.third_question)
        localQuestion = findViewById(R.id.fourth_question)
        imageAnexo = findViewById(R.id.img_anexo)
        informacoesInput = findViewById(R.id.informacoes_input)
        comentariosInput = findViewById(R.id.comentarios_input)
        resultadoInput = findViewById(R.id.resultado_input)
        imageAnexo = findViewById(R.id.img_anexo)

        token = UserSession.getToken()
        fiscal = LoggedUserSession.getFiscal()
        user = LoggedUserSession.getUser()

        setupUi()
    }

    private fun setupUi() {
        viewModel.getVistoriaRealizada(token, vistoriaId)
    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessGetVistoria.observe(this, Observer {
            mountUi(it)
        })
        viewModel.failGetVistoria.observe(this, Observer {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_LONG
            ).show()
        })
    }

    private fun mountUi(vistoria: VistoriaRealizada) {
        dtInspecaoText.text = vistoria.data_realizada
        fiscalizadorText.text = vistoria.fiscal_nome
        bocalQuestion.text = if (vistoria.bocal_vedado) "Sim" else "Não"
        indicadorQuestion.text = if (vistoria.indicador_pressao) "Sim" else "Não"
        pinoQuestion.text = if (vistoria.pino_seguranca) "Sim" else "Não"
        localQuestion.text = if (vistoria.local_acesso) "Sim" else "Não"
        comentariosInput.text = vistoria.comentarios
        informacoesInput.text = vistoria.informacoes
        resultadoInput.text = vistoria.resultado
        val anexo = vistoria.anexos_img

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.no_image)
            .error(R.drawable.no_image)

        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load("http://10.0.2.2:8000/$anexo/")
            .into(imageAnexo)
    }


}