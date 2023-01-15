package com.hamedsafari.filckrphotos.mockData

import com.hamedsafari.filckrphotos.data.local.model.SearchSuggestionEntity
import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosDataSource
import com.hamedsafari.filckrphotos.data.repository.PhotosRepository
import com.hamedsafari.filckrphotos.data.repository.mappers.toDomainModel
import com.hamedsafari.filckrphotos.domain.Photo
import com.hamedsafari.filckrphotos.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MockPhotoRepository(
    private val searchSuggestionDao: MockSearchSuggestionDao,
    private val dataSource: PhotosDataSource
) : PhotosRepository {
    override suspend fun search(searchTerms: String): Result<List<Photo>> {
        return dataSource.searchPhotos(searchTerms).mapOnSuccess { it.toDomainModel() }
    }

    override suspend fun saveSearchTerm(searchTerms: String) {
        searchSuggestionDao.insertSearchTerms(SearchSuggestionEntity(searchTerm = searchTerms))
    }

    override suspend fun getSearchSuggestions(): Flow<List<String>> =
        searchSuggestionDao.loadAllSearchTerms().map { it.map { it.searchTerm } }
}
