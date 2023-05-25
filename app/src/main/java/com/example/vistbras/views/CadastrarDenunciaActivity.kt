package com.example.vistbras.views

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.R
import com.example.vistbras.models.Denuncia
import com.example.vistbras.models.User
import com.example.vistbras.models.UserSession
import com.example.vistbras.repositories.DenunciaRepository
import com.example.vistbras.rest.RetrofitService
import com.example.vistbras.store.LoggedUserSession
import com.example.vistbras.viewmodel.cadastro_denuncia.CadastroDenunciaViewModel
import com.example.vistbras.viewmodel.cadastro_denuncia.CadastroDenunciaViewModelFactory
import java.io.ByteArrayOutputStream

private const val REQUEST_IMAGE_CAPTURE = 1

class CadastrarDenunciaActivity : AppCompatActivity() {
    private lateinit var viewModel: CadastroDenunciaViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var descricaoInput: EditText
    private lateinit var localInput: EditText
    private lateinit var anexoImg: ImageView
    private lateinit var btnPicture: TextView
    private lateinit var btnSubmit: TextView
    private var anexoJPEG: String? = null
    private var user: User? = null
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_denuncia)

        viewModel = ViewModelProvider(
            this, CadastroDenunciaViewModelFactory(DenunciaRepository(retrofitService))
        ).get(
            CadastroDenunciaViewModel::class.java
        )
        token = UserSession.getToken()
        user = LoggedUserSession.getUser()
        descricaoInput = findViewById(R.id.descricao_input)
        localInput = findViewById(R.id.ocorrencia_input)
        anexoImg = findViewById(R.id.img_anexo)
        btnPicture = findViewById(R.id.btn_picture)
        btnSubmit = findViewById(R.id.btn_submit)

        setupUi()
    }


    override fun onStart() {
        super.onStart()

        viewModel.statusCreateDenuncia.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this,
                    "Denuncia feita com sucesso!!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Não foi possivel criar a denuncia",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun setupTakePic() {
        btnPicture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            anexoImg.setImageBitmap(imageBitmap)

            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)

            this.anexoJPEG = imageBase64
        }
    }

    private fun setupUi() {
        setupTakePic()
        handleSubmit()
    }

    private fun handleSubmit() {
        btnSubmit.setOnClickListener {
            val data = Denuncia(
                usuario = user?.id!!,
                local_ocorrencia = localInput.text.toString(),
                descricao = localInput.text.toString(),
                anexo = anexoJPEG
            )

            viewModel.createDenuncia(token, data)
        }
    }
}