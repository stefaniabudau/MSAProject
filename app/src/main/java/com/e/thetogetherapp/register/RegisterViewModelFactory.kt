package com.e.thetogetherapp.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.thetogetherapp.data.RegisterDataSource
import com.e.thetogetherapp.data.RegisterRepository

class RegisterViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                registerDataSource = RegisterDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}