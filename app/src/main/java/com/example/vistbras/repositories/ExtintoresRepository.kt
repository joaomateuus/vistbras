package com.example.vistbras.repositories

import com.example.vistbras.models.ExtintorItem
import com.example.vistbras.rest.RetrofitService

class ExtintoresRepository constructor(private val retrofitService: RetrofitService) {
    fun createExtintor(token: String, extintor: ExtintorItem) =
        retrofitService.createExtintor(token, extintor)

    fun getExtintores(token: String) =
        retrofitService.getExtintores(token)

    fun getExtintor(token: String, extintorId: Int) =
        retrofitService.getExtintor(token, extintorId)

    fun editExtintor(token: String, extintorId: Int, extintor: ExtintorItem) =
        retrofitService.editExtintor(token, extintorId, extintor)
}