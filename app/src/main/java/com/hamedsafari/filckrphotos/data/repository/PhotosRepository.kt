package com.hamedsafari.filckrphotos.data.repository

import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosDataSource
import com.hamedsafari.filckrphotos.domain.Photo
import com.hamedsafari.filckrphotos.utils.Result
import com.hamedsafari.filckrphotos.data.repository.mappers.toDomainModel

/**
 * An interface to represent a repository that returns Photos.
 */
interface PhotosRepository {
    /**
     * Returns Photos.
     *
     * Assumptions:
     *
     *  - List of Photo returned based on search terms.
     */
    suspend fun search(searchTerms: String): Result<List<Photo>>
}

/**
 *  An implementation for the PhotosRepository which search's Photos from dataSource.
 */
internal class PhotosRepositoryImpl(
    private val dataSource: PhotosDataSource
) : PhotosRepository {

    override suspend fun search(searchTerms: String): Result<List<Photo>> =
        dataSource.searchPhotos(searchTerms).mapOnSuccess {
            it.toDomainModel()
        }
}
