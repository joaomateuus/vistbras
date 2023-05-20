package com.example.vistbras.views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.models.ExtintorItem
import com.example.vistbras.models.ExtintorItemResponse
import com.example.vistbras.models.UserSession
import com.example.vistbras.repositories.ExtintoresRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.editar_extintor.ExtintorDetalheViewModel
import com.example.vistbras.viewmodel.editar_extintor.ExtintorDetalheViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

class ExtintorDetalheActivity : AppCompatActivity() {
    private lateinit var viewModel: ExtintorDetalheViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var token: String

    private var extintorId: Int = 0

    private lateinit var calendarView: TextView
    private lateinit var codigoInput: EditText
    private lateinit var nivelInput: EditText
    private lateinit var tipoInput: EditText
    private lateinit var tamanhoInput: EditText
    private lateinit var localInput: EditText
    private lateinit var btnSubmit: TextView
    private lateinit var btnDelete: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extintor_detalhe)

        viewModel = ViewModelProvider(
            this, ExtintorDetalheViewModelFactory(ExtintoresRepository(retrofitService))
        ).get(
            ExtintorDetalheViewModel::class.java
        )
        token = UserSession.getToken()
        extintorId = intent.getIntExtra("extintorId", 0)
        calendarView = findViewById(R.id.dt_validade_input)
        codigoInput = findViewById(R.id.codigo_input)
        nivelInput = findViewById(R.id.nivel_input)
        tipoInput = findViewById(R.id.tipo_input)
        tamanhoInput = findViewById(R.id.tamanho_input)
        localInput = findViewById(R.id.local_input)
        btnSubmit = findViewById(R.id.btn_submit)
        btnDelete = findViewById(R.id.btn_delete)

        setupUi()
    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessGetExtintor.observe(this, Observer {
            mountForm(it)
        })
        viewModel.failGetExtintor.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.statusEditExtintor.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Extintor editado com sucesso", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ExtintoresActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Não foi possivel editar o extintor tente novamente",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupUi() {
        viewModel.getExtintor(token, extintorId)
        saveFormChanges()
        deleteExtintor()
        mountCalendar()
    }

    private fun mountForm(extintor: ExtintorItemResponse) {
        calendarView.text = extintor.data_validade
        codigoInput.setText(extintor.codigo)
        nivelInput.setText(extintor.nivel)
        tipoInput.setText(extintor.tipo)
        tamanhoInput.setText(extintor.tamanho)
        localInput.setText(extintor.local)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mountCalendar() {
        calendarView.setOnClickListener {
            val selecionadorDeData = MaterialDatePicker
                .Builder.datePicker().build()

            selecionadorDeData.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

            selecionadorDeData
                .addOnPositiveButtonClickListener { dataEmMilisegundos ->
                    val data = Instant.ofEpochMilli(dataEmMilisegundos)
                        .atZone(ZoneId.of("America/Sao_Paulo"))
                        .withZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.UTC))
                        .toLocalDate()
                    Log.i("MaterialDatePicker", "data com LocalDate: $data")
                    Log.i("MaterialDatePicker", "data em milisegundos: $dataEmMilisegundos")

                    calendarView.text = data.toString()
                }
        }
    }

    private fun saveFormChanges() {
        btnSubmit.setOnClickListener {
            val data = ExtintorItem(
                data_validade = calendarView.text.toString(),
                codigo = codigoInput.text.toString(),
                nivel = nivelInput.text.toString(),
                tipo = tipoInput.text.toString(),
                tamanho = tamanhoInput.text.toString(),
                local = localInput.text.toString(),
                termo_garantia = true
            )

            viewModel.editExtintor(token, extintorId, data)

        }
    }

    private fun deleteExtintor() {
        btnDelete.setOnClickListener {
        }
    }
}