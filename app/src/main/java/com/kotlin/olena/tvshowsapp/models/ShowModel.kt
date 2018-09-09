package com.kotlin.olena.tvshowsapp.models

data class ShowModel(val id:Int,
                     val image:ImageModel ){
    companion object {
        fun transitionName(id: Int) = "item_$id"
    }
}