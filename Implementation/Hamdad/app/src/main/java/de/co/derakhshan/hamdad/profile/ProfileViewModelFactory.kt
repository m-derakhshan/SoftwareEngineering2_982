package de.co.derakhshan.hamdad.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.co.derakhshan.hamdad.database.EventDatabase

class ProfileViewModelFactory(
    private val context: Context,
    private val database: EventDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(
                context = context,
                database = database
            ) as T
        else
            throw IllegalArgumentException("model is not Profile View model")
    }

}