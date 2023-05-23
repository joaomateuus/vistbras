package com.example.vistbras.viewmodel.editar_empresa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.EmpresaRepository

class EmpresaDetalheViewModelFactory constructor(private val repository: EmpresaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EmpresaDetalheViewModel::class.java)) {
            EmpresaDetalheViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}