package com.e.thetogetherapp.pages

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.thetogetherapp.adapters.RatingAdapter
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.Rating
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_reviews.*

class MyReviewsPage: AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RatingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ratingsArray = ArrayList<Rating>()
        var uid: String? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
        }

        setContentView(R.layout.activity_reviews)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager


        val backButton = findViewById<View>(R.id.reviewsPageBackButton)
        backButton.setOnClickListener{
            finish()
        }

        database = Firebase.database.reference

        val requestsRef = database.child("ratings")
        requestsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("MyReviewsPage", "loadRatings:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val allRatings = snapshot.children
                val userRatings = allRatings.filter { it.child("to").value == uid!! }

                userRatings.forEach {
                    val rating = it.getValue<Rating>()
                    ratingsArray.add(rating!!)
                }

                findViewById<TextView>(R.id.pageSeparatorText).setText(userRatings.count().toString() + "REVIEWS")

                adapter = RatingAdapter(
                    ratingsArray,
                    this@MyReviewsPage
                )
                recyclerView.adapter = adapter
            }
        })


    }
}