package com.example.vistbras.repositories

import com.example.vistbras.models.FiscalUser
import com.example.vistbras.models.User
import com.example.vistbras.rest.RetrofitService

class UserRepository constructor(
    private val retrofitService: RetrofitService,
) {
    fun createUser(user: User) = retrofitService.createUser(user)
    fun createFiscalUser(fiscal: FiscalUser) = retrofitService.createFiscalUser(fiscal)
}