package com.kotlin.olena.tvshowsapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_show.view.*

class ShowsAdapter(var listOfShows:List<ShowModel>?):RecyclerView.Adapter<ShowsAdapter.ShowsHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        return ShowsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_show,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfShows?.size!!
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {
        holder.showNameTxt?.setText(listOfShows?.get(position)?.name)
        Picasso.get().load(listOfShows?.get(position)?.image?.original).into(holder.showImageView)
    }

    class ShowsHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val showImageView = itemView.showImageView
        val showNameTxt = itemView.showNameTxt
    }
}