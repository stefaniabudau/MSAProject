package com.e.thetogetherapp.profile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.R
import com.e.thetogetherapp.data.model.User
import com.e.thetogetherapp.pages.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class UserProfileActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uid: String ? = null
        var userType: String ? = null
        val extras: Bundle? = intent.extras

        if(extras != null){
            uid = extras.getString("uid")
            userType = extras.getString("userType")
        }

        database = Firebase.database.reference
        setContentView(R.layout.activity_profile)

        val nickname = findViewById<TextView>(R.id.userNicknameProfileText)
        val type = findViewById<TextView>(R.id.userTypeProfileText)

        val requests = findViewById<TextView>(R.id.needyNoOfRequests)
        val donations = findViewById<TextView>(R.id.needyNoOfDonations)
        val activities = findViewById<TextView>(R.id.needyNoOfOngoingActivities)

        val myActivityButton = findViewById<Button>(R.id.myActivityButton)
        val goToSettingsButton = findViewById<View>(R.id.goToSettingsButton)
        val notificationCardButton = findViewById<View>(R.id.notificationCardButton)
        val reviewCardButton = findViewById<View>(R.id.reviewCardButton)

        //NAVIGATION BAR ------------------------------------------------------------------------------

        val navigationBar = findViewById<View>(R.id.navigationBar) as BottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        true
                    }
                    R.id.notifications -> {

                        val user = Bundle()
                        val intent = Intent(this@UserProfileActivity, NotificationPage::class.java)

                        user.putString("userType", userType)
                        user.putString("uid", uid)
                        intent.putExtras(user)

                        startActivity(intent)
                        true
                    }
                    R.id.search -> {
                        if(userType=="needy"){
                            val user = Bundle()
                            val intent = Intent(this@UserProfileActivity, SearchRequestsPage::class.java)

                            user.putString("userType", userType)
                            user.putString("uid", uid)
                            intent.putExtras(user)

                            startActivity(intent)
                        }
                        if(userType=="volunteer"){
                            val user = Bundle()
                            val intent = Intent(this@UserProfileActivity, SearchDonationsPage::class.java)

                            user.putString("userType", userType)
                            user.putString("uid", uid)
                            intent.putExtras(user)

                            startActivity(intent)
                        }
                        true
                    }
                    R.id.addNew -> {
                        val user = Bundle()
                        val intent = Intent(this@UserProfileActivity, CreateEventPage::class.java)

                        user.putString("userType", userType)
                        user.putString("uid", uid)
                        intent.putExtras(user)

                        startActivity(intent)
                        true
                    }
                    else->false
                }
        }

        // BUTTONS -------------------------------------------------------------------------------------

        myActivityButton.setOnClickListener{
            startActivity(Intent(this@UserProfileActivity, MyRequestsPage::class.java))
        }

        goToSettingsButton.setOnClickListener{
            startActivity(Intent(this@UserProfileActivity, SettingsPage::class.java))
        }

        notificationCardButton.setOnClickListener{
            startActivity(Intent(this@UserProfileActivity, NotificationPage::class.java))
        }

        reviewCardButton.setOnClickListener{
            startActivity(Intent(this@UserProfileActivity, ReviewPage::class.java))
        }

        // PROFILE BANNER ----------------------------------------------------------------------------

        val userRef = database.child("users").child(uid!!)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("Header", "loadUserData:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue<User>()

                nickname.text = userData!!.nickname
                type.text = userData!!.type
            }
        })

        database.addValueEventListener(object : ValueEventListener{
           override fun onCancelled(error: DatabaseError) {
               Log.w(TAG, "loadBannerData:onCancelled", error.toException())
           }

           override fun onDataChange(snapshot: DataSnapshot) {
               val userType = snapshot.child("users").child(uid!!).child("type").value.toString()

               val allRequests = snapshot.child("requests").children
               val userRequests = allRequests.filter { it.child(userType).value == uid }
               val ongoingRequests = userRequests.filter { it.child("status").value == "ongoing" }

               val allDonations = snapshot.child("donations").children
               val userDonations = allDonations.filter { it.child(userType).value == uid }
               val ongoingDonations = userDonations.filter { it.child("status").value == "ongoing" }

               val ongoingActivities = ongoingDonations.count() + ongoingRequests.count()

               requests.text = userRequests.count().toString()
               donations.text = userDonations.count().toString()
               activities.text = ongoingActivities.toString()
           }
       })

        // REVIEWS AND RATINGS -----------------------------------------------------------------------------

        val ratingsHonesty = findViewById<RatingBar>(R.id.averageHonestyRating)
        val ratingsAttitude = findViewById<RatingBar>(R.id.averageAttitudeRating)
        val ratingsPunctuality = findViewById<RatingBar>(R.id.averagePunctualityRating)
        val nrRatings = findViewById<TextView>(R.id.reviewsNumber)

        val ratingsRef = database.child("ratings")
        ratingsRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadRatings:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val ratings = snapshot.children
                val userRatings = ratings.filter { it.child("to").value == uid }
                val numberOfRatings = userRatings.count()

                if (numberOfRatings == 0){
                    ratingsAttitude.rating = 0F
                    ratingsPunctuality.rating = 0F
                    ratingsHonesty.rating = 0F
                    nrRatings.text = "0"
                }
                else{
                    fun getAverageRating(ratings:List<DataSnapshot>, characteristic:String, n:Int): Float{
                        return ratings.map { it.child(characteristic).value.toString().toFloat() }.sum() / n
                    }

                    ratingsAttitude.rating = getAverageRating(userRatings, "attitude", numberOfRatings)
                    ratingsPunctuality.rating = getAverageRating(userRatings, "punctuality", numberOfRatings)
                    ratingsHonesty.rating = getAverageRating(userRatings, "honesty", numberOfRatings)
                    nrRatings.text = numberOfRatings.toString()
                }

            }
        })
    }

    companion object {
        private const val TAG = "UserProfileActivity"
    }

}