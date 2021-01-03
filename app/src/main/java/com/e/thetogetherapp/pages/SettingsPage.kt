package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SettingsPage :AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        auth = Firebase.auth

//        val darkModeSwitch = findViewById<Switch>(R.id.darkModeSwitch)
//        val notificationSwitch = findViewById<Switch>(R.id.notificationSwitch)

        val editProfile = findViewById<TextView>(R.id.editProfile)
        val changePassword = findViewById<TextView>(R.id.changePassword)
        val language = findViewById<TextView>(R.id.language)
        val logout = findViewById<TextView>(R.id.logout)

        val back = findViewById<View>(R.id.settingsBackButton)

        back.setOnClickListener{
            finish()
        }

//        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
//            if (darkModeSwitch.isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                darkModeSwitch.text = "Disable dark mode"
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                darkModeSwitch.text = "Enable dark mode"
//            }
//        }

        editProfile.setOnClickListener{
            startActivity(Intent(this@SettingsPage, EditProfilePage::class.java))
        }

        changePassword.setOnClickListener{
            startActivity(Intent(this@SettingsPage, ChangePasswordPage::class.java))
        }

        logout.setOnClickListener{
            auth.signOut()
            val intnt = Intent(this@SettingsPage, LoginActivity::class.java)
            intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intnt.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intnt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intnt)
//            finishAffinity()
        }
    }
}