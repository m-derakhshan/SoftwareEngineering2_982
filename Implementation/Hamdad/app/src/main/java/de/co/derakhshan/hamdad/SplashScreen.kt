package de.co.derakhshan.hamdad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.co.derakhshan.hamdad.login.LoginActivity
import de.co.derakhshan.hamdad.profile.ProfileActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}