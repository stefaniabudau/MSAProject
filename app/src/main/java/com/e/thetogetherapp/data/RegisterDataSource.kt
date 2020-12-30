package com.e.thetogetherapp.data

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.thetogetherapp.data.model.RegisteredUser
import com.e.thetogetherapp.register.RegisterForm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.IOException

class RegisterDataSource: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    fun register(registerData: RegisterForm): Result<RegisteredUser>{
        database = Firebase.database.reference
        auth = Firebase.auth

        var registeredUser: RegisteredUser? = null

        auth.createUserWithEmailAndPassword(registerData.email!!, registerData.password1!!)
            .addOnCompleteListener(this){ task ->
                Log.d("Register", "register:onComplete:" + task.isSuccessful)

                if(task.isSuccessful){
                    registeredUser = processUserData(registerData)
                    writeUser(task.result?.user!!.uid, registeredUser!!)
                } else {
                    Toast.makeText(
                        baseContext, "Register Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }

        if(registeredUser != null)
            return Result.Success(registeredUser!!)

        return Result.Error(IOException("Error on register"))

    }


    private fun processUserData(registerData: RegisterForm): RegisteredUser{
        val userLocation = mapOf<String, String>("Country" to registerData.country!!,
            "City" to registerData.city!!, "Address" to registerData.address!!)

        val user = RegisteredUser(
            displayName = registerData.name,
            email = registerData.email,
            age = registerData.age,
            location = userLocation
        )

        return user
    }


    private fun writeUser(uid: String, registeredUser: RegisteredUser){
        val userReference = database.child("users").child(uid)

        userReference.setValue(registeredUser)
    }

}
