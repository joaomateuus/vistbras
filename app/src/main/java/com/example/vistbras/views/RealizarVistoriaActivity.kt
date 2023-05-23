package com.example.vistbras.views

import ExtintorArrayAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.models.Extintores
import com.example.vistbras.models.FiscalRequest
import com.example.vistbras.models.User
import com.example.vistbras.models.UserSession
import com.example.vistbras.models.VistoriaRealizada
import com.example.vistbras.repositories.ExtintoresRepository
import com.example.vistbras.repositories.VistoriaRealizadaRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.store.LoggedUserSession
import com.example.vistbras.viewmodel.realizar_vistoria.RealizarVistoriaViewModel
import com.example.vistbras.viewmodel.realizar_vistoria.RealizarVistoriaViewModelFactory
import java.io.ByteArrayOutputStream
import java.time.LocalDate

private const val REQUEST_IMAGE_CAPTURE = 1

class RealizarVistoriaActivity : AppCompatActivity() {
    private lateinit var viewModel: RealizarVistoriaViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var token: String
    private var fiscal: FiscalRequest? = null
    private var user: User? = null

    private lateinit var dtInspecaoText: TextView
    private lateinit var fiscalizadorText: TextView

    private lateinit var btnTakePicture: View
    private lateinit var imageAnexo: ImageView

    //    private lateinit var bocalQuestion: TextView
    private lateinit var cbYesBocal: CheckBox
    private lateinit var cbNoBocal: CheckBox

    //    private lateinit var indicadorQuestion: TextView
    private lateinit var cbYesIndicador: CheckBox
    private lateinit var cbNoIndicador: CheckBox

    //    private lateinit var pinoQuestion: TextView
    private lateinit var cbYesPino: CheckBox
    private lateinit var cbNoPino: CheckBox

    //    private lateinit var localQuestion: TextView
    private lateinit var cbYesLocal: CheckBox
    private lateinit var cbNoLocal: CheckBox
    private lateinit var option: Spinner
    private lateinit var result: TextView
    private lateinit var informacoesInput: EditText
    private lateinit var comentariosInput: EditText
    private lateinit var resultadoInput: EditText
    private lateinit var btnSubmit: View
    private var extintorId: Int = 0
    private var vistoriaAgendadaId: Int = 0
    private var anexoJPEG: ByteArray? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_vistoria)


        dtInspecaoText = findViewById(R.id.dt_inspecao_text)
        fiscalizadorText = findViewById(R.id.fiscal_nome_text)
        cbYesBocal = findViewById(R.id.cb_yes_bocal)
        cbNoBocal = findViewById(R.id.cb_no_bocal)
        cbYesIndicador = findViewById(R.id.cb_yes_indicadorpress)
        cbNoIndicador = findViewById(R.id.cb_no_indicadorpress)
        cbYesPino = findViewById(R.id.cb_yes_pino)
        cbNoPino = findViewById(R.id.cb_no_pino)
        cbYesLocal = findViewById(R.id.cb_yes_local)
        cbNoLocal = findViewById(R.id.cb_no_local)
        btnTakePicture = findViewById(R.id.btn_picture)
        imageAnexo = findViewById(R.id.img_anexo)
        option = findViewById(R.id.sp_extintor)
        result = findViewById(R.id.tv_result)
        informacoesInput = findViewById(R.id.informacoes_input)
        comentariosInput = findViewById(R.id.comentarios_input)
        resultadoInput = findViewById(R.id.resultado_input)
        btnSubmit = findViewById(R.id.btn_submit)
        token = UserSession.getToken()
        fiscal = LoggedUserSession.getFiscal()
        user = LoggedUserSession.getUser()

        viewModel = ViewModelProvider(
            this,
            RealizarVistoriaViewModelFactory(
                VistoriaRealizadaRepository(retrofitService),
                ExtintoresRepository(retrofitService)
            )
        ).get(
            RealizarVistoriaViewModel::class.java
        )

        vistoriaAgendadaId = intent.getIntExtra("vistoriaAgendadaId", 0)



        setupUi()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupUi() {
        viewModel.getExtintores(token)
        fiscalizadorText.text = user?.nome
        dtInspecaoText.text = LocalDate.now().toString()
        handleCheckBoxes()
        setupTakePic()
        handleSubmit()
    }

    override fun onStart() {
        super.onStart()

        viewModel.extintores.observe(this, Observer {
            mountExtinguishersSelect(it)
        })

        viewModel.failExtintores.observe(this, Observer {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_LONG
            ).show()
        })

        viewModel.statusCreateVistoria.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this,
                    "Vistoria realizada com sucesso",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Erro ao realizar vistoria.png.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun handleCheckBoxes() {
        cbYesBocal.setOnClickListener {
            cbNoBocal.isChecked = false
        }
        cbNoBocal.setOnClickListener {
            cbYesBocal.isChecked = false
        }
        cbYesIndicador.setOnClickListener {
            cbNoIndicador.isChecked = false
        }
        cbNoIndicador.setOnClickListener {
            cbYesIndicador.isChecked = false
        }
        cbYesPino.setOnClickListener {
            cbNoPino.isChecked = false
        }
        cbNoPino.setOnClickListener {
            cbYesPino.isChecked = false
        }
        cbYesLocal.setOnClickListener {
            cbNoLocal.isChecked = false
        }
        cbNoLocal.setOnClickListener {
            cbYesLocal.isChecked = false
        }
    }

    private fun setupTakePic() {
        btnTakePicture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageAnexo.setImageBitmap(imageBitmap)

            this.anexoJPEG = bitmapToJPEGByteArray(imageBitmap)
        }
    }

    private fun bitmapToJPEGByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        return outputStream.toByteArray()
    }


    private fun mountExtinguishersSelect(extintores: Extintores) {
        option.adapter = ExtintorArrayAdapter(this, extintores)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "Selecione o extintor"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedExtintor = extintores[position]
                val selectedId = selectedExtintor.id
                val selectedEmpresaNome = selectedExtintor.empresa_nome

                Log.i("log_spinner", "ID: $selectedId, Nome Empresa: $selectedEmpresaNome")
                extintorId = selectedExtintor.id

            }
        }
    }

    private fun handleSubmit() {
        btnSubmit.setOnClickListener {
            val data = VistoriaRealizada(
                vistoria_agendada = vistoriaAgendadaId,
                bocal_vedado = cbYesBocal.isChecked,
                indicador_pressao = cbYesIndicador.isChecked,
                pino_seguranca = cbYesPino.isChecked,
                local_acesso = cbYesLocal.isChecked,
                informacoes = informacoesInput.text.toString(),
                comentarios = comentariosInput.text.toString(),
                resultado = resultadoInput.text.toString(),
                extintor = extintorId,
                fiscal = fiscal!!.id,
//                anexos_img = this.anexoJPEG
            )

            viewModel.createVistoria(token, data)
        }
    }
}