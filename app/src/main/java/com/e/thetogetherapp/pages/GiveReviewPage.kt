package com.e.thetogetherapp.pages


import android.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GiveReviewPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.e.thetogetherapp.R.layout.activity_give_review)

        val ratingHonestyRatingBar = findViewById<View>(com.e.thetogetherapp.R.id.ratingHonestyRatingBar) as RatingBar
        val ratingPunctualityRatingBar = findViewById<View>(com.e.thetogetherapp.R.id.ratingPunctualityRatingBar) as RatingBar
        val ratingAttitudeRatingBar = findViewById<View>(com.e.thetogetherapp.R.id.ratingAttitudeRatingBar) as RatingBar

        val submitButton = findViewById<View>(com.e.thetogetherapp.R.id.submitButton) as Button
        val giveReviewBackButton = findViewById<View>(com.e.thetogetherapp.R.id.giveReviewBackButton)

        // BUTTONS ---------------------------------------------------------------------------

        giveReviewBackButton.setOnClickListener{
            finish()
        }

        submitButton.setOnClickListener {
            ratingHonestyRatingBar.rating
            ratingPunctualityRatingBar.rating
            ratingAttitudeRatingBar.rating
        }
    }
}