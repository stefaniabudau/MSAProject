package com.example.waymaker.data

import com.example.waymaker.data.model.LoggedInUser

class RegisterRepository (val dataSource: RegisterDataSource){

    fun register(username: String, password: String): Result<LoggedInUser> {
        // handle register
        val result = dataSource.register(username, password)
        return result
    }
}