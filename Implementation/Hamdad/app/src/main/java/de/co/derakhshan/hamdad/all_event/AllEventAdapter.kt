package de.co.derakhshan.hamdad.all_event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.event.MessageTransformer
import kotlinx.android.synthetic.main.event_list_item_model.view.*

class AllEventAdapter : RecyclerView.Adapter<AllEventAdapter.ViewHolder>() {
    lateinit var eventClickListener: MessageTransformer
    private val data = ArrayList<AllEventModel>()

    fun add(items: List<AllEventModel>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(data[position])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun binding(model: AllEventModel) {
            itemView.event.setOnClickListener {
                eventClickListener.transform(model.id)
            }
            itemView.title.text = model.title
            itemView.date.text = model.date
            itemView.time.text = model.time
            Glide.with(itemView.context)
                .load(model.image)
                .placeholder(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.mipmap.jpeg_loading_image
                    )
                )
                .into(itemView.image)
        }
    }
}