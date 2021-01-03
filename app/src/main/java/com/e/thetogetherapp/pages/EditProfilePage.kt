package com.e.thetogetherapp.pages

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.User
import com.e.thetogetherapp.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class EditProfilePage:AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Firebase.database.reference
        auth = Firebase.auth

        val binding : ActivityEditProfileBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_edit_profile)

        val submitButton = findViewById<Button>(R.id.saveEditProfileButton)

        val ref = database.child("users").child(auth.currentUser!!.uid)
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.w("EditProfile", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                binding.user = user
            }
        })

        submitButton.setOnClickListener{
            ref.updateChildren(binding.user!!.toMap())
        }


    }
}