package com.example.abschlussaufgabe

import androidx.fragment.app.Fragment


class LogInFragment : Fragment() {

    /*private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisiert Firebase Authentication
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }

        val email = "fdffhgsjh"
        val password = "fhjhgjfd"

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LogIn", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LogIn", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

     */
}