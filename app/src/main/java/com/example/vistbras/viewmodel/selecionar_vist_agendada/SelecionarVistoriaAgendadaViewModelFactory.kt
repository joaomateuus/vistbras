package com.example.vistbras.viewmodel.selecionar_vist_agendada

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.FiscalRepository
import com.example.vistbras.viewmodel.fiscal_home.FiscalHomeViewModel

class SelecionarVistoriaAgendadaViewModelFactory constructor(
    private val repository: FiscalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SelecionarVistoriaAgendadaViewModel::class.java)) {
            SelecionarVistoriaAgendadaViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}