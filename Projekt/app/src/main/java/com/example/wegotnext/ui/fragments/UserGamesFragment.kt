package com.example.wegotnext.ui.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    }

    private fun setupUI() {
        btn_create_new_game.setOnClickListener{ newGameCreation()}

        val mLayoutManager = LinearLayoutManager(requireContext())
        recycler_view_user_games.layoutManager = mLayoutManager
        recycler_view_user_games.itemAnimator = DefaultItemAnimator()
        recycler_view_user_games.adapter = userGamesAdapter

        setupDelete()

    }

    private fun setupListener(){
        viewModel.setupListener()
    }

    private fun newGameCreation() {
        startActivity(Intent(activity, AddNewGameActivity::class.java))
    }

    private fun setupDelete() {
        val simpleItemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                lateinit var dialog: androidx.appcompat.app.AlertDialog

                // Initialize a new instance of alert dialog builder object
                val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())

                // Set a title for alert dialog
                builder.setTitle("Are you sure you want to delete this game?")

                // On click listener for dialog buttons
                val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            val position = viewHolder.adapterPosition
                            val selectedGame = userGamesAdapter.getGame(position)
                            viewModel.deleteGame(selectedGame.id!!)
                            userGamesAdapter.deleteGame(position)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            userGamesAdapter.notifyItemChanged(viewHolder.adapterPosition)
                        }

                    }
                }
                // Set the alert dialog negative/no button
                builder.setNegativeButton("NO", dialogClickListener)
                // Set the alert dialog positive/yes button
                builder.setPositiveButton("YES", dialogClickListener)

                // Initialize the AlertDialog using builder object
                dialog = builder.create()

                // Finally, display the alert dialog
                dialog.show()

            }
        }
        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(recycler_view_user_games)
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