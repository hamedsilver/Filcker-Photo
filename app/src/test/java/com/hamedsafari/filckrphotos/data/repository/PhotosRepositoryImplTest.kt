package com.hamedsafari.filckrphotos.data.repository

import com.hamedsafari.filckrphotos.mockData.MockPhotosDataSource
import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosDataSource
import com.hamedsafari.filckrphotos.data.network.util.ErrorHandler
import com.hamedsafari.filckrphotos.data.network.util.ErrorHandlerImpl
import com.hamedsafari.filckrphotos.data.repository.mappers.toDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotosRepositoryImplTest() {
    private lateinit var subject: PhotosRepository
    private lateinit var errorHandler: ErrorHandler
    private lateinit var dataSource: PhotosDataSource

    @Before
    fun setUp() {
        errorHandler = ErrorHandlerImpl()
        dataSource = MockPhotosDataSource(errorHandler)
        subject = PhotosRepositoryImpl(dataSource)
    }

    @Test
    fun photoRepository_searchPhotos_verifyPhotos() =
        runTest {
            val mockData = MockPhotosDataSource(errorHandler).searchPhotos("test")
                .mapOnSuccess { it.toDomainModel() }

            assertEquals(mockData, subject.search("test"))
        }

    @Test
    fun photoRepository_searchPhotos_isFailure() =
        runTest {
            dataSource = MockPhotosDataSource(errorHandler, true)
            subject = PhotosRepositoryImpl(dataSource)
            val mockData = MockPhotosDataSource(errorHandler, true).searchPhotos("test")

            assertEquals(mockData.isFailure, subject.search("test").isFailure)
        }

    @Test
    fun photoRepository_searchPhotos_isSuccess() =
        runTest {
            val mockData = MockPhotosDataSource(errorHandler).searchPhotos("test")
                .mapOnSuccess { it.toDomainModel() }

            assertEquals(mockData.isSuccess, subject.search("test").isSuccess)
        }
}
