package com.hamedsafari.filckrphotos.domain

/**
 * A data class that holds details about a Photo.
 *
 * @property id: String – ID of the Photo.
 * @property title: String – Title of the Photo.
 * @property thumbnail_url: String - Thumbnail Url of the Photo.
 * @property image_url: String - Image Url of the Photo(Large size).
 *
 */
data class Photo(
    val id: String,
    val title: String,
    val thumbnail_url: String,
    val image_url: String,
)
