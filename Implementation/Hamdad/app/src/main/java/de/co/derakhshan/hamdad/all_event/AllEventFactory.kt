package de.co.derakhshan.hamdad.all_event

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.co.derakhshan.hamdad.database.EventDatabase

class AllEventFactory(val database: EventDatabase, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllEventViewModel::class.java))
            return AllEventViewModel(database, context) as T
        throw  IllegalArgumentException("model class was not recognized")
    }
}