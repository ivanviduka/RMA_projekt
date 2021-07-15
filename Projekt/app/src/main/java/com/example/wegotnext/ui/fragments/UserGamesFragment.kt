package com.example.wegotnext.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wegotnext.R
import com.example.wegotnext.ui.activities.AddNewGameActivity
import com.example.wegotnext.ui.adapter.UserGamesAdapter
import com.example.wegotnext.viewmodels.UserGamesViewModel
import kotlinx.android.synthetic.main.fragment_user_games.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserGamesFragment : Fragment() {

    private val viewModel by viewModel<UserGamesViewModel>()
    private var userGamesAdapter = UserGamesAdapter()


    companion object {
        fun newInstance(): UserGamesFragment {
            return UserGamesFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_games, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListener()

        viewModel.userGames.observe(requireActivity()) {
            userGamesAdapter.addGames(it)
            recycler_view_user_games.adapter = userGamesAdapter
        }

        //allGamesViewModel.getGames().observe(viewLifecycleOwner, {})

    }

    private fun setupUI() {
        btn_create_new_game.setOnClickListener{ newGameCreation()}

        val mLayoutManager = LinearLayoutManager(requireContext())
        recycler_view_user_games.layoutManager = mLayoutManager
        recycler_view_user_games.itemAnimator = DefaultItemAnimator()
        recycler_view_user_games.adapter = userGamesAdapter

        /*
        gamesRef
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val gamesList = arrayListOf<Game>()

                    for (doc in task.result!!) {
                        val game = doc.toObject(Game::class.java)
                        game.id = doc.id
                        gamesList.add(game)
                    }

                    userGamesAdapter = UserGamesAdapter(gamesList, db)
                    val mLayoutManager = LinearLayoutManager(requireContext())
                    recycler_view_user_games.layoutManager = mLayoutManager
                    recycler_view_user_games.itemAnimator = DefaultItemAnimator()
                    recycler_view_user_games.adapter = userGamesAdapter
                } else {
                    Log.d("TAG", "Error getting documents: ", task.exception)
                }
            }
        */
    }

    private fun setupListener(){

        viewModel.setupListener()

        /*firestoreListener = gamesRef
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

                userGamesAdapter = UserGamesAdapter(gameList, db)
                recycler_view_user_games.adapter = userGamesAdapter
            })


         */
    }

    private fun newGameCreation() {
        startActivity(Intent(activity, AddNewGameActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeListener()
    }


}