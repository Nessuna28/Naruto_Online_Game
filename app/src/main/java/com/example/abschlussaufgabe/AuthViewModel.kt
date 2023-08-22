package com.example.abschlussaufgabe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser


    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean>
        get() = _registerSuccess



    fun register(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    _registerSuccess.value = true
                }
                logout()
            } else {
                // TODO: Toast schreiben
                //Toast.makeText(this, "Hallo", Toast.LENGTH_LONG)
            }
        }
    }

    fun login(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    _currentUser.value = firebaseAuth.currentUser
                } else {
                    Log.e("ERROR", "EMAIL NOT VERIFIED")
                }
            } else {
                // TODO: Toast schreiben
                //Toast.makeText(this, "Hallo", Toast.LENGTH_LONG)
            }
        }
    }

    fun logout() {

        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }

    fun reset() {
        _registerSuccess.value = false
    }

    fun sendPasswordReset(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {

        }
    }

}