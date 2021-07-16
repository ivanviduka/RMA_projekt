package com.example.wegotnext.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wegotnext.model.Game
import com.example.wegotnext.utils.PreferenceManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class UserGamesViewModel : ViewModel() {


    private val db = FirebaseFirestore.getInstance()
    private val gamesRef = db.collection("Games").whereEqualTo(
        "createdBy",
        PreferenceManager().retrieveUsername()
    )
    private var firestoreListener: ListenerRegistration? = null
    private val _userGames = MutableLiveData<MutableList<Game>>()
    val userGames: LiveData<MutableList<Game>> = _userGames

    init {
        loadGames()
    }

    fun loadGames() {

        val arrayOfGames: ArrayList<Game> = ArrayList()

        gamesRef
            .get()
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val game = document.toObject(Game::class.java)
                        game.id = document.id
                        arrayOfGames.add(game)
                    }

                    _userGames.postValue(arrayOfGames)
                } else {
                    Log.d("TAG", "Error getting documents: ", task.exception)
                }
            })

    }

    fun setupListener() {

        firestoreListener = gamesRef
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e("TAG", "Listen failed!", e)
                    return@EventListener
                }

                val gameList = arrayListOf<Game>()

                for (doc in documentSnapshots!!) {
                    val game = doc.toObject(Game::class.java)
                    game.id = doc.id
                    gameList.add(game)
                }

                _userGames.postValue(gameList)
            })
    }

    fun removeListener() {
        firestoreListener!!.remove()
    }

    fun deleteGame(id: String) {
        db.collection("Games")
            .document(id)
            .delete()
            .addOnCompleteListener {

            }

    }

}