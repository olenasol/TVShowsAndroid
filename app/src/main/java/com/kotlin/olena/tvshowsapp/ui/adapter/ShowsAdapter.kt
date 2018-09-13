package com.kotlin.olena.tvshowsapp.ui.adapter

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.callbacks.OnShowClickedListener
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_progress.view.*
import kotlinx.android.synthetic.main.item_show.view.*
import org.jetbrains.anko.sdk21.coroutines.onClick

class ShowsAdapter(val listener:OnShowClickedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_SHOW: Int = 0
    val VIEW_PROGRESS: Int = 1
    var listOfShows: MutableList<ShowModel?> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_SHOW) {
            ShowsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false))
        } else{
            ProgressHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return listOfShows.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShowsHolder) {
            Picasso.get().load(listOfShows[position]?.image?.original).fit()
                    .centerCrop().into(holder.showImageView)
            ViewCompat.setTransitionName(holder.showImageView, ShowModel.transitionName(listOfShows[position]!!.id))
            holder.itemLayout.onClick {
                listener.onShowClicked(position,listOfShows[position]!!,
                    holder.showImageView) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listOfShows[position] == null) {
            VIEW_PROGRESS
        } else {
            VIEW_SHOW
        }
    }

    fun setShowsList(list: MutableList<ShowModel?>){
        listOfShows =list
        notifyDataSetChanged()
    }


    class ShowsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showImageView = itemView.showImageView
        val itemLayout = itemView.itemLayout
    }

    class ProgressHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val progressBar = itemView.progressLoadMore
    }
}