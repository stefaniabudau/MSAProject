package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.SearchEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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

        val database = Firebase.database.reference
        var intent: Intent

        findViewById<TextView>(R.id.nearYouDonationsButton).setOnClickListener{
            val arguments = Bundle()
            val userRef = database.child("users").child(uid!!)
            userRef.addValueEventListener(object :ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Log.d("SearchDonationsPage", "loadUserData:onCancelled")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userLocation = snapshot.child("location").child("city").value.toString()

                    arguments.apply { putString("location" , userLocation)
                        putString("category", "Near you")
                    putString("type", "donations")}
                    intent = Intent(this@SearchDonationsPage, SearchCategoryPage::class.java)
                    intent.putExtras(arguments)
                    startActivity(intent)
                }
            })
        }

        findViewById<TextView>(R.id.devicesDonationsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "Devices")
                putString("type", "donations")}
            intent = Intent(this@SearchDonationsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.foodDonationsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "Food")
                putString("type", "donations")}
            intent = Intent(this@SearchDonationsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.babySuppliesDonationsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "Baby supplies")
                putString("type", "donations")}
            intent = Intent(this@SearchDonationsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.schoolSuppliesDonationsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "School supplies")
                putString("type", "donations")}
            intent = Intent(this@SearchDonationsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.otherDonationsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "Other")
                putString("type", "donations")}
            intent = Intent(this@SearchDonationsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }


        //NAVIGATION BAR ------------------------------------------------------------------------------

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigationBar)
        navigationBar.setSelectedItemId(R.id.search)

        navigationBar.setOnNavigationItemSelectedListener {
            val user = Bundle()
            user.putString("userType", userType)
            user.putString("uid", uid)

            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this@SearchDonationsPage, UserProfileActivity::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
                    true
                }
                R.id.addNew -> {
                    val intent = Intent(this@SearchDonationsPage, CreateEventPage::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                else->false
            }
        }

    }
}