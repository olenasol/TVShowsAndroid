package com.kotlin.olena.tvshowsapp.presentation.show.list.rv

import com.kotlin.olena.tvshowsapp.data.models.Show

interface OnShowClickedListener {
    fun onShowClicked(position: Int, show: Show)

    fun onFavouriteClicked(position: Int)
}