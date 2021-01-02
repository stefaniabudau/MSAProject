package com.e.thetogetherapp.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.RegisteredUser
import com.e.thetogetherapp.profile.NeedyProfileActivity
import com.e.thetogetherapp.profile.UserProfileActivity

class RegisterActivity: AppCompatActivity(){

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var type: String? = null
        val userType: Bundle? = intent.extras

        if(userType != null){
            type = userType.getString("type")
        }

        val formData = RegisterForm(type = type!!)

        setContentView(R.layout.activity_register)

        val email = findViewById<EditText>(R.id.registerEmail)
        val name = findViewById<EditText>(R.id.registerName)
        val nickname = findViewById<EditText>(R.id.registerNickname)
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

            if (registerState.emailError != null) {
                email.error = getString(registerState.emailError)
            }
            if (registerState.nameError != null) {
                name.error = getString(registerState.nameError)
            }
            if (registerState.nicknameError != null) {
                nickname.error = getString(registerState.nicknameError)
            }
            if (registerState.ageError != null) {
                age.error = getString(registerState.ageError)
            }
            if (registerState.countryError != null) {
                country.error = getString(registerState.countryError)
            }
            if (registerState.cityError != null) {
                city.error = getString(registerState.cityError)
            }
            if (registerState.addressError != null) {
                address.error = getString(registerState.addressError)
            }
            if (registerState.passwordError != null) {
                password1.error = getString(registerState.passwordError)
            }
            if (registerState.differentPasswords != null) {
                password2.error = getString(registerState.differentPasswords)
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

                val user = Bundle()
                val intent = Intent(this@RegisterActivity, UserProfileActivity::class.java)

                user.putString("uid", registerResult.success.uid)
                intent.putExtras(user)

                startActivity(intent)
            }
            setResult(Activity.RESULT_OK)

            finish()
        })


        fun updateFormObject(){
            formData.email = email.text.toString()
            formData.name = name.text.toString()
            formData.nickname = nickname.text.toString()
            formData.age = age.text.toString()
            formData.country = country.text.toString()
            formData.city = city.text.toString()
            formData.address = address.text.toString()
            formData.password1 = password1.text.toString()
            formData.password2 = password2.text.toString()
        }


        name.afterTextChanged {
            updateFormObject()
            registerViewModel.registerDataChanged(formData)
        }

        email.afterTextChanged {
            updateFormObject()
            registerViewModel.registerDataChanged(formData)
        }

        age.afterTextChanged {
            updateFormObject()
            registerViewModel.registerDataChanged(formData)
        }

        city.afterTextChanged {
            updateFormObject()
            registerViewModel.registerDataChanged(formData)
        }

        country.afterTextChanged {
            updateFormObject()
            registerViewModel.registerDataChanged(formData)
        }

        address.afterTextChanged {
            updateFormObject()
            registerViewModel.registerDataChanged(formData)
        }

        password1.afterTextChanged {
            updateFormObject()
            registerViewModel.registerDataChanged(formData)
        }

        password2.apply {
            afterTextChanged {
                updateFormObject()
                registerViewModel.registerDataChanged(formData)
            }

//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        registerViewModel.register(formData)
//                }
//                false
//            }

            register.setOnClickListener {
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

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
