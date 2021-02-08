package de.co.derakhshan.hamdad.archive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.co.derakhshan.hamdad.Arrange
import de.co.derakhshan.hamdad.R
import de.co.derakhshan.hamdad.database.tabels.ArchiveInfo
import kotlinx.android.synthetic.main.archive_item_model.view.*

class ArchiveAdapter : RecyclerView.Adapter<ArchiveAdapter.ViewHolder>() {

    private val data = ArrayList<ArchiveInfo>()

    fun add(list: List<ArchiveInfo>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.archive_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.binding(data[position])


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun binding(model: ArchiveInfo) {
            itemView.title.text = model.title
            itemView.place.text = itemView.context.getString(R.string.place, model.place)
            itemView.date.text = itemView.context.getString(
                R.string.date,
                Arrange().safelyPersianConvert(model.date)
            )

            itemView.score.text = itemView.context.getString(
                R.string.score_archive,
                Arrange().safelyPersianConvert(model.score)
            )
            itemView.executor.text = model.holder
            Glide.with(itemView.context)
                .load(model.cover)
                .placeholder(R.mipmap.jpeg_loading_image)
                .into(itemView.image)
        }

    }


}