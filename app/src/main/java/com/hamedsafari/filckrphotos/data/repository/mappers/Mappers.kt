package com.hamedsafari.filckrphotos.data.repository.mappers

import com.hamedsafari.filckrphotos.data.local.model.BookmarkedPhotoEntity
import com.hamedsafari.filckrphotos.data.network.model.PhotoApiModel
import com.hamedsafari.filckrphotos.data.network.model.PhotosSearchApiModel
import com.hamedsafari.filckrphotos.domain.Photo

/**
 * Converts the network model to the domain model
 */
internal fun PhotosSearchApiModel.toDomainModel(): List<Photo> =
    photos.photo.map { it.toDomainModel() }

internal fun PhotoApiModel.toDomainModel() =
    Photo(
        id = id,
        title = title,
        thumbnail_url = buildImageUrl(ImageSize.Thumbnail),
        image_url = buildImageUrl(ImageSize.Large)
    )

private fun PhotoApiModel.buildImageUrl(size: ImageSize): String =
    buildString {
        append("https://farm")
        append(farm)
        append(".staticflickr.com/")
        append(server)
        append("/")
        append(id)
        append("_")
        append(secret)
        append("_")
        append(size.size)
        append(".jpg")
    }

fun BookmarkedPhotoEntity.toDomainModel() =
    Photo(
        id = id,
        title = title,
        thumbnail_url = thumbnail_url,
        image_url = image_url
    )

fun Photo.toLocalModel() =
    BookmarkedPhotoEntity(
        id = id,
        title = title,
        thumbnail_url = thumbnail_url,
        image_url = image_url
    )
