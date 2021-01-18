package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.databinding.ActivityCreateEventBinding
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CreateEventPage : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String? = null
        var userType: String? = null

        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
            userType = extras.getString("userType")
        }

        val binding : ActivityCreateEventBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_create_event)

        var event: Event
        val eventType: String?
        val database = Firebase.database.reference

        binding.event = Event()

        // FORM

        eventType = if(userType == "needy"){
            "requests"
        }else{
            "donations"
        }

        val titleText = "New $eventType"
        val buttonText = "Submit $eventType"

        findViewById<TextView>(R.id.newEventTypeTitle).text = titleText
        findViewById<Button>(R.id.submitCreateEventButton).text = buttonText


        // SPINNER

        val categorySpinner = findViewById<Spinner>(R.id.newEventCategorySpinner)
        val categoryList = ArrayList<String>()
        categoryList.add("Select category")

        val categoryRef = database.child(eventType!!).child("category")

        categoryRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    categoryList.add(it.value.toString())
                }
            }
        })


        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        categorySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("CrateEventPage", "spinner:onNothingSelected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> {}
                    1 -> binding.event!!.category = categoryList[1]
                    2 -> binding.event!!.category = categoryList[2]
                    3 -> binding.event!!.category = categoryList[3]
                    4 -> binding.event!!.category = categoryList[4]
                    5 -> binding.event!!.category = categoryList[5]
                    6 -> binding.event!!.category = categoryList[6]
                }
            }
        }


        //NAVIGATION BAR ------------------------------------------------------------------------------

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigationBar)
        navigationBar.setSelectedItemId(R.id.addNew)

        navigationBar.setOnNavigationItemSelectedListener {
            val user = Bundle()
            user.putString("userType", userType)
            user.putString("uid", uid)

            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this@CreateEventPage, UserProfileActivity::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                R.id.notifications -> {
                    val intent = Intent(this@CreateEventPage, NotificationPage::class.java)
                    intent.putExtras(user)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
                    if(userType.equals("volunteer")){
                        val intent = Intent(this@CreateEventPage, SearchRequestsPage::class.java)
                        intent.putExtras(user)
                        startActivity(intent)
                    }
                    if(userType.equals("needy")){
                        val intent = Intent(this@CreateEventPage, SearchDonationsPage::class.java)
                        intent.putExtras(user)
                        startActivity(intent)
                    }
                    true
                }
                R.id.addNew -> {
                    true
                }
                else->false
            }
        }

        // BUTTONS -----------------------------------------------------------------
        
        findViewById<Button>(R.id.submitCreateEventButton).setOnClickListener{
            val eventRef = database.child(eventType!!)
            val eventId = eventRef.push().key

            event = binding.event!!
            event.type = eventType

            if(userType == "needy"){
                event.needy = uid
            }else{
                event.volunteer = uid
            }

            event.id = eventId
            event.status = "unassigned"

            eventRef.child(eventId!!).setValue(event)

            finish()
        }
    }
}