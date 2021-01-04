package com.e.thetogetherapp.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.pages.MyRequestsPage
import com.e.thetogetherapp.pages.MyReviewsPage
import com.e.thetogetherapp.pages.SettingsPage
import com.google.firebase.database.*
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
//
        setContentView(R.layout.activity_profile_needy)


//
//        val requests= findViewById<TextView>(R.id.needyNoOfRequests)
//        val donations= findViewById<TextView>(R.id.needyNoOfDonations)
//        val activities= findViewById<TextView>(R.id.needyNoOfOngoingActivities)
//
        val myRequests = findViewById<Button>(R.id.myRequestsButton)
//
//        // PROFILE BANNER
//
//       database.addValueEventListener(object : ValueEventListener{
//           override fun onCancelled(error: DatabaseError) {
//               Log.w(TAG, "loadBannerData:onCancelled", error.toException())
//           }
//
//           override fun onDataChange(snapshot: DataSnapshot) {
//               val allRequests = snapshot.child("requests").children
//               val userRequests = allRequests.filter { it.child("needy").value == uid }
//               val ongoingRequests = userRequests.filter { it.child("status").value == "ongoing" }
//
//               val allDonations = snapshot.child("donations").children
//               val userDonations = allDonations.filter { it.child("needy").value == uid }
//               val ongoingDonations = userDonations.filter { it.child("status").value == "ongoing" }
//
//               val ongoingActivities = ongoingDonations.count() + ongoingRequests.count()
//
//               requests.text = userRequests.count().toString()
//               donations.text = userDonations.count().toString()
//               activities.text = ongoingActivities.toString()
//
//           }
//       })

//        TODO: handle requests page transition
        // MY REQUESTS

        myRequests.setOnClickListener{
            val intent= Intent(this@NeedyProfileActivity, MyReviewsPage::class.java)
            val extras = Bundle().apply { putString("uid", uid) }
            intent.putExtras(extras)
            startActivity(intent)
        }

        // REVIEWS AND RATINGS

//        val ratingsHonesty = findViewById<RatingBar>(R.id.averageHonestyRating)
//        val ratingsAttitude = findViewById<RatingBar>(R.id.averageAttitudeRating)
//        val ratingsPunctuality = findViewById<RatingBar>(R.id.averagePunctualityRating)
//        val nrRatings = findViewById<TextView>(R.id.reviewsNumber)
//
//
//        val ratingsRef = database.child("ratings")
//        ratingsRef.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "loadRatings:onCancelled", error.toException())
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val ratings = snapshot.children
//                val userRatings = ratings.filter { it.child("to").value == uid }
//                val numberOfRatings = userRatings.count()
//
//                if (numberOfRatings == 0){
//                    ratingsAttitude.rating = 0F
//                    ratingsPunctuality.rating = 0F
//                    ratingsHonesty.rating = 0F
//                    nrRatings.text = "0"
//                }
//                else{
//                    fun getAverageRating(ratings:List<DataSnapshot>, characteristic:String, n:Int): Float{
//                        return ratings.map { it.child(characteristic).value.toString().toFloat() }.sum() / n
//                    }
//
//                    ratingsAttitude.rating = getAverageRating(userRatings, "attitude", numberOfRatings)
//                    ratingsPunctuality.rating = getAverageRating(userRatings, "punctuality", numberOfRatings)
//                    ratingsHonesty.rating = getAverageRating(userRatings, "honesty", numberOfRatings)
//                    nrRatings.text = numberOfRatings.toString()
//                }
//
//            }
//        })

//        val goToReviewsPage = findViewById<TextView>(R.id.goToReviewsPageArrow)
//
////        TODO: handle review page transition
//        goToReviewsPage.setOnClickListener{
//            startActivity(Intent(this@NeedyProfileActivity, SettingsPage::class.java))
//        }


    }

    companion object {
        private const val TAG = "NeedyProfileActivity"
    }

}