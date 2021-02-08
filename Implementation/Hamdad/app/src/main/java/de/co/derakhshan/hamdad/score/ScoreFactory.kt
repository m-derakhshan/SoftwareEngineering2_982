package de.co.derakhshan.hamdad.score

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.co.derakhshan.hamdad.database.BestAllDAO

class ScoreFactory(val database: BestAllDAO, val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java))
            return ScoreViewModel(database = database, context = context) as T
        throw  IllegalArgumentException("unknown view model class")
    }
}