package com.e.thetogetherapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.register.RegisterActivity

class RegisterTransitionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register_type)

        val registerAsNeedy = findViewById<Button>(R.id.registerAsNeedy)
        val registerAsVolunteer = findViewById<Button>(R.id.registerAsVolunteer)

        val intent = Intent(this@RegisterTransitionActivity, RegisterActivity::class.java)
        val userType = Bundle()

        registerAsNeedy.setOnClickListener{
            userType.putString("type", "needy")
            intent.putExtras(userType)

            startActivity(intent)
            finish()
        }

        registerAsVolunteer.setOnClickListener{
            userType.putString("type", "volunteer")
            intent.putExtras(userType)

            startActivity(intent)
            finish()
        }

    }
}