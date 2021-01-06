package com.e.thetogetherapp.data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.data.model.RegisteredUser
import com.e.thetogetherapp.profile.UserProfileActivity
import com.e.thetogetherapp.register.RegisterForm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.IOException

class RegisterDataSource(private val context:Context): AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    fun register(registerData: RegisterForm): Result<RegisteredUser>{
        database = Firebase.database.reference
        auth = Firebase.auth


        auth.createUserWithEmailAndPassword(registerData.email, registerData.password1)
            .addOnCompleteListener(this){ task ->
                Log.d("Register", "register:onComplete:" + task.isSuccessful)

                if(task.isSuccessful) {
//                    registeredUser = processUserData(registerData)
                    writeUser(task.result?.user!!.uid, registerData)

                    val user = Bundle()
                    val intent = Intent(context, UserProfileActivity::class.java)

                    user.putString("uid", task.result?.user!!.uid)
                    intent.putExtras(user)

                    context.startActivity(intent)
                    finish()
                }
            }

        if(auth.currentUser != null) {
            var registeredUser= RegisteredUser(auth.currentUser!!.uid,
                registerData.email)
            return Result.Success(registeredUser)
        }

        return Result.Error(IOException("Error on register"))

    }

    private fun writeUser(uid: String, registerData: RegisterForm){
        val userReference = database.child("users").child(uid)

//        userReference.setValue(registerData)
        userReference.setValue(
            mapOf(
                "email" to registerData.email,
                "name" to registerData.name,
                "nickname" to registerData.nickname,
                "age" to registerData.age,
                "type" to registerData.type,
                "location" to mapOf(
                    "country" to registerData.country,
                    "city" to registerData.city,
                    "address" to registerData.address
                )
            )
        )
    }
}
