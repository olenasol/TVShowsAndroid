package com.kotlin.olena.tvshowsapp.domain.models

data class ShowGeneralInfo(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavourite: Boolean
)

data class ShowInfo(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val rating: Double?,
    val language: String?,
    val status: String?
)