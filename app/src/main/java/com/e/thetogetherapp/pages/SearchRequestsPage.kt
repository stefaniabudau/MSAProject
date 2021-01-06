package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchRequestsPage: AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String ? = null
        var userType: String ? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
            userType = extras.getString("userType")
        }

        setContentView(R.layout.activity_search_request_category)

        //NAVIGATION BAR ------------------------------------------------------------------------------

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigationBar)
        navigationBar.setSelectedItemId(R.id.search)

        navigationBar.setOnNavigationItemSelectedListener {
            val user = Bundle()
            user.putString("userType", userType)
            user.putString("uid", uid)

            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this@SearchRequestsPage, UserProfileActivity::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                R.id.notifications -> {
                    val intent = Intent(this@SearchRequestsPage, NotificationPage::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
                    true
                }
                R.id.addNew -> {
                    val intent = Intent(this@SearchRequestsPage, CreateEventPage::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                else->false
            }
        }

    }
}