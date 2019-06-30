package com.kotlin.olena.tvshowsapp.screens.show.list.rv

import com.kotlin.olena.tvshowsapp.data.models.Show

interface OnShowClickedListener {
    fun onShowClicked(position: Int, show: Show)

    fun onFavouriteClicked(position: Int)
}