package com.example.vistbras.viewmodel.agendar_vistoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.EmpresaRepository
import com.example.vistbras.repositories.VistoriaAgendadaRepository

class AgendarVistoriaViewModelFactory constructor(
    private val repository: VistoriaAgendadaRepository,
    private val empresaRepository: EmpresaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AgendarVistoriaViewModel::class.java)) {
            AgendarVistoriaViewModel(this.repository, this.empresaRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}