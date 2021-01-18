package com.e.thetogetherapp.pages


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.data.model.Rating
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class GiveReviewPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userType: String? = null
        var fromUser: String? = null
        var toUser: String? = null
        var eventId: String? = null
        var eventType: String? = null

        val extras: Bundle? = intent.extras

        if(extras != null){
            userType = extras.getString("userType")
            fromUser = extras.getString("from")
            toUser = extras.getString("to")
            eventId = extras.getString("eventId")
            eventType = extras.getString("eventType")
        }

        setContentView(com.e.thetogetherapp.R.layout.activity_give_review)

        val ratingHonestyRatingBar = findViewById<View>(com.e.thetogetherapp.R.id.ratingHonestyRatingBar) as RatingBar
        val ratingPunctualityRatingBar = findViewById<View>(com.e.thetogetherapp.R.id.ratingPunctualityRatingBar) as RatingBar
        val ratingAttitudeRatingBar = findViewById<View>(com.e.thetogetherapp.R.id.ratingAttitudeRatingBar) as RatingBar

        val submitButton = findViewById<View>(com.e.thetogetherapp.R.id.submitButton) as Button
        val giveReviewBackButton = findViewById<View>(com.e.thetogetherapp.R.id.giveReviewBackButton)

        // BUTTONS ---------------------------------------------------------------------------

        giveReviewBackButton.setOnClickListener{
            finish()
        }

        submitButton.setOnClickListener {
            val ratingRef = Firebase.database.reference.child("ratings")
            val ratingId = ratingRef.push().key.toString()

            val userRating = Rating(
                id=ratingId,
                from=fromUser.toString(),
                to=toUser.toString(),
                honesty=ratingHonestyRatingBar.rating.toString(),
                attitude=ratingAttitudeRatingBar.rating.toString(),
                punctuality=ratingPunctualityRatingBar.rating.toString()
            )

            ratingRef.child(ratingId).setValue(userRating)

            val eventRef = Firebase.database.reference.child(eventType!!).child(eventId!!)

            if(userType == "needy"){
                eventRef.child("volunteerReview").setValue(ratingId)
            }
            else if(userType == "volunteer"){
                eventRef.child("needyReview").setValue(ratingId)
            }

            eventRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.w("GiveReviewPage", "loadEventData:onCancelled", error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val event = snapshot.getValue<Event>()

                    if(event?.volunteerReview != "" &&
                            event?.needyReview != ""){
                        eventRef.child("status").setValue("done")
                    }
                }
            })

            finish()
        }
    }
}