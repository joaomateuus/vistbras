package com.example.vistbras.store

import com.example.vistbras.models.User

object LoggedUserSession {
    private var _user: User? = null

    fun setUser(user: User) {
        _user = user
    }

    fun getUser(): User? = _user

    fun isUserLoggedIn(): Boolean = _user != null

}