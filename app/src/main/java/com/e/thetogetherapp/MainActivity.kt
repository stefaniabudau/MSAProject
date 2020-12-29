package com.e.thetogetherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.thetogetherapp.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        val userType = Bundle()
        userType.putString("type", "needy")
        intent.putExtras(userType)
        startActivity(intent)
    }

}