package com.example.vistbras.viewmodel.realizar_vistoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.ExtintoresRepository
import com.example.vistbras.repositories.VistoriaRealizadaRepository
import com.example.vistbras.viewmodel.cadastro_extintor.CadastroExtintorViewModel

class RealizarVistoriaViewModelFactory constructor(
    private val repository: VistoriaRealizadaRepository,
    private val extintoresRepository: ExtintoresRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RealizarVistoriaViewModel::class.java)) {
            RealizarVistoriaViewModel(this.repository, this.extintoresRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }


    }
}