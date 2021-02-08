package de.co.derakhshan.hamdad

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.co.derakhshan.hamdad.database.EventDatabase

class UpdateInfoFactory(private val context: Context, val database: EventDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateInfo::class.java))
            return UpdateInfo(database = database, context = context) as T
        else
            throw IllegalArgumentException("model is not Update info class")

    }
}