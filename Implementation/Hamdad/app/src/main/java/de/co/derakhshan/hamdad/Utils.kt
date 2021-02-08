package de.co.derakhshan.hamdad


import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar


class Utils( private  val context: Context){

    private  val share = context.getSharedPreferences("sharePre", Context.MODE_PRIVATE)
    private  val editor =  share.edit()

    var token: String
        set(value) {
            editor.putString("token", value)
            editor.apply()
        }
        get() = share.getString("token", "") ?: ""

    var isLoggedIn: Boolean
        set(value) {
            editor.putBoolean("isLoggedIn", value)
            editor.apply()
        }
        get() = share.getBoolean("isLoggedIn", false)


    fun showSnackBar(color: Int, msg: String, snackView: View) {
        val font = Typeface.createFromAsset(context.assets, "vazir.ttf")
        val snackBar = Snackbar.make(snackView, msg, Snackbar.LENGTH_LONG)
        val view = snackBar.view
        view.setBackgroundColor(color)
        view.layoutDirection = View.LAYOUT_DIRECTION_RTL
        val txt = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        txt.typeface = font
        snackBar.show()
    }

}