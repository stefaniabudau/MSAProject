package com.e.thetogetherapp.data

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.IOException

class LoginDataSource: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    fun login(username: String, password: String): Result<LoggedInUser> {

        var loggedInUser: LoggedInUser? = null

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                Log.d("Login", "signIn:onComplete:" + task.isSuccessful)

                if (task.isSuccessful) {
                    loggedInUser = LoggedInUser(task.result?.user!!.uid, username)

                } else {
                    Toast.makeText(
                        baseContext, "Sign In Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }

        if(loggedInUser != null)
            return Result.Success(loggedInUser!!)

        return Result.Error(IOException("Error logging in"))
    }

    fun logout() {
        // TODO: revoke authentication
    }
}