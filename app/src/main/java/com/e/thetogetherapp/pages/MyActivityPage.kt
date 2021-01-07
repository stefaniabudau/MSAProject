package com.e.thetogetherapp.pages

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.thetogetherapp.R
import com.e.thetogetherapp.adapters.MyActivityAdapter
import com.e.thetogetherapp.data.model.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_activity.*

class MyActivityPage: AppCompatActivity (){

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: MyActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String? = null
        var userType: String? = null
        val extras: Bundle? = intent.extras

        if (extras != null) {
            uid = extras.getString("uid")
            userType = extras.getString("userType")
        }

        setContentView(R.layout.activity_my_activity)

        val database = Firebase.database.reference

        database.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val allDonations = snapshot.child("donations").children
                val allRequests = snapshot.child("requests").children
                val userRequests = allRequests.filter { it.child(userType!!).value == uid!!}
                val userDonations = allDonations.filter { it.child(userType!!).value == uid!!}

                val userActivity = userDonations + userRequests

                val unassigned = userActivity.filter { it.child("status").value == "unassigned" }.count()
                val pending = userActivity.filter { it.child("status").value == "pending" }.count()
                val done = userActivity.filter { it.child("status").value == "done" }.count()

                findViewById<TextView>(R.id.myActivityRequestsNumber).setText(unassigned.toString())
                findViewById<TextView>(R.id.myActivityDonationsNumber).setText(pending.toString())
                findViewById<TextView>(R.id.myActivityActivitiesNumber).setText(done.toString())
            }
        })


        linearLayoutManager = LinearLayoutManager(this)
        eventsRecyclerView.layoutManager = linearLayoutManager

        var myActivitiesArray = ArrayList<Event>()
        var status:String? = null

        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("MyActivityPage", "loadActivities:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                myActivitiesArray = ArrayList<Event>()
                val allDonations = snapshot.child("donations").children
                val userDonations = allDonations.filter { it.child(userType!!).value == uid!!
                        && it.child("status").value == status }

                val allRequests = snapshot.child("requests").children
                val userRequests = allRequests.filter { it.child(userType!!).value == uid!!
                        && it.child("status").value == status }

                val userActivity = userDonations + userRequests
                userActivity.forEach{
                    val activity = it.getValue<Event>()
                    myActivitiesArray.add(activity!!)
                }

                adapter = MyActivityAdapter(
                    myActivitiesArray,
                    this@MyActivityPage
                )

                eventsRecyclerView.adapter = adapter
            }
        }

        findViewById<Button>(R.id.unassignedActivities).setOnClickListener{
            status = "unassigned"
            database.addValueEventListener(listener)
        }

        findViewById<Button>(R.id.pendingActivities).setOnClickListener{
            status = "pending"
            database.addValueEventListener(listener)
        }

        findViewById<Button>(R.id.completedActivities).setOnClickListener{
            status = "done"
            database.addValueEventListener(listener)
        }


        findViewById<View>(R.id.myActivityBackButton).setOnClickListener{
            finish()
        }

    }
}