package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.e.thetogetherapp.R
import com.e.thetogetherapp.profile.UserProfileActivity
import java.util.*
import kotlin.system.exitProcess
@Suppress("DEPRECATION")

class LanguagePage : AppCompatActivity() {

    lateinit var locale: Locale
    private var currentLanguage :String = "en"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        val romanianSwitch = findViewById<SwitchCompat>(R.id.romanianSwitch)
        val englishSwitch = findViewById<SwitchCompat>(R.id.englishSwitch)

        val languageBackButton = findViewById<View>(R.id.languageBackButton)

        // BUTTONS ---------------------------------------------------------------------------

        languageBackButton.setOnClickListener {
            startActivity(Intent(this@LanguagePage, SettingsPage::class.java))
        }

        //switch-----------------------------------------------------------------------------
        if(currentLanguage=="en"){
            romanianSwitch.setChecked(false)
            englishSwitch.setChecked(true)
        }else if(currentLanguage=="ro"){
            romanianSwitch.setChecked(true)
            englishSwitch.setChecked(false)
        }

        englishSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (englishSwitch.isChecked) {
                romanianSwitch.setChecked(false)
                setLocale("en");
            }
        }

        romanianSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (romanianSwitch.isChecked) {
                englishSwitch.setChecked(false)
                setLocale("ro");
            }
        }
    }

    private fun setLocale(localeName: String) {
            currentLanguage = localeName
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            finish()
    }

        // ACTIVITY ----------------------------------------------------------------------------
        /*currentLanguage = intent.getStringExtra(currentLang).toString()
        spinner = findViewById(R.id.spinner)
        val list = ArrayList<String>()
        list.add("Select Language")
        list.add("English")
        list.add("Română")

        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                    }
                    1 -> setLocale("en")
                    2 -> setLocale("ro")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = Intent(
                this,
                LanguagePage::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {
            Toast.makeText(
                this@LanguagePage, "Language, , already, , selected)!", Toast.LENGTH_SHORT).show();
        }
    }
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
        exitProcess(0)*/

}