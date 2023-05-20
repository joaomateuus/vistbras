package com.example.vistbras.viewmodel.extintores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vistbras.repositories.ExtintoresRepository


class ExtintoresViewModelFactory constructor(
    private val repository: ExtintoresRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ExtintoresViewModel::class.java)) {
            ExtintoresViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}