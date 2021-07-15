package com.example.wegotnext.ui.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.wegotnext.R
import com.example.wegotnext.model.Game
import com.example.wegotnext.utils.PreferenceManager
import com.example.wegotnext.viewmodels.AddNewGameViewModel
import kotlinx.android.synthetic.main.activity_add_game.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddNewGameActivity : AppCompatActivity(){

    private val viewModel by viewModel<AddNewGameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)
        setupUI()

        viewModel.message.observe(this
        ) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()}

        viewModel.addGameSuccess.observe(this) {
            if(it){
                finish()
            }
        }

        viewModel.courts.observe(this) {
           loadCourts(it)
        }

    }

    private fun loadCourts(courts: Array<String>) {
        if (courts.isEmpty()){
            ArrayAdapter.createFromResource(this, R.array.court_array,
                android.R.layout.simple_spinner_item).also {adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spin_court.adapter = adapter
            }
        }

        else{
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                courts).also {adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spin_court.adapter = adapter
            }
        }


    }

    private fun setupUI() {

        viewModel.getCourts()

        ArrayAdapter.createFromResource(this, R.array.game_type_array,
            android.R.layout.simple_spinner_item).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spin_game_type.adapter = adapter
        }

        btn_add_match.setOnClickListener{ addNewGame()}
    }

    private fun addNewGame() {
        if(allFieldsChecked()){
            val selectedCourt = spin_court.selectedItem.toString().split(",")
            val city = selectedCourt[1].removePrefix(" ")
            val court = selectedCourt[0]
            val selectedGameType = spin_game_type.selectedItem.toString()
            val time = et_add_edit_time.text.toString()
            val playerNeeded = et_add_edit_players.text.toString().toLong()

            val newGame = Game(city, court, PreferenceManager()
                .retrieveUsername(), time, selectedGameType, playerNeeded, 0.toLong())

            viewModel.saveGame(newGame)
        }
        else
            Toast.makeText(this, "Input all data", Toast.LENGTH_SHORT).show()
    }

    private fun allFieldsChecked(): Boolean {
        return !et_add_edit_players.text.toString().trim().equals("")  && !et_add_edit_time.text.toString().trim().equals("")
    }

}