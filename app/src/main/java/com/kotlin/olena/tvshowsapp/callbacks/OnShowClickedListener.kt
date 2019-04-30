package com.kotlin.olena.tvshowsapp.callbacks

import android.widget.ImageView
import com.kotlin.olena.tvshowsapp.data.models.Show

interface OnShowClickedListener {
    fun onShowClicked(position:Int, show: Show, view: ImageView)

    fun onFavouriteClicked(position: Int)
}