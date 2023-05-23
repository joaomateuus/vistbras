package com.example.vistbras.store

import com.example.vistbras.models.FiscalRequest
import com.example.vistbras.models.FiscalUser
import com.example.vistbras.models.User

object LoggedUserSession {
    private var _user: User? = null
    private var _fiscal: FiscalRequest? = null

    fun setUser(user: User) {
        _user = user
    }

    fun getUser(): User? = _user

    fun isUserLoggedIn(): Boolean = _user != null

    fun setFiscalData(fiscal: FiscalRequest) {
        _fiscal = fiscal
    }

    fun getFiscal(): FiscalRequest? = _fiscal

}