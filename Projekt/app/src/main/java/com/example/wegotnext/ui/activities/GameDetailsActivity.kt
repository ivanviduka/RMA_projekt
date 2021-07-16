package com.example.wegotnext.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.wegotnext.R
import com.example.wegotnext.model.Game
import com.example.wegotnext.utils.PreferenceManager
import com.example.wegotnext.viewmodels.GameDetailsViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_game_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val viewModel by viewModel<GameDetailsViewModel>()


    private lateinit var gameID: String
    private lateinit var selectedCourt: String
    private lateinit var gameCity: String
    private lateinit var gameCreator: String
    private lateinit var gameType: String
    private lateinit var gameTime: String
    private var playersNeeded: Long? = null
    private var playersComing: Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupUI()
        viewModel.loadAudio()

        viewModel.message.observe(
            this
        ) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupUI() {
        val bundle = intent.extras

        if (bundle != null) {
            gameID = bundle.getString("GameID")!!
            selectedCourt = bundle.getString("GameCourt")!!
            gameType = bundle.getString("GameType")!!
            gameCity = bundle.getString("GameCity")!!
            gameCreator = bundle.getString("GameCreatedBy")!!
            gameTime = bundle.getString("GameTime")!!
            playersNeeded = bundle.getLong("GameNeeded")
            playersComing = bundle.getLong("GameComing")

            tv_start_time_value.text = gameTime
            tv_game_type_value.text = gameType
            tv_players_needed_value.text = playersNeeded.toString()
            tv_players_coming_value.text = playersComing.toString()

            btn_coming.setOnClickListener { userComing(gameID) }
            btn_not_coming.setOnClickListener { userCanceled(gameID) }
            setupButtons(gameID, gameCreator, playersNeeded!!, playersComing!!)
        }


    }

    private fun setupButtons(
        gameID: String,
        gameCreator: String,
        playersNeeded: Long,
        playersComing: Long
    ) {
        val currentUsername = PreferenceManager().retrieveUsername()

        var currentGamesArray = PreferenceManager().retrieveGames().split(",")

        var gameIsChecked = false

        for (game in currentGamesArray) {
            if (game == gameID) {
                gameIsChecked = true
                break
            }
        }

        if (gameCreator == currentUsername || playersComing >= playersNeeded || gameIsChecked) {
            btn_coming.setBackgroundColor(resources.getColor(R.color.grey))
            btn_coming.isEnabled = false
        } else {
            btn_coming.setBackgroundColor(resources.getColor(R.color.blue))
            btn_coming.isEnabled = true
        }

        if (gameIsChecked) {
            btn_not_coming.setBackgroundColor(resources.getColor(R.color.blue))
            btn_not_coming.isEnabled = true
        } else {
            btn_not_coming.setBackgroundColor(resources.getColor(R.color.grey))
            btn_not_coming.isEnabled = false
        }

    }

    private fun userComing(gameID: String) {

        var currentGames = PreferenceManager().retrieveGames()
        currentGames += "$gameID,"
        PreferenceManager().saveGames(currentGames)

        playersComing = playersComing!! + 1

        var updateGame = Game(
            gameCity,
            selectedCourt,
            gameCreator,
            gameTime,
            gameType,
            playersNeeded!!,
            playersComing!!
        )

        viewModel.updateGame(gameID, updateGame)
        tv_players_coming_value.text = playersComing.toString()
        viewModel.playSound(R.raw.coming_audio)


        btn_coming.isEnabled = false
        btn_coming.setBackgroundColor(resources.getColor(R.color.grey))
        btn_not_coming.isEnabled = true
        btn_not_coming.setBackgroundColor(resources.getColor(R.color.blue))
    }

    private fun userCanceled(gameID: String) {

        val currentGamesArray = PreferenceManager().retrieveGames().split(",")
        var removedGame = ""
        for (game in currentGamesArray) {
            if (game != gameID) {
                removedGame += "$game,"
            }
        }
        PreferenceManager().saveGames(removedGame)

        playersComing = playersComing!! - 1

        var updateGame = Game(
            gameCity,
            selectedCourt,
            gameCreator,
            gameTime,
            gameType,
            playersNeeded!!,
            playersComing!!
        )

        viewModel.updateGame(gameID, updateGame)
        tv_players_coming_value.text = playersComing.toString()
        viewModel.playSound(R.raw.cancel_audio)


        btn_not_coming.isEnabled = false
        btn_not_coming.setBackgroundColor(resources.getColor(R.color.grey))
        btn_coming.isEnabled = true
        btn_coming.setBackgroundColor(resources.getColor(R.color.blue))
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        viewModel.showMap(mMap, selectedCourt)
    }
}