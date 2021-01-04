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

class CreateEventPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String? = null
        val extras: Bundle? = intent.extras

        if (extras != null) {
            uid = extras.getString("uid")
        }

        val database = Firebase.database.reference

        setContentView(R.layout.activity_create_event)

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
    }
}