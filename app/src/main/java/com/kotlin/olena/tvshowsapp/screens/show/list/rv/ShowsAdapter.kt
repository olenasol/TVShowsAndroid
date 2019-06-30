package com.kotlin.olena.tvshowsapp.screens.show.list.rv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.olena.tvshowsapp.GlideApp
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.screens.show.list.rv.ShowListDiffCallback.Companion.ARGS_FAVOURITE
import com.kotlin.olena.tvshowsapp.data.models.Show
import kotlinx.android.synthetic.main.item_show.view.*

class ShowsAdapter(private val listener: OnShowClickedListener) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_SHOW: Int = 0
        const val VIEW_PROGRESS: Int = 1
    }

    var listOfShows: MutableList<Show?> = mutableListOf()
    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        context = parent.context
        return if (viewType == VIEW_SHOW) {
            ShowsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false))
        } else {
            ProgressHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return listOfShows.size
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (holder is ShowsHolder) {
            GlideApp.with(context).load(listOfShows[position]?.image?.originalImageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.showImageView)
            if (listOfShows[position]?.isFavourite!!) {
                holder.favouriteBtn.setImageResource(R.drawable.ic_star_full)
            } else {
                holder.favouriteBtn.setImageResource(R.drawable.ic_star_border)
            }
            holder.itemView.setOnClickListener {
                listener.onShowClicked(holder.adapterPosition, listOfShows[holder.adapterPosition]!!)
            }
            holder.favouriteBtn.setOnClickListener {
                listener.onFavouriteClicked(holder.adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (holder is ShowsHolder)
            if (!payloads.isEmpty()) {
                val payload: Bundle = payloads[0] as Bundle
                if (payload.getBoolean(ARGS_FAVOURITE))
                    holder.favouriteBtn.setImageResource(R.drawable.ic_star_full)
                else
                    holder.favouriteBtn.setImageResource(R.drawable.ic_star_border)
            }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount) {
            if (listOfShows[position] == null) {
                VIEW_PROGRESS
            } else {
                VIEW_SHOW
            }
        } else
            VIEW_PROGRESS
    }

    fun setShowsList(list: List<Show>) {
        val tempList: MutableList<Show?> = list.toMutableList()
        tempList.add(null)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                ShowListDiffCallback(listOfShows, tempList))
        diffResult.dispatchUpdatesTo(this)
        this.listOfShows = tempList
    }


    class ShowsHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val showImageView = itemView.showImageView!!
        val favouriteBtn = itemView.favouriteBtn!!
    }

    class ProgressHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}