package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        val database = Firebase.database.reference

        var intent: Intent

        findViewById<TextView>(R.id.nearYouRequestsButton).setOnClickListener{
            val userRef = database.child("users").child(uid!!)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d("SearchRequestsPage", "loadUserData:onCancelled")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userLocation = snapshot.child("location").child("city").value.toString()

                    val arguments = Bundle()
                    arguments.apply { putString("location" , userLocation)
                    putString("category", "Near you")
                        putString("type", "requests")}
                    intent = Intent(this@SearchRequestsPage, SearchCategoryPage::class.java)
                    intent.putExtras(arguments)
                    startActivity(intent)
                }
            })
        }

        findViewById<TextView>(R.id.errandsRequestsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "Errands")
                putString("type", "requests")}
            intent = Intent(this@SearchRequestsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.foodRequestsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "Food")
                putString("type", "requests")}
            intent = Intent(this@SearchRequestsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.babySuppliesRequestsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "Baby supplies")
                putString("type", "requests")}
            intent = Intent(this@SearchRequestsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.schoolSuppliesRequestsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "School supplies")
                putString("type", "requests")}
            intent = Intent(this@SearchRequestsPage, SearchCategoryPage::class.java)
            intent.putExtras(arguments)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.otherRequestsButton).setOnClickListener{
            val arguments = Bundle()
            arguments.apply { putString("category", "Other")
                putString("type", "requests")}
            intent = Intent(this@SearchRequestsPage, SearchCategoryPage::class.java)
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
                    val intent = Intent(this@SearchRequestsPage, UserProfileActivity::class.java)
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