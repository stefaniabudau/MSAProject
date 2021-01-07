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

class EventUserCardFragment :Fragment(){

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TO DO : SEND UID
        val uid = "getUID"

        val view = inflater.inflate(R.layout.component_event_user, container, false)

        database = Firebase.database.reference
        auth = Firebase.auth

        val userTypeText = view.findViewById<TextView>(R.id.userTypeText)
        val userNicknameText = view.findViewById<TextView>(R.id.userNicknameText)

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
        return view
    }
}