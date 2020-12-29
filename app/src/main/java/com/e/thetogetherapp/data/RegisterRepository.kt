package com.e.thetogetherapp.data

import com.e.thetogetherapp.data.model.RegisteredUser
import com.e.thetogetherapp.register.RegisterForm

class RegisterRepository(val dataSource: RegisterDataSource) {

    var user: RegisteredUser? = null
        private set

    val isRegistered: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

//    fun logout() {
//        user = null
//        dataSource.logout()
//    }

    fun register(registerData: RegisterForm): Result<RegisteredUser> {
        // handle login
        val result = dataSource.register(registerData)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: RegisteredUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
//         @see https://developer.android.com/training/articles/keystore
    }

}
