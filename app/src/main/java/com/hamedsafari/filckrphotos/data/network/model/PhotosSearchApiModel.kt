package com.hamedsafari.filckrphotos.data.network.model

import com.google.gson.annotations.SerializedName

data class PhotosSearchApiModel(
    @SerializedName("photos")
    val photos: PhotosMetaDataApiModel
)

data class PhotosMetaDataApiModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("perpage")
    val perPage: Int,
    @SerializedName("total")
    val total: Long,
    @SerializedName("photo")
    val photo: List<PhotoApiModel>
)

data class PhotoApiModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("farm")
    val farm: Int,
    @SerializedName("title")
    val title: String
)
