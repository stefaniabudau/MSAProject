package com.e.thetogetherapp.register

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.RegisteredUser
import com.e.thetogetherapp.login.LoggedInUserView
import com.e.thetogetherapp.login.LoginViewModel
import com.e.thetogetherapp.login.LoginViewModelFactory
import com.e.thetogetherapp.login.afterTextChanged

class RegisterActivity: AppCompatActivity(){

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var type: String? = null
        val userType: Bundle? = intent.extras

        if(userType != null){
            type = userType.getString("type")
        }

        setContentView(R.layout.activity_register)

        val email = findViewById<EditText>(R.id.registerEmail)
        val name = findViewById<EditText>(R.id.registerName)
        val age = findViewById<EditText>(R.id.registerAge)
        val country = findViewById<EditText>(R.id.registerCountry)
        val city = findViewById<EditText>(R.id.registerCity)
        val address = findViewById<EditText>(R.id.registerAddress)
        val password1 = findViewById<EditText>(R.id.registerPassword1)
        val password2 = findViewById<EditText>(R.id.registerPassword2)

        val register = findViewById<Button>(R.id.registerSubmitButton)
        val login = findViewById<Button>(R.id.loginGoToButton)

        registerViewModel = ViewModelProviders.of(this, RegisterViewModelFactory())
            .get(RegisterViewModel::class.java)


        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            register.isEnabled = registerState.isDataValid

            if (registerState.nameError != null) {
                name.error = getString(registerState.nameError)
            }
            if (registerState.emailError != null) {
                email.error = getString(registerState.emailError)
            }
            if (registerState.ageError != null) {
                age.error = getString(registerState.ageError)
            }
            if (registerState.locationError != null) {
                address.error = getString(registerState.locationError)
                country.error = getString(registerState.locationError)
                city.error = getString(registerState.locationError)
            }
            if (registerState.passwordError != null) {
                password1.error = getString(registerState.passwordError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

//            loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                updateUiWithUser(registerResult.success)
            }
            setResult(Activity.RESULT_OK)

            finish()
        })

        val formData: RegisterForm

        formData = RegisterForm(
            email = email.text.toString(),
            name = name.text.toString(),
            age = age.text.toString(),
            country = country.text.toString(),
            city = city.text.toString(),
            address = address.text.toString(),
            password1 = password1.text.toString(),
            password2 = password2.text.toString(),
            type = type!!
        )

        name.afterTextChanged {
            registerViewModel.registerDataChanged(formData)
        }

        email.afterTextChanged {
            registerViewModel.registerDataChanged(formData)
        }

        age.afterTextChanged {
            registerViewModel.registerDataChanged(formData)
        }

        city.afterTextChanged {
            registerViewModel.registerDataChanged(formData)
        }

        country.afterTextChanged {
            registerViewModel.registerDataChanged(formData)
        }

        address.afterTextChanged {
            registerViewModel.registerDataChanged(formData)
        }

        password1.afterTextChanged {
            registerViewModel.registerDataChanged(formData)
        }

        password2.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(formData)
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(formData)
                }
                false
            }

            login.setOnClickListener {
//                loading.visibility = View.VISIBLE
                registerViewModel.register(formData)
            }
        }
    }

    private fun updateUiWithUser(model: RegisteredUser) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

