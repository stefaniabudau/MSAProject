package com.example.waymaker.data

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.waymaker.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.IOException

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
                        val user = auth.currentUser
                        //updateUiWithUser(user)
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

    fun signup(username: String, password: String): Result<LoggedInUser>{
        auth = Firebase.auth

        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")

            Log.d("Hello", "There")

            auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Auth", "createUserWithEmail:success")
                        val user = auth.currentUser
                        //updateUiWithUser(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Auth", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        //updateUiWithUser(null)
                    }
                }

            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}

