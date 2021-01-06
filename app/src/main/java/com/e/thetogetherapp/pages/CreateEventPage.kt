package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.forms.PasswordChangeFormState
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        var uid: String ? = null
        var userType: String ? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
            userType = extras.getString("userType")
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

        //NAVIGATION BAR ------------------------------------------------------------------------------

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigationBar)
        navigationBar.setSelectedItemId(R.id.addNew)

        navigationBar.setOnNavigationItemSelectedListener {
            val user = Bundle()
            user.putString("userType", userType)
            user.putString("uid", uid)

            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this@CreateEventPage, UserProfileActivity::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                R.id.notifications -> {
                    val intent = Intent(this@CreateEventPage, NotificationPage::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
                    if(userType.equals("needy")){
                        val intent = Intent(this@CreateEventPage, SearchRequestsPage::class.java)
                        intent.putExtras(user)
                        startActivity(intent)
                    }
                    if(userType.equals("volunteer")){
                        val intent = Intent(this@CreateEventPage, SearchDonationsPage::class.java)
                        intent.putExtras(user)
                        startActivity(intent)
                    }
                    true
                }
                R.id.addNew -> {
                    true
                }
                else->false
            }
        }
    }
}