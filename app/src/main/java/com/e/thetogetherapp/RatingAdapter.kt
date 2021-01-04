package com.e.thetogetherapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.thetogetherapp.data.model.Rating
import com.e.thetogetherapp.profile.ViewProfileActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RatingAdapter(private val dataSet:ArrayList<Rating>, private val context: Context) :
    RecyclerView.Adapter<RatingAdapter.ViewHolder>() {

    private lateinit var database: DatabaseReference

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reviewName: TextView
        val honestyReview: RatingBar
        val punctualityReview: RatingBar
        val attitudeReview: RatingBar

        init {
            reviewName = view.findViewById(R.id.nameReview)
            honestyReview = view.findViewById(R.id.individualHonestyRating)
            punctualityReview = view.findViewById(R.id.individualPunctualityRating)
            attitudeReview = view.findViewById(R.id.individualAttitudeRating)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.component_individual_review, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        database = Firebase.database.reference

        val ref = database.child("users").child(dataSet[position].from.toString())
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.w("RatingAdapter", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val nickname = snapshot.child("nickname").value.toString()

                viewHolder.reviewName.text = nickname
            }
        })

//        viewHolder.reviewName.setOnClickListener{
//            val intent = Intent(context, ViewProfileActivity::class.java)
//            val extras = Bundle().apply { putString("uid", dataSet[position].from.toString()) }
//
//            intent.putExtras(extras)
//            context.startActivity(intent)
//        }
        viewHolder.honestyReview.rating = dataSet[position].honesty!!.toFloat()
        viewHolder.punctualityReview.rating = dataSet[position].punctuality!!.toFloat()
        viewHolder.attitudeReview.rating = dataSet[position].attitude!!.toFloat()
    }

    override fun getItemCount() = dataSet.size

}
