package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LogoutPage :AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        setContentView(R.layout.activity_logout)

        var submitLogoutButton = findViewById<Button>(R.id.submitLogoutButton)
        var logoutBackButton = findViewById<View>(R.id.logoutBackButton)

        // BUTTONS ---------------------------------------------------------------------------

        logoutBackButton.setOnClickListener{
            finish()
        }

        submitLogoutButton.setOnClickListener{
            auth.signOut()
            val intnt = Intent(this@LogoutPage, LoginActivity::class.java)
            intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intnt.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intnt)
//            finishAffinity()
        }
    }
}