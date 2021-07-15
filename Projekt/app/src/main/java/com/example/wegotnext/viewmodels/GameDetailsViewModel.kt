package com.example.wegotnext.viewmodels

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wegotnext.R
import com.example.wegotnext.WeGotNextApp
import com.example.wegotnext.model.Game
import com.example.wegotnext.utils.Court
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_game_details.*

class GameDetailsViewModel: ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val db = FirebaseFirestore.getInstance()

    private lateinit var soundPool: SoundPool
    private var isLoaded: Boolean = false
    var soundMap: HashMap<Int, Int> = HashMap()

    fun loadAudio() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.soundPool = SoundPool.Builder().setMaxStreams(10).build()
        } else {
            this.soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        }
        soundPool.setOnLoadCompleteListener { _, _, _ -> isLoaded = true }

        this.soundMap[R.raw.coming_audio] = this.soundPool.load(WeGotNextApp.context, R.raw.coming_audio, 1)
        this.soundMap[R.raw.cancel_audio] = this.soundPool.load(WeGotNextApp.context, R.raw.cancel_audio, 1)
    }

    fun playSound(selectedSound: Int) {
        val soundID = this.soundMap[selectedSound] ?: 0
        this.soundPool.play(soundID, 1f, 1f, 1, 0, 1f)
    }

    fun updateGame(gameID: String,game: Game){
        db.collection("Games")
            .document(gameID)
            .set(game)
            .addOnSuccessListener {
                _message.postValue("Game has been updated!")

            }
    }

    fun showMap(map: GoogleMap, courtID: String){
        db.collection("Courts").document(courtID).get()
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
                        val currentCourt = document.toObject(Court::class.java)

                        val courtLocation = LatLng(currentCourt!!.lat!!, currentCourt.lng!!)
                        map.addMarker(
                            MarkerOptions().position(courtLocation).title("Marker on Court")
                        )
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(courtLocation, 15f))

                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.exception)
                    val sydney = LatLng(-34.0, 151.0)
                    map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
                }
            }
            )

    }
}