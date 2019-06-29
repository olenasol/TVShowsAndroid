package com.kotlin.olena.tvshowsapp.data.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Show(@PrimaryKey(autoGenerate = false)
                     @field:SerializedName("id") var id:Int,
                @Embedded@field:SerializedName("image") val image:ImageModel,
                var isFavourite: Boolean):Parcelable{
    constructor():this(0,ImageModel(""),false)
    @Ignore@IgnoredOnParcel
    var viewId: Int? = null
}

@Parcelize
data class ImageModel(@field:SerializedName("original")val originalImageUrl:String):Parcelable