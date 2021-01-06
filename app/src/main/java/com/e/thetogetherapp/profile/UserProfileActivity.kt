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
import com.google.firebase.database.*
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

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigationBar)
        navigationBar.setSelectedItemId(R.id.home)

        navigationBar.setOnNavigationItemSelectedListener {
            val user = Bundle()
            user.putString("userType", userType)
            user.putString("uid", uid)

                when (it.itemId) {
                    R.id.home -> {
                        true
                    }
                    R.id.notifications -> {
                        val intent = Intent(this@UserProfileActivity, NotificationPage::class.java)
                        intent.putExtras(user)
                        startActivity(intent)
                        true
                    }
                    R.id.search -> {
                        if(userType.equals("needy")){
                            val intent = Intent(this@UserProfileActivity, SearchRequestsPage::class.java)
                            intent.putExtras(user)
                            startActivity(intent)
                        }
                        if(userType.equals("volunteer")){
                            val intent = Intent(this@UserProfileActivity, SearchDonationsPage::class.java)
                            intent.putExtras(user)
                            startActivity(intent)
                        }
                        true
                    }
                    R.id.addNew -> {
                        val intent = Intent(this@UserProfileActivity, CreateEventPage::class.java)
                        intent.putExtras(user)
                        startActivity(intent)
                        true
                    }
                    else->false
                }
        }

        // BUTTONS -------------------------------------------------------------------------------------

        myActivityButton.setOnClickListener{
            startActivity(Intent(this@UserProfileActivity, MyActivityPage::class.java))
        }

        goToSettingsButton.setOnClickListener{
            startActivity(Intent(this@UserProfileActivity, SettingsPage::class.java))
        }

        notificationCardButton.setOnClickListener{
            val user = Bundle()
            user.putString("userType", userType)
            user.putString("uid", uid)
            val intent = Intent(this@UserProfileActivity, NotificationPage::class.java)
            intent.putExtras(user)
            startActivity(intent)
        }

        reviewCardButton.setOnClickListener{
            val intent = Intent(this@UserProfileActivity, MyReviewsPage::class.java)
            val param = Bundle().apply { putString("uid", uid) }
            intent.putExtras(param)

            startActivity(intent)
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