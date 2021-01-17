package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.data.model.Rating
import com.e.thetogetherapp.data.model.User
import com.e.thetogetherapp.profile.UserProfileActivity
import com.e.thetogetherapp.profile.ViewProfileActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class UnassignedEventCreatorPage : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_unassigned_event_creator)
        val database = Firebase.database.reference

        var uid: String? = null
        var eventId: String? = null
        var eventType: String? = null
        var userType: String? = null

        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
            userType = extras.getString("userType")
            eventType = extras.getString("eventType")
            eventId = extras.getString("eventId")
        }

        // View
        val eventTitle = findViewById<TextView>(R.id.eventTitle)
        val eventLocation = findViewById<TextView>(R.id.eventLocation)
        val eventDate = findViewById<TextView>(R.id.eventDate)
        val eventTime = findViewById<TextView>(R.id.eventTime)
        val eventDescription = findViewById<TextView>(R.id.eventDescription)
        val eventCategory = findViewById<TextView>(R.id.eventCategory)

        //get view
        val eventRef = database.child(eventType!!).child(eventId!!)
        eventRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("Header", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val eventData = snapshot.getValue<Event>()
                    eventTitle.text = eventData!!.title
                    eventLocation.text = eventData!!.location
                    eventDate.text = eventData!!.date
                    eventTime.text = eventData!!.time
                    eventDescription.text = eventData!!.description
                    eventCategory.text = eventData!!.category
                }
            }
        })

        val userTypeText = findViewById<TextView>(R.id.userTypeText)
        val userNicknameText = findViewById<TextView>(R.id.userNicknameText)

        // Get user info
        val userRef = database.child("users").child(uid!!)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("Header", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue<User>()

                userNicknameText.text = userData!!.nickname
                userTypeText.text = userData!!.type
            }
        })

        userNicknameText.setOnClickListener{
            val intent = Intent(this@UnassignedEventCreatorPage, ViewProfileActivity::class.java)
            val arguments = Bundle().apply { putString("uid", uid) }
            intent.putExtras(arguments)
            startActivity(intent)
        }


        //Buttons
        val deleteEventButton = findViewById<Button>(R.id.deleteEventButton)
        val editEventButton = findViewById<Button>(R.id.editEventButton)
        val eventUnassignedGoBackButton = findViewById<View>(R.id.eventUnassignedGoBackButton)

        eventUnassignedGoBackButton.setOnClickListener{
            finish()
        }

        deleteEventButton.setOnClickListener{
            val event = database.child("event").child(eventId!!)
            //delete event
            database.child(eventType).child(eventId).removeValue()
            finish()
        }

        editEventButton.setOnClickListener{
            val user = Bundle()
            user.putString("eventId", eventId)
            user.putString("eventType", eventType)
            val intent = Intent(this@UnassignedEventCreatorPage, EditEventPage::class.java)
            intent.putExtras(user)
            startActivity(intent)
        }


    }
}