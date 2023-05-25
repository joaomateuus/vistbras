package com.example.vistbras.viewmodel.cadastro_denuncia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.DenunciaRepository
import com.example.vistbras.viewmodel.cadastro_extintor.CadastroExtintorViewModel

class CadastroDenunciaViewModelFactory constructor(private val repository: DenunciaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CadastroDenunciaViewModel::class.java)) {
            CadastroDenunciaViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}