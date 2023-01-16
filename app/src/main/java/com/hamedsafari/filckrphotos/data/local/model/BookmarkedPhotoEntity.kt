package com.hamedsafari.filckrphotos.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_photo")
data class BookmarkedPhotoEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val thumbnail_url: String,
    val image_url: String
)
