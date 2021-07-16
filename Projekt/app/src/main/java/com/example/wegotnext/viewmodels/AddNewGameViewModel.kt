package com.example.wegotnext.viewmodels

import android.R
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wegotnext.model.Game
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore


class AddNewGameViewModel : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _addGameSuccess = MutableLiveData<Boolean>()
    val addGameSuccess: LiveData<Boolean> = _addGameSuccess
    private val _courts = MutableLiveData<Array<String>>()
    val courts: LiveData<Array<String>> = _courts

    private val db = FirebaseFirestore.getInstance()
    private val gamesRef = db.collection("Games")
    private val courtsRef = db.collection("Courts").orderBy("city")

    fun getCourts() {

        val arrayOfCourts: MutableList<String> = ArrayList()

        courtsRef.get().addOnSuccessListener { documents ->

            var currentCourt = ""
            for (document in documents) {

                currentCourt = "${document.id}, ${document.data.get("city")}"
                arrayOfCourts.add(currentCourt)
            }

            _courts.postValue(arrayOfCourts.toTypedArray())

        }
    }

    fun saveGame(game: Game) {
        gamesRef.add(game).addOnSuccessListener(OnSuccessListener { documentReference ->
            _message.postValue("Game created successfully")
            _addGameSuccess.postValue(true)
        })
            .addOnFailureListener(OnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
                _message.postValue("Error while adding game")
                _addGameSuccess.postValue(false)
            })
    }


}