package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.data.model.Rating
import com.e.thetogetherapp.data.model.User
import com.e.thetogetherapp.profile.ViewProfileActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CompletedEventPage : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_completed_event)
        val database = Firebase.database.reference

        var uidNeedy: String? = null
        var uidVolunteer: String? = null
        var eventId: String? = null
        var eventType: String? = null
        var eventToNeedyReviewId: String? = null
        var eventToVolunteerReviewId: String? = null

        val extras: Bundle? = intent.extras

        if(extras != null){
            eventType = extras.getString("eventType")
            eventId = extras.getString("eventId")
            uidNeedy = extras.getString("uidNeedy")
            uidVolunteer = extras.getString("uidVolunteer")
            eventToNeedyReviewId = extras.getString("eventToNeedyReviewId")
            eventToVolunteerReviewId = extras.getString("eventToVolunteerReviewId")
        }


        val completedEventGoBackButton = findViewById<View>(R.id.completedEventGoBackButton)

        completedEventGoBackButton.setOnClickListener{
            finish()
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
                val eventData = snapshot.getValue<Event>()
                eventTitle.text = eventData!!.title
                eventLocation.text = eventData!!.location
                eventDate.text = eventData!!.date
                eventTime.text = eventData!!.time
                eventDescription.text = eventData!!.description
                eventCategory.text = eventData!!.category

            }
        })

        val needyNicknameReviewText = findViewById<TextView>(R.id.creatorNicknameReviewText)
        val volunteerNicknameReviewText = findViewById<TextView>(R.id.assigneeNicknameReviewText)

        val userTypeText = findViewById<TextView>(R.id.userTypeText)
        val userNicknameText = findViewById<TextView>(R.id.userNicknameText)

        // Get user info
        val userRef = database.child("users").child(uidNeedy!!)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("Header", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue<User>()

                needyNicknameReviewText.text = userData!!.nickname
                userNicknameText.text = userData!!.nickname
                userTypeText.text = userData!!.type
            }
        })


        val userTypeText2 = findViewById<TextView>(R.id.userTypeText2)
        val userNicknameText2 = findViewById<TextView>(R.id.userNicknameText2)

        // Get user info
        val userRef2 = database.child("users").child(uidVolunteer!!)
        userRef2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("Header", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue<User>()

                volunteerNicknameReviewText.text = userData!!.nickname
                userNicknameText2.text = userData!!.nickname
                userTypeText2.text = userData!!.type
            }
        })

        val intent = Intent(this@CompletedEventPage, ViewProfileActivity::class.java)

        userNicknameText.setOnClickListener{
            val arguments = Bundle().apply {
                putString("uid", uidNeedy)
            }
            intent.putExtras(arguments)
            startActivity(intent)
        }

        userNicknameText2.setOnClickListener{
            val arguments = Bundle().apply {
                putString("uid", uidVolunteer)
            }
            intent.putExtras(arguments)
            startActivity(intent)
        }

        // Reviews

        val averageHonestyRating = findViewById<RatingBar>(R.id.averageHonestyRating)
        val averagePunctualityRating = findViewById<RatingBar>(R.id.averagePunctualityRating)
        val averageAttitudeRating = findViewById<RatingBar>(R.id.averageAttitudeRating)


        if (eventToNeedyReviewId != "") {
            val needyReview = database.child("ratings").child(eventToNeedyReviewId!!)
            needyReview.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.w("Header", "loadUserData:onCancelled", error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val rating = snapshot.getValue<Rating>()

                        averageHonestyRating.rating = rating!!.honesty.toString().toFloat()
                        averagePunctualityRating.rating = rating!!.punctuality.toString().toFloat()
                        averageAttitudeRating.rating = rating!!.attitude.toString().toFloat()
                    }
                }
            })
        }

        val averageHonestyRating2 = findViewById<RatingBar>(R.id.averageHonestyRating2)
        val averagePunctualityRating2 = findViewById<RatingBar>(R.id.averagePunctualityRating2)
        val averageAttitudeRating2 = findViewById<RatingBar>(R.id.averageAttitudeRating2)

        if(eventToVolunteerReviewId != "") {

            val volunteerReview = database.child("ratings").child(eventToVolunteerReviewId!!)
            volunteerReview.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.w("Header", "loadUserData:onCancelled", error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val rating = snapshot.getValue<Rating>()

                    averageHonestyRating2.rating = rating!!.honesty.toString().toFloat()
                    averagePunctualityRating2.rating = rating!!.punctuality.toString().toFloat()
                    averageAttitudeRating2.rating = rating!!.attitude.toString().toFloat()
                }
            })
        }
    }
}