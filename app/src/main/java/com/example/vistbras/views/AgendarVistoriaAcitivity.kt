package com.example.vistbras.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.adapters.EmpresaArrayAdapter
import com.example.vistbras.models.Empresa
import com.example.vistbras.models.UserSession
import com.example.vistbras.models.VistoriaAgendada
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.repositories.VistoriaAgendadaRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.agendar_vistoria.AgendarVistoriaViewModel
import com.example.vistbras.viewmodel.agendar_vistoria.AgendarVistoriaViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

class AgendarVistoriaAcitivity : AppCompatActivity() {
    private lateinit var viewModel: AgendarVistoriaViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var token: String

    private lateinit var options: Spinner
    private lateinit var calendarText: TextView
    private lateinit var contatoInput: EditText
    private lateinit var result: TextView
    private lateinit var btnLimpar: TextView
    private lateinit var btnSubmit: TextView
    private var selectedEmpresaId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_vistoria_acitivity)

        viewModel = ViewModelProvider(
            this,
            AgendarVistoriaViewModelFactory(
                VistoriaAgendadaRepository(retrofitService),
                EmpresaRepository(retrofitService)
            )
        ).get(
            AgendarVistoriaViewModel::class.java
        )
        token = UserSession.getToken()

        options = findViewById(R.id.sp_empresa)
        calendarText = findViewById(R.id.dt_agendada_input)
        contatoInput = findViewById(R.id.contato_input)
        result = findViewById(R.id.tv_result)
        btnLimpar = findViewById(R.id.btn_limpar)
        btnSubmit = findViewById(R.id.btnSubmit)

        setupUi()
    }

    override fun onStart() {
        super.onStart()

        viewModel.statusCreateAgendarVistoria.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Vistoria Agendada com sucesso", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Não conseguimos agendar vistoria", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.sucessGetEmpresas.observe(this, Observer {
            mountSelect(it)
        })
        viewModel.failGetEmpresas.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setupUi() {
        viewModel.getEmpresas(token)
        handleLimpar()
        handleSubmit()
        mountCalendar()
    }

    private fun handleLimpar() {
        btnLimpar.setOnClickListener {
            calendarText.text = ""
            contatoInput.setText("")
        }
    }

    private fun handleSubmit() {
        btnSubmit.setOnClickListener {
            val data = VistoriaAgendada(
                data_agendada = calendarText.text.toString(),
                contato = contatoInput.text.toString(),
                empresa = selectedEmpresaId!!
            )

            viewModel.createVistoriaAgendada(token, data)
        }
    }

    private fun mountSelect(empresas: List<Empresa>) {
        options.adapter = EmpresaArrayAdapter(this, empresas)

        options.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "Selecione a empresa que pertence o extintor"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val selectedEmpresa = empresas[position]
                val selectedId = selectedEmpresa.id
                val selectedNome = selectedEmpresa.nome

                Log.i("log_spinner", "ID: $selectedId, Nome: $selectedNome")
                selectedEmpresaId = selectedId
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mountCalendar() {
        calendarText.setOnClickListener {
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

                    calendarText.text = data.toString()
                }
        }
    }
}