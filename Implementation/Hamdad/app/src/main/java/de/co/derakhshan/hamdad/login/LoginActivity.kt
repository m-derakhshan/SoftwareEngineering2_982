package de.co.derakhshan.hamdad.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.TimeoutError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import de.co.derakhshan.hamdad.*
import de.co.derakhshan.hamdad.database.EventDatabase
import de.co.derakhshan.hamdad.database.tabels.UserInfo
import de.co.derakhshan.hamdad.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var utils: Utils
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private lateinit var database: EventDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        utils = Utils(this)
        if (utils.isLoggedIn) {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_login)
        database = EventDatabase.getInstance(this)
        login.setOnClickListener {
            validate()
        }

    }

    private fun login() {
        val userInfo = JSONObject()
        userInfo.put("username", username.text)
        userInfo.put("password", password.text)
        login.startAnimation()
        val request =
            JsonObjectRequest(
                Request.Method.POST,
                Address().loginAPI,
                userInfo,
                Response.Listener { response ->
                    utils.token = response.getString("token")
                    if (utils.token.isEmpty()) {
                        utils.showSnackBar(
                            color = ContextCompat.getColor(this, R.color.black),
                            snackView = main_view,
                            msg = "نام کاربری یا کلمه ی عبور اشتباه می باشد!"
                        )
                        login.revertAnimation()
                    }
                    else {
                        utils.isLoggedIn = true
                        uiScope.launch {
                            withContext(Dispatchers.Default, block = {
                                val info = UserInfo(
                                    name = response.getString("name"),
                                    phone = Arrange().safelyPersianConvert(response.getString("phone")),
                                    identifyCode = Arrange().safelyPersianConvert(response.getString("identifyCode")),
                                    image = response.getString("image")
                                )
                                database.userDao.insert(info)
                            })
                        }
                        startActivity(Intent(this, ProfileActivity::class.java))
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        finish()
                    }

                },
                Response.ErrorListener {
                    try {
                        Log.i("Log", "error is ${String(it.networkResponse.data, Charsets.UTF_8)}")
                    } catch (e: Exception) {
                        Log.i("Log", "error is $it")
                    }
                    utils.showSnackBar(
                        color = ContextCompat.getColor(this, R.color.black),
                        snackView = main_view,
                        msg = if (it is NetworkError || it is TimeoutError) "لطفا از اتصال به اینترنت مطمئن شوید!"
                        else "نام کاربری یا کلمه ی عبور اشتباه می باشد!"
                    )
                    login.revertAnimation()
                })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    private fun validate() {
        when {
            username.text.isEmpty() -> YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn(username)
            password.text.isEmpty() -> YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn(password)
            else -> login()
        }
    }
}
