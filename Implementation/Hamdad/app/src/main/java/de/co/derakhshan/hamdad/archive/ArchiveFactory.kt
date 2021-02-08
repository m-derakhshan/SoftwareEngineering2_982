package de.co.derakhshan.hamdad.archive

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.co.derakhshan.hamdad.database.ArchiveDAO
import java.lang.IllegalArgumentException

class ArchiveFactory(val database: ArchiveDAO, val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArchiveViewModel::class.java))
            return ArchiveViewModel(database = database, context = context) as T
        throw IllegalArgumentException("view model class is unknown")
    }
}