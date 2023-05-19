package com.example.vistbras.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.repositories.ExtintoresRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.viewmodel.cadastro_extintor.CadastroExtintorViewModel
import com.example.vistbras.viewmodel.cadastro_extintor.CadastroExtintorViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

class CadastroExtintorActivity : AppCompatActivity() {
    private lateinit var viewModel: CadastroExtintorViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var token: String

    private var selectedEmpresaId: Int? = null
    private lateinit var option: Spinner
    private lateinit var result: TextView
    private lateinit var calendarView: TextView
    private lateinit var codigoInput: EditText
    private lateinit var nivel: EditText
    private lateinit var tipo: EditText
    private lateinit var tamanho: EditText
    private lateinit var local: EditText

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_extintor)

        option = findViewById(R.id.sp_empresa)
        result = findViewById(R.id.tv_result)
        calendarView = findViewById(R.id.dt_validade_input)
        codigoInput = findViewById(R.id.codigo_input)
        nivel = findViewById(R.id.nivel_input)
        tipo = findViewById(R.id.tipo_input)
        tamanho = findViewById(R.id.tamanho_input)
        local = findViewById(R.id.local_input)

        viewModel = ViewModelProvider(
            this,
            CadastroExtintorViewModelFactory(
                ExtintoresRepository(retrofitService),
                EmpresaRepository(retrofitService)
            )
        ).get(
            CadastroExtintorViewModel::class.java
        )
        token = UserSession.getToken()

        setupUi()
    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessFetchEmpresas.observe(this, Observer {
            mountSelect(it)
        })
        viewModel.errorMessageFetchEmpresas.observe(this, Observer {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_LONG
            ).show()
        })
    }

    private fun mountSelect(empresas: List<Empresa>) {
        option.adapter = EmpresaArrayAdapter(this, empresas)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupUi() {
        viewModel.getEmpresas(token)

        mountCalendar()
    }
}