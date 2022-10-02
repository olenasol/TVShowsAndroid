package com.kotlin.olena.tvshowsapp.presentation.show.list.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.olena.tvshowsapp.GlideApp
import com.kotlin.olena.tvshowsapp.R
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.olena.tvshowsapp.domain.models.ShowGeneralInfo

class ShowsAdapter(private val listener: OnShowClickedListener) :
    ListAdapter<ShowGeneralInfo, ShowsAdapter.ShowHolder>(DiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        return ShowHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        holder.setShowImage(getItem(position).imageUrl)
        holder.setFavouriteButtonState(getItem(position).isFavourite)
        holder.itemView.setOnClickListener {
            listener.onShowClicked(getItem(holder.bindingAdapterPosition).id)
        }
    }

    override fun onBindViewHolder(holder: ShowHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true) {
                holder.setFavouriteButtonState(getItem(position).isFavourite)
            }
        }
    }

    class ShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val showImageView = itemView.findViewById<ImageView>(R.id.showImageView)
        private val favouriteView = itemView.findViewById<ImageView>(R.id.favouriteView)

        fun setShowImage(imageUrl: String?) {
            GlideApp.with(itemView.context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(showImageView)
        }

        fun setFavouriteButtonState(isFavourite: Boolean) {
            favouriteView.setImageResource(if (isFavourite) R.drawable.ic_star_full else R.drawable.ic_star_border)
        }
    }

    class DiffItemCallback : DiffUtil.ItemCallback<ShowGeneralInfo>() {
        override fun areItemsTheSame(oldItem: ShowGeneralInfo, newItem: ShowGeneralInfo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ShowGeneralInfo,
            newItem: ShowGeneralInfo
        ): Boolean = oldItem == newItem

        override fun getChangePayload(oldItem: ShowGeneralInfo, newItem: ShowGeneralInfo): Any? {
            return if (oldItem.isFavourite != newItem.isFavourite) true else null
        }
    }
}