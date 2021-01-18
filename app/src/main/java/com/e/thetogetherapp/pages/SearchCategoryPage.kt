package com.e.thetogetherapp.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.thetogetherapp.R
import com.e.thetogetherapp.adapters.SearchAdapter
import com.e.thetogetherapp.data.model.Event
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_search_event_category.*

class SearchCategoryPage: AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var location: String ? = null
        var category: String ? = null
        var type: String ? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            location = extras.getString("location")
            category = extras.getString("category")
            type = extras.getString("type")
        }

        setContentView(R.layout.activity_search_event_category)

        linearLayoutManager = LinearLayoutManager(this)
        categoryRecyclerView.layoutManager = linearLayoutManager

        val database = Firebase.database.reference
        var categoryEvents = ArrayList<Event>()

        val categoryNameEvents = findViewById<TextView>(R.id.categoryNameEvents)
        categoryNameEvents.setText(category)

        if (location != null){
            val nearYouEventsRef = database.child(type!!)
            nearYouEventsRef.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Log.d("SearchCategory", "loadLocationEventData:onCancelled")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val allEvents = snapshot.children
                    val nearYouEvents = allEvents.filter { it.child("location").value == location
                            && it.child("status").value == "unassigned" }

                    nearYouEvents.forEach{
                        val event = it.getValue<Event>()
                        categoryEvents.add(event!!)
                    }

                    adapter = SearchAdapter(
                        categoryEvents,
                        this@SearchCategoryPage
                    )

                    categoryRecyclerView.adapter = adapter
                }
            })
        }
        else{
            val events = database.child(type!!)
            events.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Log.d("SearchCategory", "loadEventData:onCancelled")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val allEvents = snapshot.children
                    val eventsByCategory = allEvents.filter { it.child("category").value == category
                            && it.child("status").value == "unassigned" }

                    eventsByCategory.forEach{
                        val event = it.getValue<Event>()
                        categoryEvents.add(event!!)
                    }

                    adapter = SearchAdapter(
                        categoryEvents,
                        this@SearchCategoryPage
                    )

                    categoryRecyclerView.adapter = adapter
                }
            })
        }

        findViewById<View>(R.id.categorySearchBackButton).setOnClickListener{
            finish()
        }
    }

    override fun onRestart(){
        super.onRestart()
        finish()
        startActivity(getIntent())
    }
}