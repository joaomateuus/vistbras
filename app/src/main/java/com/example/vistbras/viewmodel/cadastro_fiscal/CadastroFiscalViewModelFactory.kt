package com.example.vistbras.viewmodel.cadastro_fiscal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.UserRepository

class CadastroFiscalViewModelFactory constructor(private val repository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CadastroFiscalViewModel::class.java)) {
            CadastroFiscalViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}