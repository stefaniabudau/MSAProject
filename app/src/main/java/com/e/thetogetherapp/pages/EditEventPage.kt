package com.e.thetogetherapp.pages

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.databinding.ActivityEditEventBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class EditEventPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var eventId: String? = null
        var eventType: String? = null

        val extras: Bundle? = intent.extras

        if (extras != null) {
            eventId = extras.getString("eventId")
            eventType = extras.getString("eventType")
        }

        val binding : ActivityEditEventBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_edit_event)

        val database = Firebase.database.reference


        // FORM

        val eventRef = database.child(eventType!!).child(eventId!!)
        eventRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.d("CrateEventPage", "eventDataLoad:onCancelled")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
               val event = snapshot.getValue<Event>()
                binding.event = event
            }
        })


     // SPINNER

        val categorySpinner = findViewById<Spinner>(R.id.editEventCategorySpinner)
        val categoryList = ArrayList<String>()

        val categoryRef = database.child(eventType!!).child("category")

        categoryRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.d("CrateEventPage", "categoryList:onCancelled")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    categoryList.add(it.value.toString())
                }
            }
        })


        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("EditEventPage", "categoryList:onNothingSelected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> {}
                    1 -> binding.event!!.category = categoryList[1]
                    2 -> binding.event!!.category = categoryList[2]
                    3 -> binding.event!!.category = categoryList[3]
                    4 -> binding.event!!.category = categoryList[4]
                    5 -> binding.event!!.category = categoryList[5]
                    6 -> binding.event!!.category = categoryList[6]
                }
            }
        }

    // BUTTONS

        findViewById<Button>(R.id.saveEditEventButton).setOnClickListener{
            eventRef.updateChildren(binding.event!!.toMap())
        }

        findViewById<View>(R.id.editEventBackButton).setOnClickListener{
            finish()
        }





    }
}