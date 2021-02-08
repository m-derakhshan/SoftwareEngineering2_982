package de.co.derakhshan.hamdad.all_event


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import de.co.derakhshan.hamdad.Address
import de.co.derakhshan.hamdad.Arrange
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.database.tabels.EventInfo
import kotlinx.coroutines.*


class AllEventViewModel(private val database: EventDatabase, val context: Context) :
    ViewModel() {

    val updateInfo = MutableLiveData<Boolean>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val filters = MutableLiveData<FilterItems>()

    init {
        updateInfo.value = false
        filters.value = FilterItems(name = "", justParticipated = null, justOpen = null)
    }

    fun getEvents() {
        if (updateInfo.value!!)
            return
        val request =
            JsonArrayRequest(Request.Method.GET, Address().eventsAPI, null, Response.Listener {
                uiScope.launch {
                    async(
                        context = Dispatchers.Default,
                        start = CoroutineStart.DEFAULT,
                        block = {
                            for (i in 0 until it.length()) {
                                val item = it.getJSONObject(i)
                                database.eventDao.insert(
                                    EventInfo(
                                        id = item.getString("id"),
                                        title = item.getString("title"),
                                        description = item.getString("description"),
                                        event_date = Arrange().safelyPersianConvert(
                                            item.getString(
                                                "date"
                                            )
                                        ),
                                        time = Arrange().safelyPersianConvert(item.getString("time")),
                                        score = item.getString("score"),
                                        isParticipant = item.getBoolean("isParticipate"),
                                        isDone = item.getBoolean("isDone"),
                                        holder = item.getString("holder"),
                                        place = item.getString("place"),
                                        cover = item.getString("cover")
                                    )
                                )
                            }
                        }).await()
                    updateInfo.value = true

                }
            }, Response.ErrorListener {
                updateInfo.value = true
            })

        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    fun filterData(): LiveData<List<AllEventModel>> {
        return Transformations.map(
            database.eventDao.filterEvents(
                isDone = filters.value?.justOpen,
                isRegistered = filters.value?.justParticipated,
                name = filters.value?.name
            )
        ) {
            val events = ArrayList<AllEventModel>()
            for (item in it) {
                events.add(
                    AllEventModel(
                        id = item.id,
                        image = item.cover,
                        title = item.title,
                        time = Arrange().safelyPersianConvert(item.time),
                        date = Arrange().safelyPersianConvert(item.event_date)
                    )
                )
            }
            return@map events.toList()
        }
    }
}