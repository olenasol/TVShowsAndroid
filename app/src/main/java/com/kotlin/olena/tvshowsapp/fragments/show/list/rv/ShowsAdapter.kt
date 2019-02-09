package com.kotlin.olena.tvshowsapp.fragments.show.list.rv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.callbacks.OnShowClickedListener
import com.kotlin.olena.tvshowsapp.fragments.show.list.rv.ShowListDiffCallback.Companion.ARGS_FAVOURITE
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_show.view.*

class ShowsAdapter(private val listener: OnShowClickedListener) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_SHOW: Int = 0
        const val VIEW_PROGRESS: Int = 1
    }

    var listOfShows: MutableList<ShowModel?> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
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
            Picasso.get().load(listOfShows[position]?.image?.original).fit()
                    .centerCrop().into(holder.showImageView)
            if (listOfShows[position]?.isFavourite!!) {
                holder.favouriteBtn.setImageResource(R.drawable.ic_star_full)
            } else {
                holder.favouriteBtn.setImageResource(R.drawable.ic_star_border)
            }
            ViewCompat.setTransitionName(holder.showImageView, ShowModel.transitionName(listOfShows[position]!!.id))
            holder.itemLayout.setOnClickListener {
                listener.onShowClicked(holder.adapterPosition, listOfShows[holder.adapterPosition]!!,
                        holder.showImageView)
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

    fun setShowsList(list: MutableList<ShowModel?>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                ShowListDiffCallback(listOfShows, list))
        diffResult.dispatchUpdatesTo(this)
        this.listOfShows = list
    }


    class ShowsHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val showImageView = itemView.showImageView!!
        val itemLayout = itemView.itemLayout!!
        val favouriteBtn = itemView.favouriteBtn!!
    }

    class ProgressHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}