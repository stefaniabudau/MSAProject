package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.e.thetogetherapp.R
import com.e.thetogetherapp.login.LoginActivity
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class SettingsPage :AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    lateinit var locale: Locale
    private var currentLanguage = "en"
    private var currentLang: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        auth = Firebase.auth

        val darkModeSwitch = findViewById<SwitchCompat>(R.id.darkModeSwitch)
        val notificationSwitch = findViewById<SwitchCompat>(R.id.notificationSwitch)

        val editProfile = findViewById<TextView>(R.id.editProfile)
        val changePassword = findViewById<TextView>(R.id.changePassword)
        val language = findViewById<TextView>(R.id.language)
        val logout = findViewById<TextView>(R.id.logout)
        val settingBackButton = findViewById<View>(R.id.settingsBackButton)


        //SWITCHES ----------------------------------------------------------------------------

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->

            // if the button is checked, i.e., towards the right or enabled
            // enable dark mode, change the text to disable dark mode
            // else keep the switch text to enable dark mode
            if (darkModeSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeSwitch.text = "Disable dark mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeSwitch.text = "Enable dark mode"
            }
        }

        // BUTTONS ---------------------------------------------------------------------------

        settingBackButton.setOnClickListener{
            finish()
        }

        editProfile.setOnClickListener{
            startActivity(Intent(this@SettingsPage, EditProfilePage::class.java))
        }

        changePassword.setOnClickListener{
            startActivity(Intent(this@SettingsPage, ChangePasswordPage::class.java))
        }

        language.setOnClickListener{
            startActivity(Intent(this@SettingsPage, LanguagePage::class.java))
        }

        logout.setOnClickListener{
            auth.signOut()
            val intnt = Intent(this@SettingsPage, LoginActivity::class.java)
            intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intnt.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intnt)
//            finishAffinity()
        }
    }

    override fun onRestart(){
        super.onRestart()
        finish()
        startActivity(getIntent())
    }
}