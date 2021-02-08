package de.co.derakhshan.hamdad.score

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import de.co.derakhshan.hamdad.Address
import de.co.derakhshan.hamdad.Arrange
import de.co.derakhshan.hamdad.database.BestAllDAO
import de.co.derakhshan.hamdad.database.tabels.BestAll
import kotlinx.coroutines.*

class ScoreViewModel(private val database: BestAllDAO, private val context: Context) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    var isDataUpdate = false
    val events = database.getBestEvents()
    val executor = database.getBestExecutor()


    fun update() {
        if (isDataUpdate)
            return
        val request =
            JsonArrayRequest(Request.Method.GET, Address().bestAPI, null, Response.Listener {
                uiScope.launch {
                    async(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
                        for (i in 0 until it.length()) {
                            val item = it.getJSONObject(i)
                            database.add(
                                BestAll(
                                    id = item.getString("id"),
                                    cover = item.getString("cover"),
                                    score = Arrange().safelyPersianConvert(item.getString("score")),
                                    title = item.getString("title"),
                                    type = item.getString("type")
                                )
                            )
                        }
                    }).await()
                    isDataUpdate = true
                }
            }, Response.ErrorListener {
                Log.i("Log","Error in ScoreViewModel: $it")
            })
        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}