package com.example.wegotnext.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wegotnext.utils.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class RegistrationViewModel : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> = _registrationSuccess


    fun register(
        userEmail: String,
        userPassword: String,
        firstname: String,
        lastname: String,
        username: String
    ) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {

                        val firebaseUser = task.result!!.user
                        val user = User(firebaseUser!!.uid, firstname, lastname, username)

                        FirebaseFirestore.getInstance().collection("Users")
                            .document(user.id!!)
                            .set(user, SetOptions.merge())
                            .addOnSuccessListener {
                                FirebaseAuth.getInstance().signOut()
                                _message.postValue("You are registered successfully")
                                _registrationSuccess.postValue(true)

                            }
                            .addOnFailureListener { e ->
                                _message.postValue(e.message.toString())
                                _registrationSuccess.postValue(false)
                            }
                    } else {
                        _message.postValue(task.exception!!.message.toString())
                        _registrationSuccess.postValue(false)

                    }
                }
            )
    }

}