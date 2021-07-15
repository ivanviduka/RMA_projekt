package com.example.wegotnext.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel: ViewModel() {
    fun userLoggedIn(): Boolean {
         return FirebaseAuth.getInstance().currentUser != null
        }
}