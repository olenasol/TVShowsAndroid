package com.kotlin.olena.tvshowsapp.models

data class ShowModel(val id:Int,
                     val image:ImageModel,
                     var isFavourite: Boolean){
    companion object {
        fun transitionName(id: Int) = "item_$id"
    }
}