package com.example.vistbras.viewmodel.fiscal_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.FiscalRepository

class FiscalHomeViewModelFactory constructor(
    private val repository: FiscalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FiscalHomeViewModel::class.java)) {
            FiscalHomeViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}