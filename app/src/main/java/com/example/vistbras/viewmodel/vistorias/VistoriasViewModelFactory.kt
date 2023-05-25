package com.example.vistbras.viewmodel.vistorias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.VistoriaAgendadaRepository
import com.example.vistbras.repositories.VistoriaRealizadaRepository

class VistoriasViewModelFactory constructor(
    private val vistoriaAgendadaRepository: VistoriaAgendadaRepository,
    private val vistoriaRealizadaRepository: VistoriaRealizadaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(VistoriaViewModel::class.java)) {
            VistoriaViewModel(
                this.vistoriaAgendadaRepository,
                this.vistoriaRealizadaRepository
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}