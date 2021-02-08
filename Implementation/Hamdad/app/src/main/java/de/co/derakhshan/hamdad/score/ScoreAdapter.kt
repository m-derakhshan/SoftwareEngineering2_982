package de.co.derakhshan.hamdad.score


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.database.tabels.BestAll
import de.co.derakhshan.hamdad.event.MessageTransformer
import kotlinx.android.synthetic.main.event_list_item_model.view.*

class ScoreAdapter : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    private val data = ArrayList<BestAll>()
    lateinit var clickListener: MessageTransformer

    fun add(info: List<BestAll>) {
        data.clear()
        data.addAll(info)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.best_list_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(data[position])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: BestAll) {

            itemView.event.setOnClickListener {
                clickListener.transform(str = model.id, where = model.type)
            }

            itemView.title.text = model.title
            val score = " امتیازکل:  ${model.score}"
            itemView.time.text = score
            Glide.with(itemView.context)
                .load(model.cover)
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