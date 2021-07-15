package com.example.wegotnext.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wegotnext.utils.PreferenceManager
import com.google.firebase.auth.FirebaseAuth

class MainActivityViewModel: ViewModel() {

    fun userLogout(){
        PreferenceManager().saveGames("")
        PreferenceManager().saveUsername("")
        FirebaseAuth.getInstance().signOut()
    }
}