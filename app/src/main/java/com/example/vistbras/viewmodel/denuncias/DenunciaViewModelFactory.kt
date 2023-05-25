package com.example.vistbras.viewmodel.denuncias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.DenunciaRepository
import com.example.vistbras.viewmodel.cadastro_extintor.CadastroExtintorViewModel

class DenunciaViewModelFactory constructor(private val repository: DenunciaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DenunciaViewModel::class.java)) {
            DenunciaViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}