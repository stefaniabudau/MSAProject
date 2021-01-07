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
import com.e.thetogetherapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class EventReviewsCardFragment :Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TO DO : SEND eventId
        val eventId = "getEvent"

        val view = inflater.inflate(R.layout.component_event_details, container, false)

        database = Firebase.database.reference
        auth = Firebase.auth

        val eventTitle = view.findViewById<TextView>(R.id.eventTitle)
        val eventLocation = view.findViewById<TextView>(R.id.eventLocation)
        val eventDate = view.findViewById<TextView>(R.id.eventDate)
        val eventTime = view.findViewById<TextView>(R.id.eventTime)
        val eventDescription = view.findViewById<TextView>(R.id.eventDescription)
        val eventCategory = view.findViewById<TextView>(R.id.eventCategory)

        // Get user info
        return view
    }
}