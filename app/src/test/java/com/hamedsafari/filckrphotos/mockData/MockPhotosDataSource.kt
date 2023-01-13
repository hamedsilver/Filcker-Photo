package com.hamedsafari.filckrphotos.mockData

import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosDataSource
import com.hamedsafari.filckrphotos.data.network.model.PhotosSearchApiModel
import com.hamedsafari.filckrphotos.data.network.util.ErrorHandler
import com.hamedsafari.filckrphotos.utils.Result

class MockPhotosDataSource(
    private val errorHandler: ErrorHandler,
    private val shouldThrowError: Boolean = false
) : PhotosDataSource {
    override suspend fun searchPhotos(searchTerm: String): Result<PhotosSearchApiModel> {
        return if (shouldThrowError) {
            Result.Error(errorHandler.handle(Exception("Photos not found")))
        } else
            Result.Success(FakePhotos.photo)
    }
}
