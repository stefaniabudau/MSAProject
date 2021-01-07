package com.e.thetogetherapp.data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.data.model.LoggedInUser
import com.e.thetogetherapp.pages.CreateEventPage
import com.e.thetogetherapp.pages.EditEventPage
import com.e.thetogetherapp.profile.UserProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.IOException

class LoginDataSource(private val context: Context): AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var loggedInUser: LoggedInUser? = null

    fun login(username: String, password: String): Result<LoggedInUser> {

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                Log.d("Login", "signIn:onComplete:" + task.isSuccessful)

                if (task.isSuccessful) {
//                    loggedInUser = LoggedInUser(task.result?.user!!.uid, username)
                    val user = Bundle()
                    val intent = Intent(context, UserProfileActivity::class.java)
                    user.putString("uid", task.result?.user!!.uid)
                    intent.putExtras(user)

                    context.startActivity(intent)
                    finish()
                }
            }

        if(auth.currentUser!= null) {
            loggedInUser = LoggedInUser(auth.currentUser!!.uid, username)
            return Result.Success(loggedInUser!!)
        }
        else return Result.Error(IOException("Error logging in"))
    }

    fun logout() {
        auth.signOut()
    }
}