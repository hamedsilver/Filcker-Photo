package com.hamedsafari.filckrphotos.mockData

import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosDataSource
import com.hamedsafari.filckrphotos.data.repository.PhotosRepository
import com.hamedsafari.filckrphotos.data.repository.mappers.toDomainModel
import com.hamedsafari.filckrphotos.domain.Photo
import com.hamedsafari.filckrphotos.utils.Result

class MockPhotoRepository(
    private val dataSource: PhotosDataSource
) : PhotosRepository {
    override suspend fun search(searchTerms: String): Result<List<Photo>> {
        return dataSource.searchPhotos(searchTerms).mapOnSuccess { it.toDomainModel() }
    }
}
