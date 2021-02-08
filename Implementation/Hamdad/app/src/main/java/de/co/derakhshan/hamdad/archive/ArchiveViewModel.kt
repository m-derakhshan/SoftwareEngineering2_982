package de.co.derakhshan.hamdad.archive

import android.content.Context
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import de.co.derakhshan.hamdad.Address
import de.co.derakhshan.hamdad.Arrange
import de.co.derakhshan.hamdad.database.ArchiveDAO
import de.co.derakhshan.hamdad.database.tabels.ArchiveInfo
import de.co.derakhshan.hamdad.profile.ProfileActivity
import kotlinx.coroutines.*

class ArchiveViewModel(private val database: ArchiveDAO, private val context: Context) :
    ViewModel() {


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
     var isDataUpdate = false

    val events = database.getAllEvents()


    fun update() {
        if (isDataUpdate)
            return
        val request =
            JsonArrayRequest(Request.Method.GET, Address().archiveAPI, null, Response.Listener {
                uiScope.launch {
                    async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                        for (i in 0 until it.length()) {
                            val item = it.getJSONObject(i)
                            database.insert(
                                ArchiveInfo(
                                    id = item.getString("id"),
                                    cover = item.getString("cover"),
                                    score = Arrange().safelyPersianConvert(item.getString("score")),
                                    title = item.getString("title"),
                                    date = item.getString("date"),
                                    holder = item.getString("holder"),
                                    place = item.getString("place")
                                )
                            )
                        }
                    }).await()
                    isDataUpdate = true
                }
            }, Response.ErrorListener {
                isDataUpdate = true
            })
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}