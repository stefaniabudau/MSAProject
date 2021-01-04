package com.e.thetogetherapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.e.thetogetherapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AverageReviewCardFragment :Fragment(){

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.card_review, container, false)

        database = Firebase.database.reference
        auth = Firebase.auth

        val ratingsHonesty = view.findViewById<RatingBar>(R.id.averageHonestyRating)
        val ratingsAttitude = view.findViewById<RatingBar>(R.id.averageAttitudeRating)
        val ratingsPunctuality = view.findViewById<RatingBar>(R.id.averagePunctualityRating)
        val nrRatings = view.findViewById<TextView>(R.id.reviewsNumber)

        val ratingsRef = database.child("ratings")
        ratingsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("AverageReviewCard", "loadRatings:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val uid = auth.currentUser!!.uid

                val ratings = snapshot.children
                val userRatings = ratings.filter { it.child("to").value == uid }
                val numberOfRatings = userRatings.count()

                if (numberOfRatings == 0){
                    ratingsAttitude.rating = 0F
                    ratingsPunctuality.rating = 0F
                    ratingsHonesty.rating = 0F
                    nrRatings.text = "0"
                }
                else{
                    fun getAverageRating(ratings:List<DataSnapshot>, characteristic:String, n:Int): Float{
                        return ratings.map { it.child(characteristic).value.toString().toFloat() }.sum() / n
                    }

                    ratingsAttitude.rating = getAverageRating(userRatings, "attitude", numberOfRatings)
                    ratingsPunctuality.rating = getAverageRating(userRatings, "punctuality", numberOfRatings)
                    ratingsHonesty.rating = getAverageRating(userRatings, "honesty", numberOfRatings)
                    nrRatings.text = numberOfRatings.toString()
                }

            }
        })


        return view
    }
}