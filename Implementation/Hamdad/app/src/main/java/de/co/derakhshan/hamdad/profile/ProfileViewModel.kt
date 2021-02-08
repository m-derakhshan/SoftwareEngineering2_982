package de.co.derakhshan.hamdad.profile


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import de.co.derakhshan.hamdad.Address
import de.co.derakhshan.hamdad.Arrange
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.database.tabels.EventInfo
import de.co.derakhshan.hamdad.database.tabels.ReminderInfo
import de.co.derakhshan.hamdad.event.MessageTransformer
import kotlinx.coroutines.*


class ProfileViewModel(
    private val context: Context,
    private val database: EventDatabase
) :
    ViewModel() {

    private var isUpdated = false
    lateinit var status: MessageTransformer

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val reminderItems = database.reminderDao.getReminders()
    val userInfo = database.userDao.getUser()


    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _cover = MutableLiveData<String>()
    val cover: LiveData<String>
        get() = _cover

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String>
        get() = _phone

    private val _identifyCode = MutableLiveData<String>()
    val identifyCode: LiveData<String>
        get() = _identifyCode


    fun setUserData() {
        _phone.value = userInfo.value?.phone
        _identifyCode.value = userInfo.value?.identifyCode
        _name.value = userInfo.value?.name
        _cover.value = userInfo.value?.image
    }


    fun updateReminder() {
        if (isUpdated)
            return
        val request =
            JsonArrayRequest(Request.Method.GET, Address().reminderAPI, null, Response.Listener {
                it?.let {
                    uiScope.launch {
                        for (i in 0 until it.length()) {
                            val item = it.getJSONObject(i)
                            val task1 = async(
                                context = Dispatchers.Default,
                                start = CoroutineStart.DEFAULT,
                                block = {
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
                            )
                            val task2 = async(
                                context = Dispatchers.Default,
                                start = CoroutineStart.DEFAULT,
                                block = {
                                    database.reminderDao.add(
                                        ReminderInfo(
                                            id = item.getString("id"),
                                            date = Arrange().safelyPersianConvert(item.getString("date")),
                                            title = item.getString("title")
                                        )
                                    )
                                })
                            task1.await()
                            task2.await()
                            isUpdated = true
                            status.transform("200")
                        }
                    }
                }
            }, Response.ErrorListener {
                status.transform("500")
            })
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}