package com.kotlin.olena.tvshowsapp.screens.show.list.rv

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.kotlin.olena.tvshowsapp.data.models.Show

class ShowListDiffCallback(private val prevList: MutableList<Show?>,
                           private val newList: List<Show?>) : DiffUtil.Callback() {
    companion object {
        const val ARGS_FAVOURITE: String = "ARGS_FAVOURITE"
    }

    override fun getOldListSize(): Int {
        return prevList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return prevList[oldPos]?.id == newList[newPos]?.id
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        return prevList[oldPos]?.id == newList[newPos]?.id &&
                prevList[oldPos]?.isFavourite == (newList[newPos]?.isFavourite)
    }

    override fun getChangePayload(oldPos: Int, newPos: Int): Any? {
        val bundle = Bundle()
        if (prevList[oldPos]?.isFavourite != newList[newPos]?.isFavourite)
            bundle.putBoolean(ARGS_FAVOURITE, newList[newPos]?.isFavourite!!)
        return bundle
    }
}