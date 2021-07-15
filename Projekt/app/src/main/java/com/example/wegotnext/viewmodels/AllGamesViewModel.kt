package com.example.wegotnext.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wegotnext.model.Game
import com.example.wegotnext.ui.adapter.AllGamesAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_all_games.*

class AllGamesViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val gamesRef = db.collection("Games").orderBy("city")
    private var firestoreListener: ListenerRegistration? = null
    private val _allGames = MutableLiveData<MutableList<Game>>()
    val allGames: LiveData<MutableList<Game>> = _allGames


    init {
        loadGames()
    }

    fun loadGames(){


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

                    _allGames.postValue(arrayOfGames)
                } else {
                    Log.d("TAG", "Error getting documents: ", task.exception)
                }
            })

    }

    fun setupListener(){



        firestoreListener = gamesRef
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e("TAG", "Listen failed!", e)
                    return@EventListener
                }

                val arrayOfGames: ArrayList<Game> = ArrayList()

                for (doc in documentSnapshots!!) {
                    val game = doc.toObject(Game::class.java)
                    game.id = doc.id
                    arrayOfGames.add(game)
                }

                _allGames.postValue(arrayOfGames)
            })
    }

    fun removeListener() {
        firestoreListener!!.remove()
    }

}