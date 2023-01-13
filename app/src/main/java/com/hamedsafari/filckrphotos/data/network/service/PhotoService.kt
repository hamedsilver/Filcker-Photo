package com.hamedsafari.filckrphotos.data.network.service

import com.hamedsafari.filckrphotos.data.network.model.PhotosSearchApiModel
import com.hamedsafari.filckrphotos.data.network.util.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to connect to the Filckr API to fetch photos
 */

interface PhotoService {

    @GET("?method=flickr.photos.search")
    suspend fun fetchPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int = 25,
        @Query(value = "text") searchTerm: String,
    ): PhotosSearchApiModel

    companion object {
        private const val BASE_URL = "https://api.flickr.com/services/rest/"

        fun create(): PhotoService {
            val logger = HttpLoggingInterceptor().apply {
                level = Level.BASIC
                level = Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PhotoService::class.java)
        }
    }
}
