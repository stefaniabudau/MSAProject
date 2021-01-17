package com.e.thetogetherapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.data.model.User
import com.e.thetogetherapp.pages.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class MyActivityAdapter(private val dataSet:ArrayList<Event>, private val context: Context) :
    RecyclerView.Adapter<MyActivityAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val donationTitle: TextView
        val eventLocation: TextView

        init {
            donationTitle = view.findViewById(R.id.donationTitle)
            eventLocation = view.findViewById(R.id.eventLocation)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.component_event, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.eventLocation.text = dataSet[position].location!!
        holder.donationTitle.text = dataSet[position].title!!

        var intent:Intent? = null
        var user: User? = null

        val uid = Firebase.auth.currentUser!!.uid
        val userRef = Firebase.database.reference.child("users").child(uid)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("MyActivityAdapter", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue<User>()
            }
        })


        holder.donationTitle.setOnClickListener{

            if (dataSet[position].status == "unassigned"){
                intent = Intent(context, UnassignedEventCreatorPage::class.java)

                val arguments = Bundle().apply{
                    putString("uid", uid)
                    putString("eventId", dataSet[position].id)
                    putString("eventType", dataSet[position].type)
                    putString("userType", user!!.type)
                }
                intent!!.putExtras(arguments)
                context.startActivity(intent)
            }

            else if (dataSet[position].status == "pending"){
                intent = Intent(context, AssignedEventCreatorPage::class.java)

                val arguments = Bundle().apply{
                    putString("eventId", dataSet[position].id)
                    putString("eventType", dataSet[position].type)
                    putString("uidNeedy", dataSet[position].needy)
                    putString("uidVolunteer", dataSet[position].volunteer)
                    putString("userType", user!!.type)
                }
                intent!!.putExtras(arguments)
                context.startActivity(intent)
            }

            else if (dataSet[position].status == "done"){
                intent = Intent(context, CompletedEventPage::class.java)

                val arguments = Bundle().apply{
                    putString("eventId", dataSet[position].id)
                    putString("eventType", dataSet[position].type)
                    putString("uidNeedy", dataSet[position].needy)
                    putString("uidVolunteer", dataSet[position].volunteer)
                    putString("eventToNeedyReviewId", dataSet[position].needyReview)
                    putString("eventToVolunteerReviewId", dataSet[position].volunteerReview)
                }
                intent!!.putExtras(arguments)
                context.startActivity(intent)
            }

        }


    }

}