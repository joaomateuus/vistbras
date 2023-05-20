package com.example.vistbras.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.models.FiscalRequest
import com.example.vistbras.models.User
import com.example.vistbras.models.UserSession
import com.example.vistbras.models.Vistoria
import com.example.vistbras.repositories.FiscalRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.store.LoggedUserSession
import com.example.vistbras.viewmodel.fiscal_home.FiscalHomeViewModel
import com.example.vistbras.viewmodel.fiscal_home.FiscalHomeViewModelFactory

class HomeFiscalActivity : AppCompatActivity() {
    private lateinit var viewModel: FiscalHomeViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var token: String
    private lateinit var user: User

    private lateinit var tableVistoria: TableLayout
    private lateinit var nomeFiscal: TextView
    private lateinit var empresaFiscal: TextView
    private lateinit var codigoFiscal: TextView
    private lateinit var btnExtintores: TextView
    private lateinit var btnEmpresas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_fiscal)

        token = UserSession.getToken()
        user = LoggedUserSession.getUser()!!
        tableVistoria = findViewById(R.id.table_vistoria)
        nomeFiscal = findViewById(R.id.nome_fiscal)
        empresaFiscal = findViewById(R.id.empresa_fiscal)
        codigoFiscal = findViewById(R.id.cod_fiscal)
        btnExtintores = findViewById(R.id.btn_extintores)
        btnEmpresas = findViewById(R.id.btn_empresas)

        viewModel = ViewModelProvider(
            this, FiscalHomeViewModelFactory(FiscalRepository(retrofitService))
        ).get(
            FiscalHomeViewModel::class.java
        )

        setupUi()
    }

    override fun onStart() {
        super.onStart()

        viewModel.sucessGetLoggedFiscal.observe(this, Observer {
            mountHeader(it)
        })
        viewModel.sucessGetVistorias.observe(this, Observer {
            mountTable(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_LONG
            ).show()
        })

        viewModel.errorMessageFetchFiscal.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_LONG
            ).show()
        })
    }

    fun mountTable(data: List<Vistoria>) {
        val rowCount = tableVistoria.childCount
        for (i in rowCount - 1 downTo 1) {
            tableVistoria.removeViewAt(i)
        }

        for (i in data) {
            val tableRow = TableRow(this)

            val empresa = TextView(this)
            empresa.text = if (i.empresa == null) "-" else i.empresa
            val data = TextView(this)
            data.text = if (i.data_agendada == null) "-" else i.data_agendada

            tableRow.addView(empresa)
            tableRow.addView(data)
            tableVistoria.addView(tableRow)
        }
    }

    fun mountHeader(data: FiscalRequest) {
        nomeFiscal.text = user?.nome ?: ""
        empresaFiscal.text = data.empresa_fiscal
        codigoFiscal.text = data.codigo_fiscal
    }

    fun setupUi() {
        viewModel.getVistorias(token)
        viewModel.getLoggedFiscal(token, user.id)

        btnExtintores.setOnClickListener {
            startActivity(Intent(this, ExtintoresActivity::class.java))
        }

        btnEmpresas.setOnClickListener {
            startActivity(Intent(this, EmpresasAcitvity::class.java))
        }
    }
}