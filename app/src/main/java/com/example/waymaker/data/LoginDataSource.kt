package com.example.waymaker.data

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.waymaker.R
import com.example.waymaker.data.model.LoggedInUser
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.google.firebase.auth.ktx.auth
import java.io.IOException

//import com.google.firebase.quickstart.auth.R
//import com.google.firebase.quickstart.auth.databinding.ActivityEmailpasswordBinding

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

class LoginDataSource : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    fun login(username: String, password: String): Result<LoggedInUser> {
        auth = Firebase.auth

        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")

            Log.d("Hello", "There")

            auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                        val user = auth.currentUser
                        Log.d("Auth", "Success")
                    }
                    else{
                        Log.d("Auth", "Fail")

                    }

//                    // [START_EXCLUDE]
//                    if (!task.isSuccessful) {
//                        binding.status.setText(R.string.auth_failed)
//                    }
//                    hideProgressBar()
//                    // [END_EXCLUDE]
                }

            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

