package com.kotlin.olena.tvshowsapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Show (
    @PrimaryKey(autoGenerate = false) val id:Int,
    val name: String,
    val imageUrl: String?,
    val rating: Double?,
    val status: String?,
    val officialSite: String?,
    val language: String?,
)