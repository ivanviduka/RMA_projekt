package com.example.wegotnext.utils

import android.content.Context
import com.example.wegotnext.WeGotNextApp

class PreferenceManager {

    companion object {
        const val PREFS_FILE = "MyPreferences"
        const val PREFS_KEY_USERNAME = "Username"
        const val PREFS_KEY_GAMES = "CheckedGames"

    }

    fun saveUsername(currentUsernameValue: String) {

        val sharedPreferences = WeGotNextApp.context.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )

        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_USERNAME, currentUsernameValue)
        editor.apply()
    }

    fun retrieveUsername(): String {

        val sharedPreferences = WeGotNextApp.context.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(PREFS_KEY_USERNAME, "") ?: ""
    }

    fun saveGames(checkedGames: String){

        val sharedPreferences = WeGotNextApp.context.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )

        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY_GAMES, checkedGames)
        editor.apply()


    }

    fun retrieveGames(): String {

        val sharedPreferences = WeGotNextApp.context.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(PREFS_KEY_GAMES, "") ?: ""


    }
}