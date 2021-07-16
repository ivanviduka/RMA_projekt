package com.example.wegotnext.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wegotnext.utils.PreferenceManager
import com.example.wegotnext.utils.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun login(userEmail: String, userPassword: String) {


        FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val currentUser = FirebaseAuth.getInstance().currentUser
                    var currentUserID = ""

                    if (currentUser != null) {
                        currentUserID = currentUser.uid
                    }

                    FirebaseFirestore.getInstance().collection("Users")
                        .document(currentUserID)
                        .get()
                        .addOnSuccessListener { document ->
                            val user = document.toObject(User::class.java)!!
                            PreferenceManager().saveUsername(user.username!!)
                            _message.postValue("You are logged in successfully")
                            _loginSuccess.postValue(true)

                        }
                        .addOnFailureListener { e ->
                            _message.postValue(e.message.toString())
                            _loginSuccess.postValue(false)
                        }

                } else {
                    _message.postValue("Incorrect username or password")
                    _loginSuccess.postValue(false)
                }
            }

    }


}