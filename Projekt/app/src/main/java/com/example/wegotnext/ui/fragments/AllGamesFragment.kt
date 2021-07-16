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
        return view
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

    }

    private fun setupListener(){
        viewModel.setupListener()
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
