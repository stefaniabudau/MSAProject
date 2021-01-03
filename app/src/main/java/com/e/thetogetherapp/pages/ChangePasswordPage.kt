package com.e.thetogetherapp.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.forms.PasswordChangeFormState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChangePasswordPage : AppCompatActivity(){

    private lateinit var changePasswordFormState : MutableLiveData<PasswordChangeFormState>
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Firebase.database.reference
        auth = Firebase.auth

        setContentView(R.layout.activity_change_password)

        val currentPassword = findViewById<EditText>(R.id.currentPassword)
        val newPassword = findViewById<EditText>(R.id.newPassword)
        val newPasswordRe = findViewById<EditText>(R.id.confirmNewPassword)

        val save = findViewById<Button>(R.id.submitChangePasswordButton)

        changePasswordFormState = MutableLiveData<PasswordChangeFormState>()
        changePasswordFormState.observe(this@ChangePasswordPage, Observer {
            val passwordFormState = it ?: return@Observer

            save.isEnabled = passwordFormState.isDataValid

            if (passwordFormState.passwordLengthError != null) {
                newPassword.error = getString(passwordFormState.passwordLengthError)
            }
            if (passwordFormState.differentPasswordsError != null) {
                newPasswordRe.error = getString(passwordFormState.differentPasswordsError)
            }
            if(passwordFormState.notNewPassword != null){
                newPassword.error = getString(passwordFormState.notNewPassword)
            }
        })


        currentPassword.afterTextChanged {
            formDataChanged(
                currentPassword.text.toString(),
                newPassword.text.toString(),
                newPasswordRe.text.toString()
            )
        }

        newPassword.afterTextChanged {
            formDataChanged(
                currentPassword.text.toString(),
                newPassword.text.toString(),
                newPasswordRe.text.toString()
                )
            }


        newPasswordRe.apply {
            afterTextChanged {
                formDataChanged(
                    currentPassword.text.toString(),
                    newPassword.text.toString(),
                    newPasswordRe.text.toString()
                )
            }
        }

            save.setOnClickListener {
                val user = auth.currentUser!!

                val email = user.email.toString()
                val credential: AuthCredential =
                    EmailAuthProvider.getCredential(email, currentPassword.text.toString())

                user.reauthenticate(credential).addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        user.updatePassword(newPassword.text.toString()).addOnCompleteListener(this){task2 ->
                            if (task2.isSuccessful){
                                Toast.makeText(baseContext, R.string.password_update_success, Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(baseContext, R.string.password_update_fail, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{
                        Toast.makeText(baseContext, R.string.password_update_fail, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    private fun formDataChanged(crtPassword: String, newPassword: String, newPasswordRe: String) {
        if(!isPasswordValid(newPassword)){
            changePasswordFormState.value = PasswordChangeFormState(passwordLengthError = R.string.invalid_password)
        }
        else if(!arePasswordsIdentical(newPassword, newPasswordRe)){
            changePasswordFormState.value = PasswordChangeFormState(differentPasswordsError = R.string.different_passwords)
        }
        else if (!isNewPassword(crtPassword, newPassword)){
            changePasswordFormState.value = PasswordChangeFormState(notNewPassword = R.string.not_new_password)
        }
        else{
            changePasswordFormState.value = PasswordChangeFormState(isDataValid = true)
        }
    }


    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun arePasswordsIdentical(password1: String, password2: String): Boolean{
        return password1 == password2
    }

    private fun isNewPassword(crtPassword: String, newPassword:String): Boolean{
        return crtPassword != newPassword
    }
}