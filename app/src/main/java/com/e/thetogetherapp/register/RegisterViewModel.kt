package com.e.thetogetherapp.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.RegisterDataSource
import com.e.thetogetherapp.data.Result
import com.e.thetogetherapp.data.model.RegisteredUser

class RegisterViewModel(private val registerDataSource: RegisterDataSource): ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(registerData: RegisterForm) {
        val result = registerDataSource.register(registerData)

        if (result is Result.Success) {
            _registerResult.value = RegisterResult(success = RegisteredUserView(displayName = result.data.displayName))
        } else {
            _registerResult.value = RegisterResult(error = R.string.register_failed)
        }
    }


    // TODO: fix conditions

    fun registerDataChanged(formData: RegisterForm) {
        if (!isEmailValid(formData.email)){
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        }
        else if (isEmpty(formData.name)){
            _registerForm.value = RegisterFormState(nameError = R.string.empty_name)
        }
        else if (isEmpty(formData.nickname)){
            _registerForm.value = RegisterFormState(nicknameError = R.string.empty_nickname)
        }
        else if (isEmpty(formData.age)){
            _registerForm.value = RegisterFormState(ageError = R.string.empty_age)
        }
        else if (!isAbove18(formData.age.toInt())){
            _registerForm.value = RegisterFormState(ageError = R.string.invalid_age)
        }
        else if (isEmpty(formData.country)){
            _registerForm.value = RegisterFormState(countryError = R.string.invalid_location)
        }
        else if (isEmpty(formData.city)){
            _registerForm.value = RegisterFormState(cityError = R.string.invalid_location)
        }
        else if (isEmpty(formData.country)){
            _registerForm.value = RegisterFormState(addressError = R.string.invalid_location)
        }
        else if (!isPasswordValid(formData.password1)){
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        }
        else if (!arePasswordsIdentical(formData.password1, formData.password2)){
            _registerForm.value = RegisterFormState(differentPasswords = R.string.different_passwords)
        }
        else{
            _registerForm.value = RegisterFormState(isDataValid = true)

        }
    }


    private fun isEmailValid(email: String): Boolean{
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    private fun isAbove18(age: Int): Boolean{
        return age >= 18
    }

    private fun arePasswordsIdentical(password1: String, password2: String): Boolean{
        return password1 == password2
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isEmpty(fieldData: String?): Boolean{
        if (fieldData != null) {
            return fieldData.isBlank()
        }
        return true
    }
}