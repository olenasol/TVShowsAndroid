package com.kotlin.olena.tvshowsapp.domain.models

data class ShowGeneralInfo(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavourite: Boolean
)