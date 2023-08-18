package com.example.abschlussaufgabe

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
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


    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message




    fun register(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    _registerSuccess.value = true
                }
                logout()
            } else {

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

    fun setMessage(message: String) {

        _message.value = message
    }

    @SuppressLint("ShowToast")
    fun showToast(context: Context) {

        val context: Context = context
        val message = message
        val duration = Toast.LENGTH_SHORT // oder Toast.LENGTH_LONG für eine längere Anzeigedauer

        val toast = Toast.makeText(context, message.value, duration)
    }
}