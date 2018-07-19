package com.kotlin.olena.tvshowsapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_progress.view.*
import kotlinx.android.synthetic.main.item_show.view.*

class ShowsAdapter(var listOfShows: MutableList<ShowModel?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_SHOW: Int = 0
    val VIEW_PROGRESS: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_SHOW) {
            return ShowsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false))
        } else{
            return ProgressHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return listOfShows.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShowsHolder)
            Picasso.get().load(listOfShows.get(position)?.image?.original).into(holder.showImageView)
    }

    override fun getItemViewType(position: Int): Int {
        return if (listOfShows[position] == null) {
            VIEW_PROGRESS
        } else {
            VIEW_SHOW
        }
    }

    fun addShowsList(list: MutableList<ShowModel?>){
        listOfShows.addAll(list)
    }

    fun addProgress() {
        listOfShows.add(null)
        notifyItemInserted(listOfShows.size.minus(1))
    }

    fun deleteProgress() {
        listOfShows.removeAt(listOfShows.size.minus(1))
        notifyItemRemoved(listOfShows.size.minus(1))
    }

    class ShowsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showImageView = itemView.showImageView
        val itemLayout = itemView.itemLayout
    }

    class ProgressHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val progressBar = itemView.progressLoadMore
    }
}