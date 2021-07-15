package com.example.wegotnext.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wegotnext.viewmodels.AllGamesViewModel

class ViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllGamesViewModel::class.java)) {
            return AllGamesViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}