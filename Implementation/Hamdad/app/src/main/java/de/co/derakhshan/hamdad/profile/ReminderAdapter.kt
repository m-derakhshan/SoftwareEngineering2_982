package de.co.derakhshan.hamdad.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.database.tabels.ReminderInfo
import de.co.derakhshan.hamdad.event.MessageTransformer
import kotlinx.android.synthetic.main.reminder_item_model.view.*


class ReminderAdapter : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    lateinit var eventClickListener: MessageTransformer
    private val items = ArrayList<ReminderInfo>()
    fun addData(data: List<ReminderInfo>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.reminder_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(items[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: ReminderInfo) {
            itemView.description.text = model.title
            itemView.date.text = model.date
            if (model.id.isNotEmpty()) {
                itemView.go_event.visibility = View.VISIBLE
                itemView.go_event.setOnClickListener {
                    eventClickListener.transform(model.id)
                }
            } else
                itemView.go_event.visibility = View.GONE
        }
    }

}