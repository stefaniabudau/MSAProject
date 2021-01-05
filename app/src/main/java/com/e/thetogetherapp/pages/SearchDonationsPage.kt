package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchDonationsPage: AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String ? = null
        var userType: String ? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
            userType = extras.getString("userType")
        }

        setContentView(R.layout.activity_search_donation_category)

        //NAVIGATION BAR ------------------------------------------------------------------------------

        val navigationBar = findViewById<View>(R.id.navigationBar) as BottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@SearchDonationsPage, UserProfileActivity::class.java))
                    true
                }
                R.id.notifications -> {
                    startActivity(Intent(this@SearchDonationsPage, NotificationPage::class.java))
                    true
                }
                R.id.search -> {
                    true
                }
                R.id.addNew -> {
                    startActivity(Intent(this@SearchDonationsPage, CreateEventPage::class.java))
                    true
                }
                else->false
            }
        }

    }
}