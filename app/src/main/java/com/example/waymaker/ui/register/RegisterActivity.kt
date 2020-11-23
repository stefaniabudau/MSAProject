package com.example.waymaker.ui.register

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.example.waymaker.R
import com.example.waymaker.ui.login.LoginViewModel
import com.example.waymaker.ui.login.LoginViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register1.*
import java.util.Observer

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)
        auth = FirebaseAuth.getInstance()

        var name = findViewById<TextInputEditText>(R.id.name)
        var email = findViewById<TextInputEditText>(R.id.email)
        var password = findViewById<TextInputEditText>(R.id.password)


        btnLogRegister.setOnClickListener {
            onBackPressed()
        }

        btnRegister.setOnClickListener{
            registerViewModel.register(email.toString(), password.text.toString());
        }

        registerViewModel = ViewModelProviders.of(this, RegisterViewModelFactory())
            .get(RegisterViewModel::class.java)

        /*registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            if (registerState.usernameError != null) {
                email.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val loginResult = it ?: return@Observer

            Log.d("State", loginResult.toString())
            Log.d("Email", email.text.toString())
            Log.d("Pass", password.text.toString())

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                //updateUiWithUser(loginResult.success)
                setContentView(R.layout.home_page_login)
            }


            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            // finish()
        })*/

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_from_left,
            R.anim.slide_to_right
        )
    }

}