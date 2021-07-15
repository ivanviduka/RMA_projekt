package com.example.wegotnext.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wegotnext.R
import com.example.wegotnext.ui.adapter.AllGamesAdapter
import com.example.wegotnext.viewmodels.AllGamesViewModel
import kotlinx.android.synthetic.main.fragment_all_games.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllGamesFragment: Fragment() {

    private val viewModel by viewModel<AllGamesViewModel>()
    private lateinit var allGamesAdapter: AllGamesAdapter

    companion object {
        fun newInstance(): AllGamesFragment {
            return AllGamesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_all_games, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allGamesAdapter = AllGamesAdapter(requireContext())
        setupUI()
        setupListener()

        viewModel.allGames.observe(requireActivity()) {
            allGamesAdapter.addGames(it)
            recycler_view_all_games.adapter = allGamesAdapter
        }

    }

    private fun setupUI() {
        val mLayoutManager = LinearLayoutManager(requireContext())
        recycler_view_all_games.layoutManager = mLayoutManager
        recycler_view_all_games.itemAnimator = DefaultItemAnimator()
        recycler_view_all_games.adapter = allGamesAdapter

        /*gamesRef
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val gamesList = arrayListOf<Game>()

                    for (doc in task.result!!) {
                        val game = doc.toObject(Game::class.java)
                        game.id = doc.id
                        gamesList.add(game)
                    }

                    allgamesAdapter = AllGamesAdapter(gamesList, requireContext(),db)
                    val mLayoutManager = LinearLayoutManager(requireContext())
                    recycler_view_all_games.layoutManager = mLayoutManager
                    recycler_view_all_games.itemAnimator = DefaultItemAnimator()
                    recycler_view_all_games.adapter = allgamesAdapter
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

            })

         */
    }

    override fun onDestroy() {
        super.onDestroy()
       viewModel.removeListener()
    }

    override fun onResume() {
        super.onResume()
        setupUI()
    }







}
