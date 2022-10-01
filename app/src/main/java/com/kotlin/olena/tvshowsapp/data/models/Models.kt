package com.kotlin.olena.tvshowsapp.data.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Show(@PrimaryKey(autoGenerate = false)
                @field:SerializedName("id") var id:Int,
                @field:SerializedName("name") var name:String,
                @Embedded@field:SerializedName("image") val image:ImageModel,
                var isFavourite: Boolean):Parcelable{

    constructor():this(0,"",ImageModel(""),false)

}

@Parcelize
data class ImageModel(@field:SerializedName("original")val originalImageUrl:String?):Parcelable

@Entity
data class ShowDetails(
        @PrimaryKey(autoGenerate = false)
        @field:SerializedName("id") var id:Int,
        var name:String?,
        var language: String?,
        @Ignore var genres: List<String>?,
        var status: String?,
        var officialSite:String?,
        @Embedded@field:SerializedName("rating") var rating:RatingModel?
){
    constructor():this(0,"","", listOf<String>(),"","",RatingModel(0.0))
}
data class RatingModel(@field:SerializedName("average") var rating:Double?)