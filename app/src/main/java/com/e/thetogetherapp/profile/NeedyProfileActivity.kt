package com.e.thetogetherapp.profile

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NeedyProfileActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
        }

        database = Firebase.database.reference

        setContentView(R.layout.activity_needy_profile)

        val requests= findViewById<TextView>(R.id.needyNoOfRequests)
        val donations= findViewById<TextView>(R.id.needyNoOfDonations)
        val activities= findViewById<TextView>(R.id.needyNoOfOngoingActivities)

        val myRequests = findViewById<Button>(R.id.myRequestsButton)


        val requestsRef = database.child("requests")
        requestsRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadRequests:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val allRequests = snapshot.children
                val userRequests = allRequests.filter { (it.value as Map<*,*>)["needy"] == uid }.count()
                requests.text = userRequests.toString()
            }
        })


        val donationsRef = database.child("donations")
        donationsRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadDonations:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val allDonations = snapshot.children
                val userDonations = allDonations.filter { (it.value as Map<*,*>)["needy"] == uid }.count()
                donations.text = userDonations.toString()
            }
        })


    }

    companion object {
        private const val TAG = "NeedyProfileActivity"
    }

}