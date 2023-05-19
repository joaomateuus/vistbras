package com.example.vistbras.viewmodel.cadastro_extintor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.repositories.ExtintoresRepository
import com.example.vistbras.repositories.UserRepository
import com.example.vistbras.viewmodel.cadastro_usuario.CadastroUsuarioViewModel

class CadastroExtintorViewModelFactory(
    private val repository: ExtintoresRepository,
    private val empresaRepository: EmpresaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CadastroExtintorViewModel::class.java)) {
            CadastroExtintorViewModel(this.repository, this.empresaRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}