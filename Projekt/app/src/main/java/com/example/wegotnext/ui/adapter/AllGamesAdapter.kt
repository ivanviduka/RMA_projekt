package com.example.wegotnext.ui.adapter


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wegotnext.R
import com.example.wegotnext.model.Game
import com.example.wegotnext.ui.activities.GameDetailsActivity
import kotlinx.android.synthetic.main.item_all_games.view.*


class AllGamesAdapter(private val context: Context) :
    RecyclerView.Adapter<AllGamesAdapter.AllGamesViewHolder>() {

    private val games = ArrayList<Game>()

    class AllGamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {

            itemView.tv_all_city.text = game.city
            itemView.tv_all_court.text = game.court
            itemView.tv_all_time.text = game.time
            itemView.tv_all_game_type.text = game.gameType
            setBackgroundColor(itemView, game.city!!)

        }


        private fun setBackgroundColor(itemView: View, city: String) {
            when (city) {
                "Osijek" -> itemView.setBackgroundColor(Color.parseColor("#4BAEFB"))
                "Zagreb" -> itemView.setBackgroundColor(Color.parseColor("#F84545"))
                "Split" -> itemView.setBackgroundColor(Color.parseColor("#5FFB4E"))
                "Zadar" -> itemView.setBackgroundColor(Color.parseColor("#FBE555"))
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllGamesViewHolder {

        return AllGamesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_all_games,
                parent, false
            )
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: AllGamesViewHolder, position: Int) {
        var game = games[position]
        holder.bind(game)

        holder.itemView.setOnClickListener { showGameDetails(game) }
    }

    private fun showGameDetails(game: Game) {
        val intent = Intent(context, GameDetailsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("GameID", game.id)
        intent.putExtra("GameCourt", game.court)
        intent.putExtra("GameCity", game.city)
        intent.putExtra("GameCreatedBy", game.createdBy)
        intent.putExtra("GameType", game.gameType)
        intent.putExtra("GameTime", game.time)
        intent.putExtra("GameNeeded", game.playersNeeded)
        intent.putExtra("GameComing", game.playersComing)
        context.startActivity(intent)

    }

    fun addGames(listOfGames: MutableList<Game>) {
        this.games.clear()
        this.games.addAll(listOfGames)
        this.notifyDataSetChanged()
    }


}



