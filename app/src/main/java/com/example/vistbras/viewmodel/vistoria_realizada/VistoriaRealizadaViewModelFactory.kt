package com.example.vistbras.viewmodel.vistoria_realizada

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.VistoriaRealizadaRepository
import com.example.vistbras.viewmodel.cadastro_extintor.CadastroExtintorViewModel

class VistoriaRealizadaViewModelFactory constructor(private val repository: VistoriaRealizadaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(VistoriaRealizadaViewModel::class.java)) {
            VistoriaRealizadaViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}