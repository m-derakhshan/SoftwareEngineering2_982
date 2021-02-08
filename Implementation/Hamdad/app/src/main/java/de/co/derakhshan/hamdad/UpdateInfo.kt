package de.co.derakhshan.hamdad

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.database.tabels.EventInfo
import kotlinx.coroutines.*


class UpdateInfo(val database: EventDatabase, val context: Context) : ViewModel() {


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val isDone = MutableLiveData<String>()

    init {
        isDone.value = "false"
    }

    fun updateAllInfo(): LiveData<String> {
        uiScope.launch {

            val task1 =
                async(context = Dispatchers.Default, start = CoroutineStart.DEFAULT, block = {
                    val request = JsonArrayRequest(
                        Request.Method.GET,
                        Address().eventsAPI,
                        null,
                        Response.Listener {

                            for (i in 0 until it.length()) {
                                val jsonO = it.getJSONObject(i)
                                val event = EventInfo(
                                    id = jsonO.getString("id"),
                                    title = jsonO.getString("title"),
                                    cover = jsonO.getString("cover"),
                                    description = jsonO.getString("description"),
                                    event_date = Arrange().safelyPersianConvert(jsonO.getString("date")),
                                    time = Arrange().safelyPersianConvert(jsonO.getString("time")),
                                    place = jsonO.getString("place"),
                                    holder = jsonO.getString("holder"),
                                    isDone = jsonO.getBoolean("isDone"),
                                    isParticipant = jsonO.getBoolean("isParticipate"),
                                    score = jsonO.getString("score")
                                )
                                uiScope.launch {
                                    async(
                                        context = Dispatchers.Default,
                                        start = CoroutineStart.DEFAULT,
                                        block = {
                                            database.eventDao.insert(event)
                                        }).await()
                                }
                            }
                            isDone.value = "yes"
                        },
                        Response.ErrorListener {
                            isDone.value="error"
                        })
                    val queue = Volley.newRequestQueue(context)
                    queue.add(request)
                })
            task1.await()
        }
        return isDone
    }

    fun doneUpdate() {
        isDone.value = "false"
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
