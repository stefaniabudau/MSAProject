package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.login.LoginActivity
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class NotificationPage :AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String ? = null
        var userType: String ? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
            userType = extras.getString("userType")
        }

        setContentView(R.layout.activity_notifications)

        //NAVIGATION BAR ------------------------------------------------------------------------------

        val navigationBar = findViewById<View>(R.id.navigationBar) as BottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val user = Bundle()
                    val intent = Intent(this@NotificationPage, UserProfileActivity::class.java)

                    user.putString("userType", userType)
                    user.putString("uid", uid)
                    intent.putExtras(user)

                    startActivity(intent)
                    true
                    true
                }
                R.id.notifications -> {
                    true
                }
                R.id.search -> {
                    if(userType=="needy"){
                        val user = Bundle()
                        val intent = Intent(this@NotificationPage, SearchRequestsPage::class.java)

                        user.putString("userType", userType)
                        user.putString("uid", uid)
                        intent.putExtras(user)

                        startActivity(intent)
                    }
                    if(userType=="volunteer"){
                        val user = Bundle()
                        val intent = Intent(this@NotificationPage, SearchDonationsPage::class.java)

                        user.putString("userType", userType)
                        user.putString("uid", uid)
                        intent.putExtras(user)

                        startActivity(intent)
                    }
                    true
                }
                R.id.addNew -> {
                    val user = Bundle()
                    val intent = Intent(this@NotificationPage, CreateEventPage::class.java)

                    user.putString("userType", userType)
                    user.putString("uid", uid)
                    intent.putExtras(user)

                    startActivity(intent)
                    true
                }
                else->false
            }
        }


    }
}