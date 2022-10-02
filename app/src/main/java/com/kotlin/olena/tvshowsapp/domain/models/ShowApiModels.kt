package com.kotlin.olena.tvshowsapp.domain.models

import com.google.gson.annotations.SerializedName

data class ShowApiModel (
        @field:SerializedName("id") val id:Int,
        @field:SerializedName("name") val name:String,
        @field:SerializedName("image") val image: ImageModel?,
        @field:SerializedName("rating") var rating: RatingModel?,
        val status: String?,
        val officialSite:String?,
        val language: String?,
)

data class ImageModel(@field:SerializedName("original")val originalImageUrl:String?)

data class RatingModel(@field:SerializedName("average") val rating:Double?)