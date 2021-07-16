package com.example.wegotnext.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wegotnext.R
import com.example.wegotnext.model.Game
import kotlinx.android.synthetic.main.item_user_games.view.*

class UserGamesAdapter :
    RecyclerView.Adapter<UserGamesAdapter.UserGamesViewHolder>() {

    private val userGames = ArrayList<Game>()

    class UserGamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tv_user_court.text = game.court
            itemView.tv_user_time.text = game.time
            itemView.tv_user_players_needed_number.text = game.playersNeeded.toString()
            itemView.tv_user_players_coming_number.text = game.playersComing.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGamesViewHolder {

        return UserGamesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_games,
                parent, false
            )
        )
    }

    fun deleteGame(position: Int) {

        userGames.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, userGames.size)

    }

    override fun getItemCount() = userGames.size

    fun getGame(position: Int) = userGames[position]

    override fun onBindViewHolder(holder: UserGamesViewHolder, position: Int) {
        val game = userGames[position]
        holder.bind(game)
    }

    fun addGames(listOfGames: MutableList<Game>) {
        this.userGames.clear()
        this.userGames.addAll(listOfGames)
        this.notifyDataSetChanged()
    }
}