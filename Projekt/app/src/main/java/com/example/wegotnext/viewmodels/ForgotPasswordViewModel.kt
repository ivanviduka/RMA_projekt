package com.example.wegotnext.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel: ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _passwordSendSuccess = MutableLiveData<Boolean>()
    val passwordSendSuccess: LiveData<Boolean> = _passwordSendSuccess


    fun send(email: String){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _message.postValue("New password sent successfully")
                    _passwordSendSuccess.postValue(true)


                } else{
                    _message.postValue("This email doesn't exist")
                    _passwordSendSuccess.postValue(false)

                }
            }
    }
}