package com.e.thetogetherapp.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class VolunteerProfileActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String? = null
        val extras: Bundle? = intent.extras

        if (extras != null) {
            uid = extras.getString("uid")
        }

        database = Firebase.database.reference

        setContentView(R.layout.activity_profile_volunteer)

    }
}