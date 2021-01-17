package com.e.thetogetherapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.pages.AssignedEventPage
import com.e.thetogetherapp.pages.CompletedEventPage
import com.e.thetogetherapp.pages.UnassignedEventPage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SearchAdapter(private val dataSet:ArrayList<Event>, private val context: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

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

        holder.donationTitle.setOnClickListener{
            val arguments = Bundle().apply{
                putString("eventId", dataSet[position].id)
                putString("eventType", dataSet[position].type)
                putString("uidNeedy", dataSet[position].needy)
                putString("uidVolunteer", dataSet[position].volunteer)
            }

            var intent:Intent? = null
            intent = Intent(context, CompletedEventPage::class.java)
            intent.putExtras(arguments)
            context.startActivity(intent)


//            if (dataSet[position].status == "unassigned"){
//                intent = Intent(context, CompletedEventPage::class.java)
////                intent = Intent(context, UnassignedEventPage::class.java)
//
////                val userRef = Firebase.database.reference.child("users").child(Firebase.auth.currentUser!!.uid)
////                userRef.addValueEventListener(object : ValueEventListener{
////                    override fun onCancelled(error: DatabaseError) {
////                        TODO("Not yet implemented")
////                    }
////
////                    override fun onDataChange(snapshot: DataSnapshot) {
////                        val data = snapshot.child("type").value
////                        val arguments = Bundle().apply{
////                            putString("uid", Firebase.auth.currentUser!!.uid)
////                            putString("eventId", dataSet[position].id)
////                            putString("eventType", dataSet[position].type)
////                            putString("uidNeedy", dataSet[position].needy)
////                            putString("uidVolunteer", dataSet[position].volunteer)
////                            putString("userType", dataSet[position].volunteer)
////                        }
//                val arguments = Bundle().apply{
//                    putString("eventId", dataSet[position].id)
//                    putString("eventType", dataSet[position].type)
//                    putString("uidNeedy", dataSet[position].needy)
//                    putString("uidVolunteer", dataSet[position].volunteer)
//                }
//                intent!!.putExtras(arguments)
//            }
//
//            else if (dataSet[position].status == "pending"){
//                intent = Intent(context, CompletedEventPage::class.java)
////                intent = Intent(context, AssignedEventPage::class.java)
////                val userRef = Firebase.database.reference.child("users").child(Firebase.auth.currentUser!!.uid)
////                userRef.addValueEventListener(object : ValueEventListener{
////                    override fun onCancelled(error: DatabaseError) {
////                        TODO("Not yet implemented")
////                    }
////
////                    override fun onDataChange(snapshot: DataSnapshot) {
////                        val data = snapshot.child("type").value
////                        val arguments = Bundle().apply{
////                            putString("eventId", dataSet[position].id)
////                            putString("eventType", dataSet[position].type)
////                            putString("uidNeedy", dataSet[position].needy)
////                            putString("uidVolunteer", dataSet[position].volunteer)
////                            putString("userType", data.toString())
////                        }
//                        val arguments = Bundle().apply{
//                            putString("eventId", dataSet[position].id)
//                            putString("eventType", dataSet[position].type)
//                            putString("uidNeedy", dataSet[position].needy)
//                            putString("uidVolunteer", dataSet[position].volunteer)
//                        }
//                        intent!!.putExtras(arguments)
//                    }
//
//
//            else if (dataSet[position].status == "done"){
//                intent = Intent(context, CompletedEventPage::class.java)
//                val arguments = Bundle().apply{
//                    putString("eventId", dataSet[position].id)
//                    putString("eventType", dataSet[position].type)
//                    putString("uidNeedy", dataSet[position].needy)
//                    putString("uidVolunteer", dataSet[position].volunteer)
//                }
//                intent.putExtras(arguments)
//            }

//            context.startActivity(intent)
        }


    }

}