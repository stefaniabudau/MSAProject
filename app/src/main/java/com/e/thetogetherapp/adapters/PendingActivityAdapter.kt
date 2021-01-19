package com.e.thetogetherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.Event

class PendingActivityAdapter(private val dataSet:ArrayList<Event>, private val context: Context) :
    RecyclerView.Adapter<PendingActivityAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventCategory: TextView
        val eventType: TextView
        val eventTitle: TextView

        init {

            eventCategory = view.findViewById(R.id.ongoingCategory)
            eventType = view.findViewById(R.id.ongoingType)
            eventTitle = view.findViewById(R.id.ongoingTitle)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.component_ongoing, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.eventCategory.text  = dataSet[position].category
        holder.eventType.text = dataSet[position].type
        holder.eventTitle.text = dataSet[position].title
    }


}