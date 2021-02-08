package de.co.derakhshan.hamdad.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.co.derakhshan.hamdad.database.EventDatabase

class EventViewModelFactory(val database: EventDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java))
            return EventViewModel(database = database) as T
        throw IllegalArgumentException("model can't be recognized!")
    }
}