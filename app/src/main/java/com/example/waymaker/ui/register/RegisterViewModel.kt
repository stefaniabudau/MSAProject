package com.example.waymaker.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.waymaker.data.LoginRepository
import com.example.waymaker.data.Result

import com.example.waymaker.R
import com.example.waymaker.data.RegisterRepository
import com.example.waymaker.ui.login.LoggedInUserView
import com.example.waymaker.ui.login.LoginResult

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = registerRepository.register(username, password)

        if (result is Result.Success) {
            _registerResult.value = RegisterResult(
                success = RegisteredUserView(
                    displayEmail = result.data.displayEmail
                )
            )
        } else {
            _registerResult.value =
                RegisterResult(error = R.string.login_failed)
        }
    }

    fun registerDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _registerForm.value =
                RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value =
                RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _registerForm.value =
                RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}