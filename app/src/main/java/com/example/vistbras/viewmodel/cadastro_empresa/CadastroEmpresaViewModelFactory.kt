package com.example.vistbras.viewmodel.cadastro_empresa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.viewmodel.cadastro_extintor.CadastroExtintorViewModel

class CadastroEmpresaViewModelFactory constructor(private val repository: EmpresaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CadastroEmpresaViewModel::class.java)) {
            CadastroEmpresaViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}