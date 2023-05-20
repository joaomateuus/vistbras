package com.example.vistbras.viewmodel.editar_extintor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.ExtintoresRepository
import com.example.vistbras.viewmodel.cadastro_usuario.CadastroUsuarioViewModel

class ExtintorDetalheViewModelFactory constructor(private val repository: ExtintoresRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ExtintorDetalheViewModel::class.java)) {
            ExtintorDetalheViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}