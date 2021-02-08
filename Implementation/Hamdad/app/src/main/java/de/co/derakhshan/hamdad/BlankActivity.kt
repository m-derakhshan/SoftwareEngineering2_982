package de.co.derakhshan.hamdad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_blanck.*

class BlankActivity : AppCompatActivity() {
    private lateinit var utils: Utils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blanck)
        utils = Utils(this)
        token.text = utils.token
        exit.setOnClickListener {
            utils.isLoggedIn = false
            finish()
        }
    }
}