package com.e.thetogetherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//DARK MODE -------------------------------------------------------------

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Declare the switch from the layout file
        val btn = findViewById<SwitchCompat>(R.id.darkModeSwitch)

        // set the switch to listen on checked change
        btn.setOnCheckedChangeListener { _, isChecked ->

            // if the button is checked, i.e., towards the right or enabled
            // enable dark mode, change the text to disable dark mode
            // else keep the switch text to enable dark mode
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn.text = "Disable dark mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btn.text = "Enable dark mode"
            }
        }
    }


}


//LANGUAGE ------------------------------------------------------------------------
/*
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.system.exitProcess
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var spinner: Spinner
    lateinit var locale: Locale
    private var currentLanguage = "en"
    private var currentLang: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)
        title = "KotlinApp"
        currentLanguage = intent.getStringExtra(currentLang).toString()
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
                    MainActivity::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {
            Toast.makeText(
                    this@MainActivity, "Language, , already, , selected)!", Toast.LENGTH_SHORT).show();
        }
    }
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
        exitProcess(0)
    }
}*/

//NAVIGATION BAR ------------------------------------------------------------------
/*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.component_navigation_bar)
        val bottomBar: BottomNavigationView = findViewById(R.id.bottomBar)
        bottomBar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
            true
        })
    }
}*/

