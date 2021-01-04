package com.e.thetogetherapp.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.forms.PasswordChangeFormState
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.databinding.ActivityCreateEventBinding
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateEventPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String? = null
        val extras: Bundle? = intent.extras

        if (extras != null) {
            uid = extras.getString("uid")
        }

        val binding : ActivityCreateEventBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_create_event)

        var eventType: String? = null
        var event: Event
        val database = Firebase.database.reference

        val userRef = database.child("users").child(uid!!)
        userRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val userType = snapshot.child("type").value.toString()

                eventType = if (userType == "needy"){
                    "request"
                } else{
                    "donation"
                }
            }
        })


        findViewById<Button>(R.id.submitCreateEventButton).setOnClickListener{
            event = binding.event!!

            val eventRef = database.child(eventType!!)
            val eventId = eventRef.push().key

            eventRef.child(eventId!!).setValue(event)
        }
    }
}