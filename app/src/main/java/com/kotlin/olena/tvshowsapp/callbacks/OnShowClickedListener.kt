package com.kotlin.olena.tvshowsapp.callbacks

import android.widget.ImageView
import com.kotlin.olena.tvshowsapp.models.ShowModel

interface OnShowClickedListener {
    fun onShowClicked(position:Int, show: ShowModel, view: ImageView)
}