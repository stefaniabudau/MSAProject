package com.e.thetogetherapp.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.data.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class UserProfileActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
        }

        val databaseRef = Firebase.database.reference.child("users").child(uid!!)
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Log.w("UserProfileActivity", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val userType:String = (snapshot.value as Map<*, *>)["type"].toString()
                val intent: Intent

                val extras = Bundle()
                extras.putString("uid", uid)

                intent = if(userType == "needy") {
                    Intent(this@UserProfileActivity, NeedyProfileActivity::class.java)
                } else{
                    Intent(this@UserProfileActivity, VolunteerProfileActivity::class.java)
                }

                intent.putExtras(extras)
                startActivity(intent)
            }
        })

//        finish()
    }


}