package com.example.vistbras.repositories

import com.example.vistbras.models.Extintor
import com.example.vistbras.models.ExtintorItem
import com.example.vistbras.rest.RetrofitService

class ExtintoresRepository constructor(private val retrofitService: RetrofitService) {
    fun createExtintor(token: String, extintor: ExtintorItem) =
        retrofitService.createExtintor(token, extintor)
}