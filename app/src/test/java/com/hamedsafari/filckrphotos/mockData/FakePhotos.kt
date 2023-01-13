package com.hamedsafari.filckrphotos.mockData

import com.hamedsafari.filckrphotos.data.network.model.PhotoApiModel
import com.hamedsafari.filckrphotos.data.network.model.PhotosMetaDataApiModel
import com.hamedsafari.filckrphotos.data.network.model.PhotosSearchApiModel

object FakePhotos {
    val photo = PhotosSearchApiModel(
        photos = PhotosMetaDataApiModel(
            page = 1,
            pages = 1,
            perPage = 1,
            total = 1,
            photo = listOf(
                PhotoApiModel(
                    id = "test_id",
                    owner = "test_owner",
                    secret = "test_secret",
                    server = "test_server",
                    farm = 1,
                    title = "test_title"
                )
            )
        )
    )
}
