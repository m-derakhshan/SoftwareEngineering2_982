package de.co.derakhshan.hamdad

import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.co.derakhshan.hamdad.database.tabels.EventInfo

@BindingAdapter("loadURL")
fun ImageView.loadURL(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(context, R.mipmap.jpeg_loading_image))
        .into(this)
}


@BindingAdapter("canEnter")
fun Button.canEnter(data: EventInfo?) {
    data?.let {
        if (data.isParticipant) {
            this.background = ContextCompat.getDrawable(context, R.drawable.score_event_btn)
            this.setText(R.string.score_event)
        } else
            this.setText(R.string.participate_event)
    }
}