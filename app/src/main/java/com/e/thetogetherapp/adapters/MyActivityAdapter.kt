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
import com.e.thetogetherapp.pages.CompletedEventPage


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

        holder.donationTitle.setOnClickListener{
            val arguments = Bundle().apply{
                putString("eventId", dataSet[position].id)
                putString("eventType", dataSet[position].type)
                putString("needyId", dataSet[position].needy)
                putString("volunteerId", dataSet[position].volunteer)
            }

            var intent:Intent? = null

            when(dataSet[position].status){
                "unassigned" -> intent = Intent(context, CompletedEventPage::class.java)
                "pending" -> intent = Intent(context, CompletedEventPage::class.java)
                "done" -> intent = Intent(context, CompletedEventPage::class.java)
            }

            intent!!.putExtras(arguments)
            context.startActivity(intent)
        }


    }

}